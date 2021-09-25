package ru.skillbox.services.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object DownloadState {

    private val mutableDownloadState = MutableStateFlow(false)
    val downloadState: StateFlow<Boolean> = mutableDownloadState

    fun changeDownloadState(isDownloading: Boolean) {
        mutableDownloadState.value = isDownloading
    }

}