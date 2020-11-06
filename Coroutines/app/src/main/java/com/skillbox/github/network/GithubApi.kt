package com.skillbox.github.network

import com.skillbox.github.models.Following
import com.skillbox.github.models.ProfileUser
import com.skillbox.github.models.Repositories
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface GithubApi {
    @GET("user")
    fun getProfile(
    ): Call<ProfileUser>

    @GET("user/following")
    suspend fun getFollowingList() : List<Following>

    @GET("repositories")
    suspend fun getRepositories() : List<Repositories>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("user/starred/{owner}/{repo}")
    suspend fun checkStarred(
        @Path("owner")
        owner_path:String,
        @Path("repo")
        repo_path:String
    ) : Response<Any>

    @Headers("Accept: application/vnd.github.v3+json")
    @PUT("user/starred/{owner}/{repo}")
    suspend fun putStarred(
        @Path("owner")
        owner_path:String,
        @Path("repo")
        repo_path:String
    ) : Response<Any>

    @Headers("Accept: application/vnd.github.v3+json")
    @DELETE("user/starred/{owner}/{repo}")
    suspend fun deleteStarred(
        @Path("owner")
        owner_path:String,
        @Path("repo")
        repo_path:String
    ) : Response<Any>
}