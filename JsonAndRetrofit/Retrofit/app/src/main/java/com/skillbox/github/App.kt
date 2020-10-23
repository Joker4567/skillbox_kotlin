package com.skillbox.github

import android.app.Application
import android.content.Context
import androidx.fragment.app.viewModels
import com.skillbox.github.ui.repository_list.RepositoryListFragment
import com.skillbox.github.ui.repository_list.RepositoryListViewModel


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    companion object {
        var repositories:RepositoryListFragment? = null
        private var mContext: Context? = null
        var token:String? = null
        val context: Context?
            get() = mContext
    }
}