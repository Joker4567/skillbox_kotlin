package com.skillbox.presentation.order_datail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.skillbox.data.db.entities.Menu
import com.skillbox.presentation.R
import com.skillbox.presentation.delegates.itemMenuRecyclerView
import com.skillbox.presentation.menu.MenuFragmentArgs
import com.skillbox.utils.ext.observeLifeCycle
import com.skillbox.utils.ext.setData
import com.skillbox.utils.platform.BaseFragment
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_order_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailFragment : BaseFragment(R.layout.fragment_order_detail) {
    override val toolbarTitle: String = "Детали заказа"
    override val statusBarColor: Int = R.color.colorTransparent
    override val statusBarLightMode: Boolean = true
    override val setToolbar: Boolean = true
    override val setDisplayHomeAsUpEnabled: Boolean = true
    override val screenViewModel by viewModel<OrderDetailViewModel>()

    private lateinit var router: OrderDetailRouterLogic
    private lateinit var rvOrderDetail: RecyclerView
    private val args by navArgs<OrderDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        router = OrderDetailRouter(this)
        rvOrderDetail = view.findViewById(R.id.order_detail_recycler_view)

        setupRecyclerView()
        bind()
    }

    private val orderListAdapter by lazy {
        ListDelegationAdapter(
                itemMenuRecyclerView()
        )
    }

    private fun setupRecyclerView() {
        with(rvOrderDetail) {
            adapter = orderListAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = ScaleInAnimator()
            setHasFixedSize(true)
        }
    }

    private fun bind(){
        observeLifeCycle(screenViewModel.source, ::handleResult)
        lifecycleScope.launchWhenStarted {
            screenViewModel.getDetailOrder(args.orderId)
        }
        toolbar.setNavigationOnClickListener {
            router.routerNavigateUp()
        }
    }

    private fun handleResult(orderDetailDishList:List<Menu>?){
        orderDetailDishList?.let {
            orderListAdapter.setData(orderDetailDishList)
        }
    }
}