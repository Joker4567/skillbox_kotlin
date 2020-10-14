package ru.skillbox.viewandlayout

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_enabled.*
import kotlin.system.exitProcess

class EnabledActivity : AppCompatActivity(R.layout.activity_enabled) {

    companion object {
        private const val DIAL_REQUEST_CODE = 100
    }

    private val dialLauncher = prepareCall(ActivityResultContracts.Dial()) { result ->
        //    Did not answer with "We called"
        if (result) {
            println(result.toString())
            toast("We did call")
        } else {
            println(result.toString())
            toast("We did not call")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callButton.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()
            val isPhoneNumberValid = Patterns.PHONE.matcher(phoneNumber).matches()
            if (!isPhoneNumberValid || phoneNumber.length <= 10) {
                toast("Type valid phone number")
            } else {
                dialLauncher.launch(phoneNumber)
                val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                }
                if (phoneIntent.resolveActivity(packageManager) != null) {
                    startActivityForResult(phoneIntent, DIAL_REQUEST_CODE)
                }
            }
        }
    }
//    Did not answer with "We called"
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == DIAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                toast("We did call")
            } else {
                toast("We did not call")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    //Закрываем приложение, в случае успешной авторизации пользователя
    override fun onBackPressed() {
        moveTaskToBack(true);
        exitProcess(-1)
        super.onBackPressed()
    }
}