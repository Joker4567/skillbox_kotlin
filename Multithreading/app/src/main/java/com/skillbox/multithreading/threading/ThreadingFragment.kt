package com.skillbox.multithreading.threading

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.multithreading.R
import com.skillbox.multithreading.adapter.MovieAdapter
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_threading.*

class ThreadingFragment: Fragment(R.layout.fragment_threading) {

    //region Поля
    private var movieAdapter: MovieAdapter? = null
    private val viewModel: ThreadingViewModel by viewModels()
    private val mainHandler = Handler(Looper.getMainLooper())
    //endregion
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        requestMovies.setOnClickListener {
            viewModel.requestMovies()
        }
    }

    private fun initList() {
        //как передать два itemClickListener ? для одного нажатия и долгого
        movieAdapter = MovieAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        with(movieFragmentList) {
            adapter = movieAdapter
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            itemAnimator = ScaleInAnimator()
        }
        movieAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (positionStart == 0 && positionStart == linearLayoutManager.findFirstCompletelyVisibleItemPosition()) {
                    linearLayoutManager.scrollToPosition(0)
                }
            }
        })

        viewModel.time.observe(viewLifecycleOwner) {
            Log.d("ThreadTest", "livedata changed on ${Thread.currentThread().name}")
            timeTextView.text = "Время выполнения запроса: " + it.toString() + " milliseconds"
        }
        viewModel.movies.observe(viewLifecycleOwner) {
            movieAdapter?.items = it
            mainHandler.postDelayed({
                Toast.makeText(requireContext(), "Список фильмов получен", Toast.LENGTH_SHORT).show()
            }, 1000)
        }
    }

    override fun onDestroyView() {
        mainHandler.looper.quit()
        movieAdapter = null
        super.onDestroyView()
    }
}