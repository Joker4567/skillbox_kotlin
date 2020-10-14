package ru.skillbox.viewandlayout

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onStart() {
        super.onStart()
        editTextTextPassword.doOnTextChanged { text, start, count, after -> checkAccept() }
        editTextTextEmailAddress.doOnTextChanged { text, start, count, after -> checkAccept() }
        checkBox.setOnCheckedChangeListener { buttonView, isChecked -> checkAccept() }
        btnLogin.isEnabled = false
        btnLogin.setOnClickListener {
            it.hideKeyboard()
            visibleElement(false)
            editTextTextPassword.setText("")
            editTextTextEmailAddress.setText("")
            checkBox.isChecked = false
            val view = layoutInflater.inflate(R.layout.item_progress, container, false).apply {
                Handler().postDelayed({
                    container.removeView(this)
                }, 2000)
            }
            makeLongOperation()
            container.addView(view)
        }
    }

    //Проверить статус выполнения условия, активации процесса авторизации
    private fun checkAccept() { btnLogin.isEnabled = checkBox.isChecked && editTextTextPassword.text.isNotBlank() && editTextTextEmailAddress.text.isNotBlank() }

    private fun makeLongOperation() {
        btnLogin.isEnabled = false
        Handler().postDelayed({
            btnLogin.isEnabled = true
            visibleElement(true)
            Toast.makeText(this, "Аккаунт успешно авторизован", Toast.LENGTH_SHORT).show()
        }, 2000)
    }

    //Скрыть/Показать элементы интерфейса, кода происходит загрузка аккаунта
    private fun visibleElement(flag: Boolean){
        if(flag)
        {
            //Показать элементы
            imageView3.visibility = View.VISIBLE
            textView3.visibility = View.VISIBLE
            editTextTextEmailAddress.visibility = View.VISIBLE
            textView5.visibility = View.VISIBLE
            editTextTextPassword.visibility = View.VISIBLE
            checkBox.visibility = View.VISIBLE
            btnLogin.visibility = View.VISIBLE
        }
        else
        {
            //Скрыть элементы
            imageView3.visibility = View.INVISIBLE
            textView3.visibility = View.INVISIBLE
            editTextTextEmailAddress.visibility = View.INVISIBLE
            textView5.visibility = View.INVISIBLE
            editTextTextPassword.visibility = View.INVISIBLE
            checkBox.visibility = View.INVISIBLE
            btnLogin.visibility = View.INVISIBLE
        }
    }

    //Скрыть клавиатуру
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}