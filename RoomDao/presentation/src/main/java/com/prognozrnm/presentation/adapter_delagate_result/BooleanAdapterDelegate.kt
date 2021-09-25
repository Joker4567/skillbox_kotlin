package com.prognozrnm.presentation.adapter_delagate_result

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.prognozrnm.data.db.entities.CheckListItemDaoEntity
import com.prognozrnm.data.entity.ParamResult
import com.prognozrnm.presentation.R
import com.prognozrnm.presentation.models.ParamInputType
import com.prognozrnm.utils.ext.inflate
import com.prognozrnm.utils.ext.onItemSelected
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_param_select.*

class BooleanAdapterDelegate(
    private val afterTextChanged: (ParamResult) -> Unit,
    private val clickPhoto: (CheckListItemDaoEntity) -> Unit
) : AbsListItemAdapterDelegate<CheckListItemDaoEntity, CheckListItemDaoEntity, BooleanAdapterDelegate.TextHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): TextHolder {
        return TextHolder(
            parent.inflate(R.layout.fragment_param_select),
            afterTextChanged,
            clickPhoto
        )
    }

    override fun isForViewType(
        item: CheckListItemDaoEntity,
        items: MutableList<CheckListItemDaoEntity>,
        position: Int
    ): Boolean {
        return ParamInputType.valueOf(item.inputType) == ParamInputType.Boolean
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
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: CheckListItemDaoEntity) {
            paramTitle.text = item.name
            val values = arrayOf("Значение не выбрано", "Да", "Нет")
            val adapter = ArrayAdapter(
                containerView.context,
                android.R.layout.simple_spinner_item,
                values
            )
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
            paramField.adapter = adapter
            paramField.onItemSelected {
                //Возвращаем результат занесённый пользователем + передаём тип результата
                if (!values[0].contains(it)) {
                    afterTextChanged(
                        ParamResult(
                            it,
                            item.inputType,
                            item.id
                        )
                    )
                }
            }
            when (item.resultText) {
                "Да" -> paramField.setSelection(1)
                "Нет" -> paramField.setSelection(2)
                else -> paramField.setSelection(0)
            }
            paramPhoto.setOnClickListener { clickPhoto(item) }
        }
    }
}