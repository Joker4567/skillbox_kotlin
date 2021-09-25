package com.prognozrnm.presentation.adapter_delagate_result

import android.text.InputType
import android.view.View
import android.view.ViewGroup
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

class NumberAdapterDelegate(
    private val afterTextChanged: (ParamResult) -> Unit,
    private val clickPhoto: (CheckListItemDaoEntity) -> Unit
): AbsListItemAdapterDelegate<CheckListItemDaoEntity, CheckListItemDaoEntity, NumberAdapterDelegate.TextHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): TextHolder {
        return TextHolder(
            parent.inflate(R.layout.fragment_param_text),
            afterTextChanged,
            clickPhoto
        )
    }

    override fun isForViewType(
        item: CheckListItemDaoEntity,
        items: MutableList<CheckListItemDaoEntity>,
        position: Int
    ): Boolean {
        return ParamInputType.valueOf(item.inputType) == ParamInputType.Number
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
            paramUnit.text = item.unit
            paramTitle.text = item.name
            paramField.setText(item.resultText)
            paramField.afterTextChanged { afterTextChangedResult(it, item.inputType, item.id) }
            paramField.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
            paramPhoto.setOnClickListener { clickPhoto(item) }
        }

        //Возвращаем результат занесённый пользователем + передаём тип результата
        private fun afterTextChangedResult(text: String, inputType: Int, idCheckList:Int){
            afterTextChanged(
                ParamResult(text, inputType, idCheckList)
            )
        }
    }
}