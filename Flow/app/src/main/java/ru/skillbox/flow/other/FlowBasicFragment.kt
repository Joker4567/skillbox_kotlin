package ru.skillbox.flow.other//package ru.skillbox.flow.ui.fragment
//
//import android.os.Bundle
//import android.view.View
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.lifecycleScope
//import by.kirich1409.viewbindingdelegate.viewBinding
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.asFlow
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.launch
//import ru.skillbox.flow.R
//import ru.skillbox.flow.databinding.FragmentFlowBasicBinding
//import ru.skillbox.flow.utils.textChangedFlow
//import timber.log.Timber
//import kotlin.random.Random
//
//class FlowBasicFragment : Fragment(R.layout.fragment_flow_basic) {
//
//    private val binding: FragmentFlowBasicBinding by viewBinding(FragmentFlowBasicBinding::bind)
//
//    private var currentJob: Job? = null
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        flowFromCallback()
//    }
//
//    private fun flowFromCallback() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            binding.editText.textChangedFlow().collect {
//                Timber.d(it)
//            }
//        }
//
//    }
//
//    private fun flowGenerator() {
//        val generator = createFlowGenerator()
//        binding.startEmitButton.setOnClickListener {
//            currentJob?.cancel()
//            currentJob = viewLifecycleOwner.lifecycleScope.launch {
//                generator
//                        .collect {
//                            Timber.d("consume value = $it")
//                            binding.numberTextView.text = it.toString()
//                            delay(2000)
//                        }
//            }
//        }
//    }
//
//    private fun createFlowGenerator(): Flow<Int> {
//        return flow {
//            Timber.d("start flow")
//            while (true) {
//                val value = Random.nextInt()
//                Timber.d("emit value = $value")
//                emit(value)
//                Timber.d("delay")
//                delay(1000)
//            }
//        }
//    }
//
//    private fun flowBuilders() {
//        flowOf(1, 2, 3)
//
//        val flowFromSuspendLambda = suspend {
//            delay(1000)
//            10
//        }.asFlow()
//
//        (0..100).asFlow()
//        arrayOf(123, 123).asFlow()
//        listOf(123, 123).asFlow()
//        setOf(123, 123).asFlow()
//
//    }
//}