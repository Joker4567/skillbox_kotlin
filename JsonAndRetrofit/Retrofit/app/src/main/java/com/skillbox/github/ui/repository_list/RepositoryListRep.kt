package com.skillbox.github.ui.repository_list

import com.skillbox.github.models.Repositories
import com.skillbox.github.models.ServerItemsWrapper
import com.skillbox.github.network.Networking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryListRep {
    fun getRepositories(
        onComplete: (List<Repositories>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Networking.githubApi.getRepositories().enqueue(
            object : Callback<List<Repositories>> {

                override fun onFailure(call: Call<List<Repositories>>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(
                    call: Call<List<Repositories>>,
                    response: Response<List<Repositories>>
                ) {
                    if(response.isSuccessful) {
                        onComplete(response.body()!!)
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }
            }
        )
    }
    fun getCheckStarred(
        owner_path:String,
        repo_path:String,
        onComplete: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Networking.githubApi.checkStarred(owner_path, repo_path).enqueue(
            object : Callback<Boolean> {

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    if(response.isSuccessful) {
                        onComplete(response.body()!!)
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }
            }
        )
    }
}