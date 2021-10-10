package com.skillbox.presentation.lenta

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.skillbox.presentation.R
import com.skillbox.presentation.delegates.itemTapeRecyclerView
import com.skillbox.presentation.lenta.model.LentaAdapterModel
import com.skillbox.utils.ext.observeLifeCycle
import com.skillbox.utils.ext.setData
import com.skillbox.utils.platform.BaseFragment
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import org.koin.androidx.viewmodel.ext.android.viewModel


class LentaFragment : BaseFragment(R.layout.fragment_lenta) {
    override val toolbarTitle: String = "Лента"
    override val statusBarColor: Int = R.color.colorTransparent
    override val statusBarLightMode: Boolean = true
    override val setToolbar: Boolean = true
    override val setDisplayHomeAsUpEnabled: Boolean = false
    override val screenViewModel by viewModel<LentaViewModel>()
    private lateinit var router: LentaRounter

    private lateinit var rvRating: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        router = LentaRounter(this)
        rvRating = view.findViewById(R.id.lenta_recycler_view)

        setupRecyclerView()
        bind()
    }

    private val lentaAdapter by lazy {
        ListDelegationAdapter(
                itemTapeRecyclerView { restaurantId ->
                    router.routeToMenuRestaurant(restaurantId)
                }
        )
    }

    private fun setupRecyclerView() {
        with(rvRating) {
            adapter = lentaAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = ScaleInAnimator()
            setHasFixedSize(true)
        }
    }

    private fun bind(){
        observeLifeCycle(screenViewModel.source, ::handleResult)
        lifecycleScope.launchWhenStarted {
            screenViewModel.executeDao()
        }
    }

    private fun handleResult(lentaList:List<LentaAdapterModel>?){
        lentaList?.let {
            lentaAdapter.setData(lentaList)
        }
    }
}