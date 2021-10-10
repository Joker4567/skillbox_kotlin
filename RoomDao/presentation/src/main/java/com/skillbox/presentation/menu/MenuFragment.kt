package com.skillbox.presentation.menu

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
import com.skillbox.utils.ext.observeLifeCycle
import com.skillbox.utils.ext.setData
import com.skillbox.utils.platform.BaseFragment
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_menu.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : BaseFragment(R.layout.fragment_menu) {
    override val toolbarTitle: String = "Меню ресторана"
    override val statusBarColor: Int = R.color.colorTransparent
    override val statusBarLightMode: Boolean = true
    override val setToolbar: Boolean = true
    override val setDisplayHomeAsUpEnabled: Boolean = true
    override val screenViewModel by viewModel<MenuViewModel>()

    private val args by navArgs<MenuFragmentArgs>()

    private lateinit var router: MenuRouter
    private lateinit var rvMenuList: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        router = MenuRouter(this)
        rvMenuList = view.findViewById(R.id.menu_recycler_view)

        setupRecyclerView()
        bind()
    }

    private val menuAdapter by lazy {
        ListDelegationAdapter(
                itemMenuRecyclerView()
        )
    }

    private fun setupRecyclerView() {
        with(rvMenuList) {
            adapter = menuAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = ScaleInAnimator()
            setHasFixedSize(true)
        }
    }

    private fun bind() {
        observeLifeCycle(screenViewModel.source, ::handleResult)
        lifecycleScope.launchWhenStarted {
            screenViewModel.getMenu(args.restaurantId)
        }
        toolbar.setNavigationOnClickListener {
            router.routerBack()
        }
    }

    private fun handleResult(menuList: List<Menu>?) {
        menuList?.let {
            menuAdapter.setData(menuList)
        }
    }
}