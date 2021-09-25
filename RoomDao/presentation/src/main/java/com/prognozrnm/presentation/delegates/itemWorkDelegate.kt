package com.prognozrnm.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.prognozrnm.data.entity.WorkList.Work
import com.prognozrnm.presentation.R
import com.prognozrnm.utils.ext.gone
import kotlinx.android.synthetic.main.item_work_list.view.*

fun itemWorkList(clickListener: (Work) -> Unit) =
    adapterDelegateLayoutContainer<Work, Any>(R.layout.item_work_list) {

        containerView.setOnClickListener { clickListener.invoke(item) }

        bind {
            containerView.tvObj.text = item.nameObj
            containerView.tvZakaz.text = item.itemName
            if(item.comment.isNotEmpty())
                containerView.tvComment.text = "Комментарий: ${item.comment}"
            else
                containerView.tvComment.gone()
        }
    }

