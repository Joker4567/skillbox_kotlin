package com.skillbox.networking.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.networking.R
import com.skillbox.networking.adapter.MovieListAdapter
import com.skillbox.networking.utils.autoCleared
import com.skillbox.networking.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_movies.*


class MovieListFragment : Fragment(R.layout.fragment_movies) {

    private var movieAdapter: MovieListAdapter by autoCleared()
    private var positionType:Int = 0
    private val viewModel: MovieListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        val adapter = ArrayAdapter.createFromResource(requireContext(),
            R.array.type_movies, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTypeFilm.adapter = adapter
        spinnerTypeFilm.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View,
                position: Int, id: Long
            ) {
                positionType = position
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        }

        btRevert.setOnClickListener {
            sendAPI()
            linearBlockError.isVisible = false
        }
    }

    private fun bindViewModel() {
        searchButton.setOnClickListener {
            sendAPI()
            linearBlockError.isVisible = false
        }
        ratingButton.setOnClickListener {
            //Добавляем оценку
            if(viewModel.addRating())
                Toast.makeText(requireContext(),"Оценка успешно добавлена", Toast.LENGTH_SHORT).show()
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
        ratingButton.isEnabled = isLoading.not()
    }
}