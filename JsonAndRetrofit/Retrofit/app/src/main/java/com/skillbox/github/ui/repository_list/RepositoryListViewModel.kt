package com.skillbox.github.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.models.Repositories
import com.skillbox.github.utils.SingleLiveEvent

class RepositoryListViewModel : ViewModel() {
    private val repository = RepositoryListRep()

    private val repoLiveData = MutableLiveData<List<Repositories>>(emptyList())
    private val isLoadingLiveData = MutableLiveData<Boolean>(false)
    private val onErrorLiveData = SingleLiveEvent<String>()

    val repoList: LiveData<List<Repositories>>
        get() = repoLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val onError: SingleLiveEvent<String>
        get() = onErrorLiveData

    fun getRepositoriesList() {
        isLoadingLiveData.postValue(true)
        repository.getRepositories(
            onComplete = { users ->
                isLoadingLiveData.postValue(false)
                repoLiveData.postValue(users)
            },
            onError = { mes ->
                isLoadingLiveData.postValue(false)
                repoLiveData.postValue(emptyList())
                onErrorLiveData.postValue(mes.message)
            }
        )
    }

    fun getCheckStarred(
        item: Repositories,
        onComplete: (Boolean) -> Unit,
        onError: (String) -> Unit
    ){
        val owner_path = item.owner.owner_path.replace("https://api.github.com/users/","")
        val repo_path = item.owner.repos_path.replace("https://api.github.com/users/","")
        repository.getCheckStarred(
            owner_path = owner_path,
            repo_path = repo_path,
            onComplete = { check ->
                isLoadingLiveData.postValue(false)
                onComplete(check)
            },
            onError = { mes ->
                isLoadingLiveData.postValue(false)
                onError(mes.message.orEmpty())
                onErrorLiveData.postValue(mes.message)
            }
        )
    }
}