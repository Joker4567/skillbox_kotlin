package ru.skillbox.viewandlayout

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var state: FormState = FormState("","",false)

    //region ЖЦ Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DebugLog.Log("onCreate")
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
            if (Validate()) {
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
        btnANR.setOnClickListener {
            openNewTabWindow("https://permscience.ru/napravleniya/ekologiya-i-bezopasnost-territorij", this)
//            Thread.sleep(7000)
//            btnANR.setBackgroundColor(Color.DKGRAY)
        }
        btnANR2.text = "ANR"
        btnANR2.setOnClickListener {
            Thread.sleep(7000)
            btnANR2.setBackgroundColor(Color.DKGRAY)
        }
    }

    override fun onStart() {
        super.onStart()
        DebugLog.Log("OnStart")
    }

    override fun onStop() {
        super.onStop()
        DebugLog.Log("onStop")
    }

    override fun onResume() {
        super.onResume()
        DebugLog.Log("onResume")
    }

    override fun onPause() {
        super.onPause()
        DebugLog.Log("onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        DebugLog.Log("onDestroy")
    }
    //endregion

    //Восстановление данных из SaveInstanceState
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state = savedInstanceState.getParcelable(KEY_ACTIVITY) ?: error("unreachable")
        editTextTextEmailAddress.setText(state.email)
        editTextTextPassword.setText(state.password)
        checkBox.isChecked = state.checkAgree
    }

    //Сохранение данных в SaveInstanceState
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        state.save(editTextTextEmailAddress.text.toString(), editTextTextPassword.text.toString(), checkBox.isChecked)
        outState.putParcelable(KEY_ACTIVITY, state)
    }

    //Проверить статус выполнения условия, активации процесса авторизации
    private fun checkAccept(): Boolean =
        checkBox.isChecked && editTextTextPassword.text.isNotBlank() && editTextTextEmailAddress.text.isNotBlank()
    //Обработка кнопки долгая операция
    private fun makeLongOperation() {
        btnLogin.isEnabled = false
        Handler().postDelayed({
            val enabledActivityIntent = Intent(this, EnabledActivity::class.java)
            startActivity(enabledActivityIntent)
            btnLogin.isEnabled = true
            visibleElement(true)
            Toast.makeText(this, "Аккаунт успешно авторизован", Toast.LENGTH_SHORT).show()
        }, 2000)
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
    //Открыть Url в браузере или ином View
    private fun openNewTabWindow(urls: String, context: Context) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        context.startActivity(intents)
    }
    //Закрываем приложение
    public fun ExitApp(){
        finish()
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

    companion object{
        const val KEY_ACTIVITY:String = "MainActivity"
    }

    object DebugLog{
        fun Log(text:String){
            if(BuildConfig.DEBUG) {
                Log.v(KEY_ACTIVITY, text)
                Log.d(KEY_ACTIVITY, text)
                Log.i(KEY_ACTIVITY, text)
                Log.w(KEY_ACTIVITY, text)
                Log.e(KEY_ACTIVITY, text)
                Log.println(Log.ASSERT, KEY_ACTIVITY, text)
            }
        }
    }
}