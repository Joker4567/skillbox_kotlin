package ru.skillbox.flow.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ru.skillbox.flow.model.TypeMovie

fun EditText.textChangedFlow(): Flow<String> {
    return callbackFlow<String> {
        val textChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                sendBlocking(s?.toString().orEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        this@textChangedFlow.addTextChangedListener(textChangedListener)
        awaitClose {
            this@textChangedFlow.removeTextChangedListener(textChangedListener)
        }
    }
}

fun CheckBox.checkedChangesFlow(): Flow<Boolean> {
    return callbackFlow {
        val checkedChangeListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            sendBlocking(isChecked)
        }
        setOnCheckedChangeListener(checkedChangeListener)
        awaitClose {
            setOnCheckedChangeListener(null)
        }
    }
}

fun RadioGroup.checkRadioGroup() : Flow<TypeMovie> {
    return callbackFlow {
        val checkedChangeListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            sendBlocking(TypeMovie.valueOf(radio.text.toString()))
        }
        setOnCheckedChangeListener(checkedChangeListener)
        awaitClose {
            setOnCheckedChangeListener(null)
        }
    }
}
