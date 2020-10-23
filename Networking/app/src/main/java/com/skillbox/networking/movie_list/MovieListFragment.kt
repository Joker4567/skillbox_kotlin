package com.skillbox.networking.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.networking.R
import com.skillbox.networking.network.TypeMovie
import com.skillbox.networking.utils.autoCleared
import kotlinx.android.synthetic.main.fragment_movies.*


class MovieListFragment : Fragment(R.layout.fragment_movies) {

    private var movieAdapter: MovieListAdapter by autoCleared()
    private var positionType:Int = 0
    private val viewModel: MovieListViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        bindViewModel()
    }

    private fun initList() {
        movieAdapter = MovieListAdapter()
        with(moviesList) {
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
        btRevert.setOnClickListener {
            sendAPI()
            linearBlockError.isVisible = false
        }

        val countries = resources.getStringArray(R.array.type_movies)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, countries)
        adsObjects.setAdapter(adapter)
        adsObjects.setOnItemClickListener { parent, view, position, id ->
            positionType = position
        }
        adsObjects.setText("all")
    }

    private fun bindViewModel() {
        searchButton.setOnClickListener {
            sendAPI()
            linearBlockError.isVisible = false
        }

        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.movieList.observe(viewLifecycleOwner) {
            linearBlockError.isVisible = false
            movieAdapter.items = it
        }
        viewModel.isError.observe(viewLifecycleOwner) {
            linearBlockError.isVisible = true
            textError.text = "Ошибка: " + it
            moviesList.isVisible = false
        }
    }

    private fun sendAPI(){
        val queryText = searchEditText.text.toString()
        val year:Int = yearEditText.text.toString().toIntOrNull()?: 0
        viewModel.search(queryText, positionType, year)
    }

    private fun updateLoadingState(isLoading: Boolean) {
        moviesList.isVisible = isLoading.not()
        progressBar.isVisible = isLoading
        searchButton.isEnabled = isLoading.not()
    }
}