package com.skillbox.datetimepermission.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.skillbox.datetimepermission.Adapter.MessageAdapter
import com.skillbox.datetimepermission.App
import com.skillbox.datetimepermission.Models.Message
import com.skillbox.datetimepermission.R
import com.skillbox.datetimepermission.Utils.toast
import kotlinx.android.synthetic.main.message_fragment.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import kotlin.random.Random

class MessageListFragment : Fragment(R.layout.message_fragment) {

    //region Поля
    private var messageAdapter: MessageAdapter? = null
    private var rationaleDialog: AlertDialog? = null
    //endregion

    //region ЖЦ Фрагмента
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        initMessageField()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            addLocation()
            getLocationButton.text = getString(R.string.getLocationButton)
            setDanger(false)
        } else {
            val needRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if(needRationale) {
                showLocationRationaleDialog()
                getLocationButton.text = getString(R.string.permissionDeniedButton)
                setDanger(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rationaleDialog?.dismiss()
        rationaleDialog = null
        messageAdapter = null
    }
    //endregion

    //Кнопка добавить геолокацию
    private fun initMessageField() {
        val isLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if(!isLocationPermissionGranted) {
            getLocationButton.text = getString(R.string.permissionDeniedButton)
            setDanger(true)
        }
        else {
            getLocationButton.text = getString(R.string.getLocationButton)
            setDanger(false)
            isEmptyList()
        }
        getLocationButton.setOnClickListener {
            showCurrentLocationWithPermissionCheck()
        }
    }
    //Первичная иницализация массива записей
    private fun initList() = with(messageList) {
        messageAdapter = MessageAdapter {position -> addLocation(position)}
        adapter = messageAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
        messageAdapter?.submitList(App.data?.getLocationList())
    }
    //Изменяем дату при клике на элемент списка
    private fun addLocation(position: Int) {
        val currentDateTime = LocalDateTime.now()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        val zonedDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute)
                            .atZone(ZoneId.systemDefault())

                        toast("Установленная дата: $zonedDateTime")
                        App.data?.editLocationInDate(position, zonedDateTime.toInstant())
                        messageAdapter?.submitList(App.data?.getLocationList())
                        messageAdapter?.notifyItemChanged(position)
                    },
                    currentDateTime.hour,
                    currentDateTime.minute,
                    true
                )
                    .show()
            },
            currentDateTime.year,
            currentDateTime.month.value - 1,
            currentDateTime.dayOfMonth
        )
            .show()

    }

    private fun showLocationRationaleDialog() {
        rationaleDialog = AlertDialog.Builder(requireContext())
            .setMessage("Необходимо одобрение разрешения для отображения информации по локации")
            .setPositiveButton("OK", { _, _ -> requestLocationPermission() })
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE)
    }

    private fun showCurrentLocationWithPermissionCheck() {
        val isLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
            addLocation()
        if(isLocationPermissionGranted) {
            getLocationButton.text = getString(R.string.getLocationButton)
            setDanger(false)
        } else {
            val needRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if(needRationale) {
                showLocationRationaleDialog()
            } else {
                requestLocationPermission()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun addLocation(){
        LocationServices.getFusedLocationProviderClient(requireContext())
            .getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, null)
            .addOnSuccessListener {
                var info = "Локация отсутствует"
                it?.let {
                    info = """
                        Lat = ${it.latitude}
                        Lng = ${it.longitude}
                        Alt = ${it.altitude}
                        Speed = ${it.speed}
                        Accuracy = ${ it.accuracy }
                    """.trimIndent()
                } ?: toast("Локация отсутствует")
                if(info == "Локация отсутствует" && App.data?.getLocationList()?.isEmpty()!!)
                {
                    textDanger.visibility = View.VISIBLE
                    textDanger.text = "Нет локаций для отображения"
                } else {
                    setDanger(false)
                    textDanger.text = ""
                    val newMessage = Message(
                        id = Random.nextLong(),
                        text = info,
                        dateTime = Instant.now())
                    val position = App.data?.addLocation(newMessage)
                    messageAdapter?.submitList(App.data?.getLocationList())
                    position?.let { pos -> messageAdapter?.notifyItemInserted(pos) }
                }
            }
            .addOnCanceledListener {
                toast("Запрос локации был отменен")
            }
            .addOnFailureListener {
                toast("Запрос локации завершился неудачно")
            }
    }

    private fun setDanger(flag:Boolean){
        textDanger.text =
            if(flag)
                "Для отображения списка геолокаций необходимо разрешение"
            else
                ""
        if(flag){
            textDanger.visibility = View.VISIBLE
            messageList.visibility = View.GONE
        }
        else {
            textDanger.visibility = View.GONE
            messageList.visibility = View.VISIBLE
        }
    }

    private fun isEmptyList(){
        if(App.data?.getLocationList()?.isEmpty()!!)
        {
            textDanger.visibility = View.VISIBLE
            textDanger.text = "Нет локаций для отображения"
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 4313
    }
}