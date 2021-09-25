package ru.skillbox.flow.other/*
package ru.skillbox.flow.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.skillbox.flow.R
import ru.skillbox.flow.databinding.FragmentFlowOperatorsBinding
import ru.skillbox.flow.utils.checkedChangesFlow
import ru.skillbox.flow.utils.textChangedFlow
import timber.log.Timber
import java.io.IOException

class FlowOperatorsFragment : Fragment(R.layout.fragment_flow_operators) {

    private val users = listOf(
            User(1, "Иван Пертов", 18, Gender.MALE),
            User(2, "Иван Сергеев", 20, Gender.MALE),
            User(3, "Анна Иванова", 25, Gender.FEMALE),
            User(4, "Елена Сидорова", 10, Gender.FEMALE),
            User(5, "Сергей Петров", 20, Gender.MALE),
            User(6, "Елена Васильева", 30, Gender.FEMALE),
    )

    private val binding: FragmentFlowOperatorsBinding by viewBinding(FragmentFlowOperatorsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorHandling()
    }

    private fun flowOperators() {
        viewLifecycleOwner.lifecycleScope.launch {

            flow {
                Timber.d("thread on flow = ${Thread.currentThread().name}")
                delay(1000)
                emit(1)
                delay(1000)
                emit(2)
            }
                    .flowOn(Dispatchers.IO)
                    .onEach {
                        Timber.d("thread onEach1 = ${Thread.currentThread().name}")
                    }
                    .flowOn(Dispatchers.Main)
                    .onEach {
                        Timber.d("thread onEach2 = ${Thread.currentThread().name}")
                    }
                    .map { it * it }
                    .flowOn(Dispatchers.IO)
                    .onEach {
                        Timber.d("thread before collect = ${Thread.currentThread().name}")
                    }
                    .collect { Timber.d("consume value = $it") }

            combine(
                    binding.femaleCheckbox.checkedChangesFlow().onStart { emit(false) },
                    binding.searchEditText.textChangedFlow().onStart { emit("") }
            ) { onlyFemale, text -> onlyFemale to text }

                    .debounce(500)
                    .distinctUntilChanged()
                    .onEach {
                        showProgress(true)
                    }
                    .mapLatest { (onlyFemale, text) -> searchUsers(onlyFemale, text) }
                    .onEach {
                        showProgress(false)
                    }
                    .map { it.map { it.toString() }.joinToString("\n") }
                    .collect {
                        binding.resultTextView.text = it
                    }

        }
    }

    private suspend fun searchUsers(onlyFemale: Boolean, query: String): List<User> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            users.filter {
                val isGenderCorrect = (!onlyFemale || it.gender == Gender.FEMALE)
                it.name.contains(query, ignoreCase = true) && isGenderCorrect
            }
        }
    }

    private fun showProgress(show: Boolean) {
        binding.progressBar.isVisible = show
        binding.resultTextView.isVisible = !show
    }

    private fun errorHandling() {
        flow {
            delay(1000)
            throw IOException("network unavailable")
            emit(1)
        }
                .catch { emit(-1) }
                .map { it * 2 }
                .catch { Timber.d("map exception = $it") }
                .map { error("test exception") }
                .catch { Timber.d("map 2 exception = $it") }
                .onEach { Timber.d("element = $it") }
                .catch { Timber.d("collect exception = $it") }
                .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}*/
