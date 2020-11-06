package com.skillbox.github.ui.repository_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.models.Repositories
import com.skillbox.github.utils.SingleLiveEvent
import kotlinx.coroutines.*

class RepositoryListViewModel : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("RepositoryListViewModel", "error from CoroutineExceptionHandler", throwable)
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)


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
        scope.launch {
            isLoadingLiveData.postValue(true)
            try {
                val users = repository.getRepositories()
                repoLiveData.postValue(users)
            } catch (t: Throwable) {
                repoLiveData.postValue(emptyList())
                onErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun getCheckStarred(
        item: Repositories,
        onComplete: (Boolean) -> Unit,
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
            val owner_path = item.owner.owner_path.replace("https://api.github.com/users/","")
            val repo_path = item.header
            isLoadingLiveData.postValue(false)
            try {
                val checkStarred = repository.getCheckStarred(
                    owner_path = owner_path,
                    repo_path = repo_path)
                onComplete(checkStarred)
            } catch (t:Throwable){
                onError(t.message.orEmpty())
                onErrorLiveData.postValue(t.message)
            }
        }
    }

    fun putStarred(
        item: Repositories,
        onComplete: (Boolean) -> Unit,
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
            val owner_path = item.owner.owner_path.replace("https://api.github.com/users/","")
            val repo_path = item.header
            isLoadingLiveData.postValue(false)
            try {
                val checkStarred = repository.putStarred(
                    owner_path = owner_path,
                    repo_path = repo_path)
                onComplete(checkStarred)
            } catch (t:Throwable){
                onError(t.message.orEmpty())
                onErrorLiveData.postValue(t.message)
            }
        }
    }

    fun deleteStarred(
        item: Repositories,
        onComplete: (Boolean) -> Unit,
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
            val owner_path = item.owner.owner_path.replace("https://api.github.com/users/","")
            val repo_path = item.header
            isLoadingLiveData.postValue(false)
            try {
                val checkStarred = repository.deleteStarred(
                    owner_path = owner_path,
                    repo_path = repo_path)
                onComplete(checkStarred)
            } catch (t:Throwable){
                onError(t.message.orEmpty())
                onErrorLiveData.postValue(t.message)
            }
        }
    }

    override fun onCleared() {
        scope.coroutineContext.cancelChildren()
        viewModelScope.coroutineContext.cancelChildren()
        super.onCleared()
    }
}