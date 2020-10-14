package com.skillbox.fragments11.service

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class FormState(
    var articleTags: MutableMap<String, Boolean> = emptyMap<String, Boolean>().toMutableMap(),
    var listBadge:MutableList<Int> = emptyList<Int>().toMutableList()
): Parcelable {

    fun save(articleTags: MutableMap<String, Boolean>, listBadge:MutableList<Int>): FormState {
        this.articleTags = articleTags
        this.listBadge = listBadge
        return copy(articleTags = articleTags, listBadge = listBadge)
    }
}