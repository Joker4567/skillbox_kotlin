package com.prognozrnm.presentation.adapter_delagate_result

import android.app.TimePickerDialog
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.prognozrnm.data.db.entities.CheckListItemDaoEntity
import com.prognozrnm.data.entity.ParamResult
import com.prognozrnm.presentation.R
import com.prognozrnm.presentation.models.ParamInputType
import com.prognozrnm.utils.ext.afterTextChanged
import com.prognozrnm.utils.ext.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_param_text.*
import java.time.LocalDateTime
import java.util.*

class TimeAdapterDelegate(
    private val afterTextChanged: (ParamResult) -> Unit,
    private val clickPhoto: (CheckListItemDaoEntity) -> Unit
): AbsListItemAdapterDelegate<CheckListItemDaoEntity, CheckListItemDaoEntity, TimeAdapterDelegate.TextHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): TextHolder {
        return TextHolder(
            parent.inflate(R.layout.fragment_param_text),
            afterTextChanged,
            clickPhoto
        )
    }

    override fun isForViewType(item: CheckListItemDaoEntity, items: MutableList<CheckListItemDaoEntity>, position: Int): Boolean {
        return ParamInputType.valueOf(item.inputType) == ParamInputType.Time
    }

    override fun onBindViewHolder(
        item: CheckListItemDaoEntity,
        holder: TextHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class TextHolder(
        override val containerView: View,
        private val afterTextChanged: (ParamResult) -> Unit,
        private val clickPhoto: (CheckListItemDaoEntity) -> Unit
    ): RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: CheckListItemDaoEntity) {
            paramField.setOnClickListener {
                val currentDateTime = LocalDateTime.now()
                TimePickerDialog(
                    containerView.context,
                    { _, hourOfDay, minute ->
                        paramField.setText("$hourOfDay:$minute")
                    },
                    currentDateTime.hour,
                    currentDateTime.minute,
                    true
                )
                    .show()
            }
            paramUnit.text = item.unit
            paramTitle.text = item.name
            paramField.setText(item.resultText)
            paramField.afterTextChanged { afterTextChangedResult(it, item.inputType, item.id) }
            paramPhoto.setOnClickListener { clickPhoto(item) }
        }

        //Возвращаем результат занесённый пользователем + передаём тип результата
        private fun afterTextChangedResult(text:String, inputType:Int, idCheckList:Int){
            afterTextChanged(
                ParamResult(text, inputType, idCheckList)
            )
        }
    }
}