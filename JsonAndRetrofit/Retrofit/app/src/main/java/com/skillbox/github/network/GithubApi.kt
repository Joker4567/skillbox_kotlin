package com.skillbox.github.network

import com.skillbox.github.models.ProfileUser
import com.skillbox.github.models.Repositories
import com.skillbox.github.models.ServerItemsWrapper
import retrofit2.Call
import retrofit2.http.*

interface GithubApi {
    @GET("user")
    fun getProfile(
    ): Call<ProfileUser>

    @GET("repositories")
    fun getRepositories() : Call<List<Repositories>>

    @GET("user/starred/{owner}/{repo}")
    fun checkStarred(
        @Path("owner")
        owner_path:String,
        @Path("repo")
        repo_path:String
    ) : Call<Boolean>
}