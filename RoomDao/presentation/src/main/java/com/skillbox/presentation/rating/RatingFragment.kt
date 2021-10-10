package com.skillbox.presentation.rating

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.skillbox.presentation.R
import com.skillbox.presentation.delegates.itemRatingRecyclerView
import com.skillbox.presentation.rating.model.RatingAdapterModel
import com.skillbox.utils.ext.observeLifeCycle
import com.skillbox.utils.ext.setData
import com.skillbox.utils.platform.BaseFragment
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import org.koin.androidx.viewmodel.ext.android.viewModel

class RatingFragment : BaseFragment(R.layout.fragment_rating) {

    override val toolbarTitle: String = "Рейтинг"
    override val statusBarColor: Int = R.color.colorTransparent
    override val statusBarLightMode: Boolean = true
    override val setToolbar: Boolean = true
    override val setDisplayHomeAsUpEnabled: Boolean = false
    override val screenViewModel by viewModel<RatingViewModel>()

    private lateinit var rvRating: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvRating = view.findViewById(R.id.rating_recycler_view)

        setupRecyclerView()
        bind()
    }

    private val ratingListAdapter by lazy {
        ListDelegationAdapter(
            itemRatingRecyclerView()
        )
    }

    private fun setupRecyclerView() {
        with(rvRating) {
            adapter = ratingListAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = ScaleInAnimator()
            setHasFixedSize(true)
        }
    }

    private fun bind(){
        observeLifeCycle(screenViewModel.source, ::handleResult)
        lifecycleScope.launchWhenStarted {
            screenViewModel.getRating()
        }
    }

    private fun handleResult(ratingRestourant:List<RatingAdapterModel>?){
        ratingRestourant?.let {
            ratingListAdapter.setData(ratingRestourant)
        }
    }
}