package ru.skillbox.flow.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.skillbox.flow.data.repository.MovieRepository
import ru.skillbox.flow.model.Movie
import ru.skillbox.flow.model.TypeMovie
import java.util.logging.Handler

class MovieViewModel : ViewModel() {

    private val mutableProgressState = MutableStateFlow(false)
    val progressState: StateFlow<Boolean> = mutableProgressState

    private val mutableResultState = MutableStateFlow(emptyList<Movie>())
    val movieListState: StateFlow<List<Movie>> = mutableResultState

    private val repository = MovieRepository()

    init {
        change(TypeMovie.all, "")
    }

    fun bind(queryFlow: Flow<String>, typeMovieFlow: Flow<TypeMovie>) {
        viewModelScope.launch {
            combine(
                    typeMovieFlow,
                    queryFlow.onStart { emit("") }
            ) { radioItem, text -> radioItem to text }
                    .debounce(500)
                    .distinctUntilChanged()
                    .onEach {
                        mutableProgressState.emit(true)
                    }
                    .flowOn(Dispatchers.Main)
                    .catch {
                        Log.e("MovieViewModel", it.message ?: "")
                    }
                    .mapLatest { (typeMovie, query) ->
                        repository.searchMovie(query, typeMovie)
                        change(typeMovie, query)
                    }
                    .flowOn(Dispatchers.IO)
                    .onEach {
                        mutableProgressState.emit(false)
                    }
                    .catch {
                        Log.e("MovieViewModel", it.message ?: "")
                    }
                    .collect { list -> }
        }
    }

    private fun change(typeMovie: TypeMovie, query: String) {
        viewModelScope.launch {
            repository.observeMovies(query, typeMovie)
                    .debounce(500)
                    .distinctUntilChanged()
                    .catch {
                        Log.e("MovieViewModel", it.message ?: "")
                    }
                    .flowOn(Dispatchers.IO)
                    .onEach { list ->
                        mutableResultState.emit(list)
                    }
                    .flowOn(Dispatchers.Main)
                    .catch {
                        Log.e("MovieViewModel", it.message ?: "")
                    }
                    .collect { }
        }
    }
}