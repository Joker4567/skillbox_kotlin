package com.skillbox.presentation.order

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.skillbox.presentation.R
import com.skillbox.presentation.delegates.itemOrderRecyclerView
import com.skillbox.presentation.order.model.OrderAdapterModel
import com.skillbox.utils.ext.observeLifeCycle
import com.skillbox.utils.ext.setData
import com.skillbox.utils.platform.BaseFragment
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderFragment : BaseFragment(R.layout.fragment_order) {
    override val toolbarTitle: String = "Заказ"
    override val statusBarColor: Int = R.color.colorTransparent
    override val statusBarLightMode: Boolean = true
    override val setToolbar: Boolean = true
    override val setDisplayHomeAsUpEnabled: Boolean = false
    override val screenViewModel by viewModel<OrderViewModel>()

    private lateinit var router: OrderRouterLogic
    private lateinit var rvOrder: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        router = OrderRouter(this)
        rvOrder = view.findViewById(R.id.order_recycler_view)

        setupRecyclerView()
        bind()
    }

    private val orderListAdapter by lazy {
        ListDelegationAdapter(
                itemOrderRecyclerView(::openDetailOrder)
        )
    }

    private fun setupRecyclerView() {
        with(rvOrder) {
            adapter = orderListAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = ScaleInAnimator()
            setHasFixedSize(true)
        }
    }

    private fun bind(){
        observeLifeCycle(screenViewModel.source, ::handleResult)
        lifecycleScope.launchWhenStarted {
            screenViewModel.getOrder()
        }
    }

    private fun handleResult(orderList:List<OrderAdapterModel>?){
        orderList?.let {
            orderListAdapter.setData(orderList)
        }
    }

    private fun openDetailOrder(orderId: Int) {
        this.findNavController().navigate(OrderFragmentDirections.actionOrderFragmentToOrderDetailFragment(orderId))
//        router.routeDetailOrder(orderId)
    }
}