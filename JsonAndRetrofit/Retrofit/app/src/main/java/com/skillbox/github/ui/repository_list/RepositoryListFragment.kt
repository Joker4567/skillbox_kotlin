package com.skillbox.github.ui.repository_list

import android.os.Bundle
import android.view.View
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.github.App
import com.skillbox.github.R
import com.skillbox.github.ui.adapter.RepositoriesAdapter
import com.skillbox.github.utils.autoCleared
import com.skillbox.github.utils.toast
import kotlinx.android.synthetic.main.fragment_repos_list.*

class RepositoryListFragment : Fragment(R.layout.fragment_repos_list) {
    val viewModel: RepositoryListViewModel by viewModels()
    var repositoriesAdapter: RepositoriesAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.repositories = this
        initList()
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        Handler().post {
            viewModel.getRepositoriesList()
        }
    }

    private fun initList() {
        repositoriesAdapter = RepositoriesAdapter(onItemClick = { position ->
            showDetail(position)
        })
        reposList.apply {
            adapter = repositoriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        viewModel.repoList.observe(viewLifecycleOwner, Observer { repositoriesAdapter.items = it })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { enableControls(it.not()) })
        viewModel.onError.observe(viewLifecycleOwner, Observer {
            toast("Во время загрузки случилась ошибка: $it")
        })
    }

    private fun enableControls(enable: Boolean) {
        //if(enable) toast("Загрузка списка репозиториев")
    }

    private fun showDetail(position: Int) {
        //Переход в детальную информацию репозитория
        val action = RepositoryListFragmentDirections.actionRepositoryListFragmentToDetailRepoFragment(position)
        findNavController().navigate(action)
    }
}