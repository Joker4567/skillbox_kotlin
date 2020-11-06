package com.skillbox.github.ui.current_user

import com.skillbox.github.models.Following
import com.skillbox.github.models.ProfileUser
import com.skillbox.github.network.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CurrentUserRepository {
    suspend fun getUserProfile() : ProfileUser {
        return suspendCoroutine { continuation ->
            Networking.githubApi.getProfile().enqueue(
                object : Callback<ProfileUser> {

                    override fun onFailure(call: Call<ProfileUser>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }

                    override fun onResponse(
                        call: Call<ProfileUser>,
                        response: Response<ProfileUser>
                    ) {
                        if(response.isSuccessful) {
                            continuation.resume(response.body()!!)
                        } else {
                            continuation.resumeWithException(RuntimeException("incorrect status code"))
                        }
                    }
                }
            )
        }
    }

    suspend fun getFollowingList() : List<Following> {
        return withContext(Dispatchers.IO) {
            Networking.githubApi.getFollowingList()
        }
    }
}
