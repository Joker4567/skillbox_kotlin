package com.skillbox.a10_fragment_

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.skillbox.a10_fragment_.helper.withArguments

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        editTextTextPassword.doOnTextChanged { text, start, count, after ->
            btnLogin.isEnabled = checkAccept()
        }
        editTextTextEmailAddress.doOnTextChanged { text, start, count, after ->
            btnLogin.isEnabled = checkAccept()
        }
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            btnLogin.isEnabled = checkAccept()
        }
        btnLogin.isEnabled = false
        btnLogin.setOnClickListener {
//            if (Validate()) {
                it.hideKeyboard()
                visibleElement(false)
                editTextTextPassword.setText("")
                editTextTextEmailAddress.setText("")
                checkBox.isChecked = false
//                val view = layoutInflater.inflate(R.layout.item_progress, container, false).apply {
//                    Handler().postDelayed({
//                        container.removeView(this)
//                    }, 2000)
//                }
                makeLongOperation()
//                container.addView(view)
//            }
        }
    }

    //Проверить статус выполнения условия, активации процесса авторизации
    private fun checkAccept(): Boolean =
        checkBox.isChecked && editTextTextPassword.text.isNotBlank() && editTextTextEmailAddress.text.isNotBlank()
    //Обработка кнопки долгая операция
    private fun makeLongOperation() {
        btnLogin.isEnabled = false
        Handler().postDelayed({
            btnLogin.isEnabled = true
            visibleElement(true)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, MainFragment())
                ?.apply {
                    addToBackStack("MainFragment")
                }
                ?.commit()
        }, 500)
    }
    //Валидация формы
    private fun Validate(): Boolean {
        if (editTextTextEmailAddress.text.toString()
                .isEmailValid() && editTextTextPassword.text.toString().isPassword()
        ) {
            editTextTextEmailAddress.setTextColor(Color.BLACK)
            editTextTextPassword.setTextColor(Color.BLACK)
            textViewCorrectEmailAddress.visibility = View.GONE
            textViewCorrectPassword.visibility = View.GONE
            return true
        } else if (!editTextTextEmailAddress.text.toString().isEmailValid() && !editTextTextPassword.text.toString().isPassword()) {
            textViewCorrectEmailAddress.isVisible = true
            textViewCorrectPassword.isVisible = true
            editTextTextPassword.setTextColor(Color.RED)
            editTextTextEmailAddress.setTextColor(Color.RED)
            return false
        } else if (!editTextTextEmailAddress.text.toString().isEmailValid()) {
            textViewCorrectEmailAddress.isVisible = true
            editTextTextEmailAddress.setTextColor(Color.RED)
            textViewCorrectPassword.visibility = View.GONE
            editTextTextPassword.setTextColor(Color.BLACK)
            return false
        } else if (!editTextTextPassword.text.toString().isPassword()) {
            textViewCorrectPassword.isVisible = true
            editTextTextPassword.setTextColor(Color.RED)
            textViewCorrectEmailAddress.visibility = View.GONE
            editTextTextEmailAddress.setTextColor(Color.BLACK)
            return false
        }
        return false
    }
    //Скрыть/Показать элементы интерфейса, кода происходит загрузка аккаунта
    private fun visibleElement(flag: Boolean) {
        grpComponent.isVisible = flag
    }
    //Скрыть клавиатуру
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    //Валидация email
    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }
    //Валидация пароля
    fun String.isPassword(): Boolean {
        val PASSWORD_REGEX =
            """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#${'$'}%!\-_?&])(?=\S+${'$'}).{8,}""".toRegex()
        return PASSWORD_REGEX.matches(this)
    }
}