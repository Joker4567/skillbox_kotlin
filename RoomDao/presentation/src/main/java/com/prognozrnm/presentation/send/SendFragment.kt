package com.prognozrnm.presentation.send

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.prognozrnm.data.entity.ResultAdapter
import com.prognozrnm.presentation.R
import com.prognozrnm.presentation.delegates.itemResultList
import com.prognozrnm.utils.ext.observeLifeCycle
import com.prognozrnm.utils.ext.setData
import com.prognozrnm.utils.platform.BaseFragment
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_send.*
import org.jetbrains.anko.support.v4.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SendFragment : BaseFragment(R.layout.fragment_send) {
    override val toolbarTitle: String = "Данные"
    override val statusBarColor: Int = R.color.colorTransparent
    override val statusBarLightMode: Boolean = true
    override val setToolbar: Boolean = true
    override val setDisplayHomeAsUpEnabled: Boolean = false
    override val screenViewModel by viewModel<SendViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        bind()
    }

    private val resultListAdapter by lazy {
        ListDelegationAdapter(
            itemResultList()
        )
    }

    private fun setupRecyclerView() {
        with(rvResults) {
            adapter = resultListAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = ScaleInAnimator()
            setHasFixedSize(true)
        }
    }

    private fun bind(){
        observeLifeCycle(screenViewModel.source, ::handleResult)
        ivSend.setOnClickListener {
            val res = screenViewModel.uploadResult()
            if(res.isNotEmpty())
                toast(res)
        }
        pullToRefresh_send.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        pullToRefresh_send.setColorSchemeColors(Color.WHITE)
        pullToRefresh_send.setOnRefreshListener {
            screenViewModel.load()
            pullToRefresh_send.isRefreshing = false
        }
    }

    private fun handleResult(listResult:List<ResultAdapter>?){
        listResult?.let {
            resultListAdapter.setData(listResult)
        }
    }
}