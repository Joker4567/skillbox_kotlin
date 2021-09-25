package ru.skillbox.flow.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.skillbox.flow.R
import ru.skillbox.flow.data.room.ProspectorDatabase
import ru.skillbox.flow.databinding.FragmentMovieBinding
import ru.skillbox.flow.ui.adapter.MovieListAdapter
import ru.skillbox.flow.ui.viewmodel.MovieViewModel
import ru.skillbox.flow.utils.autoCleared
import ru.skillbox.flow.utils.checkRadioGroup
import ru.skillbox.flow.utils.textChangedFlow

class MovieFragment : Fragment(R.layout.fragment_movie) {

    private val binding: FragmentMovieBinding by viewBinding(FragmentMovieBinding::bind)
    private val movieViewModel by viewModels<MovieViewModel>()
    private var movieAdapter: MovieListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        movieViewModel.bind(binding.movieSearch.textChangedFlow(), binding.movieGroup.checkRadioGroup())
        movieViewModel.progressState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .onEach { isProgress ->
                    showProgress(isProgress)
                }
                .launchIn(lifecycleScope)
        movieViewModel.movieListState
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .onEach { list ->
                    movieAdapter.items = list
                }
                .launchIn(lifecycleScope)
    }

    private fun showProgress(show: Boolean) {
        binding.movieProgress.isVisible = show
        binding.movieMoviesList.isVisible = !show
    }

    private fun initList() {
        movieAdapter = MovieListAdapter()
        with(binding.movieMoviesList) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(
                    DividerItemDecoration(
                            requireContext(),
                            DividerItemDecoration.VERTICAL
                    )
            )
        }
    }
}