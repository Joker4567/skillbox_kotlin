package com.prognozrnm.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.prognozrnm.data.entity.ResultAdapter
import com.prognozrnm.presentation.R
import kotlinx.android.synthetic.main.item_send_list.view.*

fun itemResultList() = adapterDelegateLayoutContainer<ResultAdapter,Any>(R.layout.item_send_list) {
    bind {
        containerView.tv_title.text = item.zakaz
        containerView.tv_description.text = item.objName
        when(item.status){
            0.toByte() -> containerView.tv_status.text = "Статус: в ожидании отправки"
            1.toByte() -> containerView.tv_status.text = "Статус: отправлены"
            2.toByte() -> containerView.tv_status.text = "Статус: ошибка отправки"
        }

        containerView.tv_count_work.text =
            "Всего для отправки: ${item.countWorkItemLocal} из ${item.countWorkItemsServer}"
        containerView.tv_count_map.text = "Всего геопозиций для отправки: ${item.countObjMap}"
    }
}