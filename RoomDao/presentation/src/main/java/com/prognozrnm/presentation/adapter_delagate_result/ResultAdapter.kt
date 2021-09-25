package com.prognozrnm.presentation.adapter_delagate_result

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.prognozrnm.data.db.entities.CheckListItemDaoEntity
import com.prognozrnm.data.entity.ParamResult

class ResultAdapter(
    afterTextChanged:(ParamResult) -> Unit,
    clickPhoto: (CheckListItemDaoEntity) -> Unit
) : AsyncListDifferDelegationAdapter<CheckListItemDaoEntity>(PersonDiffUtilCallback()) {
    init {
        delegatesManager
            .addDelegate(DateTimeAdapterDelegate(afterTextChanged = afterTextChanged, clickPhoto))
            .addDelegate(TimeAdapterDelegate(afterTextChanged = afterTextChanged, clickPhoto))
            .addDelegate(SecondsAdapterDelegate(afterTextChanged = afterTextChanged, clickPhoto))
            .addDelegate(SelectAdapterDelegate())
            .addDelegate(TextAdapterDelegate(afterTextChanged = afterTextChanged, clickPhoto))
            .addDelegate(NumberAdapterDelegate(afterTextChanged = afterTextChanged, clickPhoto))
            .addDelegate(BooleanAdapterDelegate(afterTextChanged = afterTextChanged, clickPhoto))
    }

    class PersonDiffUtilCallback : DiffUtil.ItemCallback<CheckListItemDaoEntity>() {
        override fun areItemsTheSame(oldItem: CheckListItemDaoEntity, newItem: CheckListItemDaoEntity): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: CheckListItemDaoEntity, newItem: CheckListItemDaoEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }
}