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
import kotlinx.android.synthetic.main.fragment_param_seconds.*

class SecondsAdapterDelegate(
    private val afterTextChanged: (ParamResult) -> Unit,
    private val clickPhoto: (CheckListItemDaoEntity) -> Unit
) : AbsListItemAdapterDelegate<CheckListItemDaoEntity, CheckListItemDaoEntity, SecondsAdapterDelegate.TextHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): TextHolder {
        return TextHolder(
            parent.inflate(R.layout.fragment_param_seconds),
            afterTextChanged,
            clickPhoto
        )
    }

    override fun isForViewType(
        item: CheckListItemDaoEntity,
        items: MutableList<CheckListItemDaoEntity>,
        position: Int
    ): Boolean {
        return ParamInputType.valueOf(item.inputType) == ParamInputType.Seconds
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
            paramField.inputType = InputType.TYPE_CLASS_NUMBER
            paramField2.inputType = InputType.TYPE_CLASS_NUMBER
            paramField.afterTextChanged { afterTextChangedResult(it, item.inputType, item.id) }
            paramField2.afterTextChanged { afterTextChangedResult(it, item.inputType, item.id) }
            paramPhoto.setOnClickListener { clickPhoto(item) }
        }

        //Возвращаем результат занесённый пользователем + передаём тип результата
        private fun afterTextChangedResult(text: String, inputType: Int, idCheckList:Int) {
            if (paramField.text.isNotEmpty() && paramField2.text.isNotEmpty()) {
                val text:String = (paramField.text.toString() + "-" + paramField2.text.toString())
                afterTextChanged(
                    ParamResult(text, inputType, idCheckList)
                )
            }
        }
    }
}