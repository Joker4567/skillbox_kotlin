package com.prognozrnm.presentation.adapter_delagate_result

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.prognozrnm.data.db.entities.CheckListItemDaoEntity
import com.prognozrnm.presentation.R
import com.prognozrnm.presentation.models.ParamInputType
import com.prognozrnm.utils.ext.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_param_select.paramTitle

class SelectAdapterDelegate(
): AbsListItemAdapterDelegate<CheckListItemDaoEntity, CheckListItemDaoEntity, SelectAdapterDelegate.TextHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): TextHolder {
        return TextHolder(
            parent.inflate(R.layout.fragment_param_select)
        )
    }

    override fun isForViewType(item: CheckListItemDaoEntity, items: MutableList<CheckListItemDaoEntity>, position: Int): Boolean {
        return ParamInputType.valueOf(item.inputType) == ParamInputType.Select
    }

    override fun onBindViewHolder(
        item: CheckListItemDaoEntity,
        holder: TextHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class TextHolder(
        override val containerView: View
    ): RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: CheckListItemDaoEntity) {
            paramTitle.text = item.name
        }
    }
}