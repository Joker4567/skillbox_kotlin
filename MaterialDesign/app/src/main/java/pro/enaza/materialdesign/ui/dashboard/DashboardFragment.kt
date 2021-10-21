package pro.enaza.materialdesign.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pro.enaza.materialdesign.R
import pro.enaza.materialdesign.databinding.FragmentDashboardBinding
import pro.enaza.materialdesign.ui.dashboard.adapter.CardListAdapter
import pro.enaza.materialdesign.utils.autoCleared
import pro.enaza.materialdesign.utils.convertDpToPixel
import pro.enaza.materialdesign.utils.hideKeyBoard

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val dashboardViewModel by viewModels<DashboardViewModel>()
    private val binding: FragmentDashboardBinding by viewBinding(FragmentDashboardBinding::bind)
    private var movieAdapter: CardListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        dashboardViewModel.observableCardList
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .onEach { list ->
                    movieAdapter.items = list
                    hideKeyBoard()
                }
                .launchIn(lifecycleScope)

        val snackBar = Snackbar.make(requireView(), "", Snackbar.LENGTH_LONG)

        val snackBarView: View = snackBar.view
        snackBarView.translationY = -convertDpToPixel(48F, requireContext())

        dashboardViewModel.observableSnackBar
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .onEach { isSnackSucces ->
                    if(isSnackSucces.not()){
                        snackBar
                                .setText("Соединение с сервером отсутствует, показаны сохранённые объекты")
                                .setDuration(Snackbar.LENGTH_INDEFINITE)
                                .setAction("Повторить") {
                                    snackBar.dismiss()
                                    Handler().postDelayed({
                                        dashboardViewModel.postSnackBar()
                                    }, 1000)
                                }
                                .show()
                        Handler().postDelayed({
                            snackBar.dismiss()
                        }, 8000)
                    } else {
                        snackBar.setAction("", null)
                        snackBar.setText("Список обновлён")
                        snackBar.duration = Snackbar.LENGTH_LONG
                        snackBar.show()
                    }

                }
                .launchIn(lifecycleScope)
    }

    private fun initList() {
        movieAdapter = CardListAdapter()
        with(binding.dashboardCardList) {
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