package com.prognozrnm.presentation.geo_location

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.prognozrnm.data.db.entities.ObjDaoEntity
import com.prognozrnm.presentation.R
import com.prognozrnm.presentation.delegates.itemGeoMapList
import com.prognozrnm.utils.ext.observeLifeCycle
import com.prognozrnm.utils.ext.setData
import com.prognozrnm.utils.platform.BaseFragment
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_geo_location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class GeoLocationFragment : BaseFragment(R.layout.fragment_geo_location) {
    override val toolbarTitle: String = "Список геометок"
    override val statusBarColor: Int = R.color.colorTransparent
    override val statusBarLightMode: Boolean = true
    override val setToolbar: Boolean = true
    override val setDisplayHomeAsUpEnabled: Boolean = true
    override val screenViewModel by viewModel<GeoLocationViewModel> {
        parametersOf(args.idResult)
    }
    private val args: GeoLocationFragmentArgs by navArgs()
    private lateinit var router: GeoLocationLogic
    private lateinit var item: ObjDaoEntity
    private var image_uri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = GeoLocationRouter(this)
        setupRecyclerView()
        bind()
    }

    private val geoMapListAdapter by lazy {
        ListDelegationAdapter(
            itemGeoMapList(
                {
                    this.item = it
                    getLocation()
                }, {
                    //delete
                    handleWorks(screenViewModel.removeGeoBlock(it))
                }, {
                    this.item = it
                    openCameraCheckPermission()
                }
            )
        )
    }

    private fun bind() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            router.routeToWorkCheckList()
        }
        observeLifeCycle(screenViewModel.geoMaps, ::handleWorks)
        ivAddBlockGeoMap.setOnClickListener {
            screenViewModel.addGeoBlock()
        }
    }

    private fun setupRecyclerView() {
        with(rvGeoList) {
            adapter = geoMapListAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = ScaleInAnimator()
            setHasFixedSize(true)
        }
    }

    private fun handleWorks(geoMaps: List<ObjDaoEntity>?) {
        geoMaps?.let {
            geoMapListAdapter.setData(geoMaps)
        }
    }

    private fun getLocation() {
        val isLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (isLocationPermissionGranted) {
            CoroutineScope(Dispatchers.IO).launch {
                LocationServices.getFusedLocationProviderClient(requireContext())
                    .getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, null)
                    .addOnSuccessListener {
                        CoroutineScope(Dispatchers.Main).launch {
                            with(item) {
                                latitude = it.latitude
                                longitude = it.longitude
                            }
                            screenViewModel.addItemGeoLocation(item)
                        }
                    }
                    .addOnCanceledListener { toast("Запрос локации был отменен") }
                    .addOnFailureListener { toast("Запрос локации завершился неудачно") }
            }
        } else {
            permission(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),PERMISSION_REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE_LOCATION) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                getLocation()
            } else {
                val needRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                if (needRationale) {
                    toast("Для получения текущего местоположения, требуется разрешение!")
                }
            }
        }
        if (requestCode == PERMISSION_REQUEST_CAMERA_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                openCamera()
            } else {
                val needRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.CAMERA
                )
                if (needRationale) {
                    toast("Для снимка фото, требуется разрешение!")
                }
            }
        }
    }

    private fun openCameraCheckPermission() {
        val isCameraPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        if (isCameraPermissionGranted) {
            openCamera()
        } else {
            permission(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), PERMISSION_REQUEST_CAMERA_CODE
            )

        }
    }

    private fun permission(arr: Array<String>, code: Int) {
        requestPermissions(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            code
        )
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Photo_title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo_description")
        image_uri = requireActivity().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPTURE_CODE) {
            val bytes = readBytes(requireContext(), image_uri!!)
            bytes?.let {
                screenViewModel.uploadPhoto(item, bytes, requireContext())
                toast("Фото успешно отправлено")
            }
        }
    }

    private fun readBytes(context: Context, uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }

    companion object {
        private const val PERMISSION_REQUEST_CAMERA_CODE = 1000;
        private const val IMAGE_CAPTURE_CODE = 1001
        private const val PERMISSION_REQUEST_CODE_LOCATION = 4313
    }
}