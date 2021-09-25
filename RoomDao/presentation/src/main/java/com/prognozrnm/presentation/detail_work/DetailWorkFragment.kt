package com.prognozrnm.presentation.detail_work

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
import com.prognozrnm.data.db.entities.CheckListItemDaoEntity
import com.prognozrnm.presentation.R
import com.prognozrnm.presentation.adapter_delagate_result.ResultAdapter
import com.prognozrnm.utils.ext.observeLifeCycle
import com.prognozrnm.utils.platform.BaseFragment
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_detail_work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailWorkFragment : BaseFragment(R.layout.fragment_detail_work) {
    override val toolbarTitle: String = "Детальная информация"
    override val statusBarColor: Int = R.color.colorTransparent
    override val statusBarLightMode: Boolean = true
    override val setToolbar: Boolean = true
    override val setDisplayHomeAsUpEnabled: Boolean = true
    override val screenViewModel by viewModel<DetailWorkViewModel> {
        parametersOf(args.workItem)
    }
    private lateinit var router: DetailWorkRouterLogic
    private val args: DetailWorkFragmentArgs by navArgs()
    private var resultAdapter: ResultAdapter? = null
    private val PERMISSION_CAMERA_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null
    private lateinit var item: CheckListItemDaoEntity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = DetailWorkRouter(this)
        setupAppBar()
        bind()
        initList()
    }

    private fun bind() {
        ivGeoMapPlace.setOnClickListener {
            router.routeToGeoMap(args.workItem.resultId)
        }
        observeLifeCycle(screenViewModel.listCheckList, ::handleWorks)
    }

    private fun setupAppBar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).title = getString(R.string.fragment_detail_work_title)
        toolbar.setNavigationOnClickListener {
            router.routeToHome()
        }
    }

    private fun initList() {
        resultAdapter = ResultAdapter ({
            //Получаем текст который был введён в поле
            screenViewModel.checkResult(it)
        },{
            //событие клика фото
            item = it
            openCameraCheck()
        })
        with(rvWorkListCheck) {
            adapter = resultAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            itemAnimator = ScaleInAnimator()
        }
    }

    private fun handleWorks(item: List<CheckListItemDaoEntity>?) {
        item?.let {
            resultAdapter?.items = mutableListOf<CheckListItemDaoEntity>().apply {
                addAll(it)
            }
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
                            screenViewModel.addItemGeoLocation(it.latitude, it.longitude)
                        }
                    }
                    .addOnCanceledListener { toast("Запрос локации был отменен") }
                    .addOnFailureListener { toast("Запрос локации завершился неудачно") }
            }
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_REQUEST_CODE_CAMERA
            )
        }
    }

    private fun openCameraCheck(){
        val isCameraPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        if(isCameraPermissionGranted){
            openCamera()
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PERMISSION_CAMERA_CODE
            )
        }
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

    override fun onResume() {
        super.onResume()
        getLocation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPTURE_CODE){
            val bytes = readBytes(requireContext(), image_uri!!)
            bytes?.let {
                screenViewModel.uploadPhoto(item, bytes, requireContext())
                toast("Фото успешно отправлено")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_REQUEST_CODE_CAMERA) {
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
        if(requestCode == PERMISSION_CAMERA_CODE) {
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

    private fun readBytes(context: Context, uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }

    override fun onDestroyView() {
        super.onDestroyView()
        resultAdapter = null
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE_CAMERA = 4313
    }
}
