package com.skillbox.github.ui.current_user

import com.skillbox.github.models.ProfileUser
import com.skillbox.github.network.Networking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentUserRepository {
    fun getUserProfile(
        onComplete: (ProfileUser) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Networking.githubApi.getProfile().enqueue(
            object : Callback<ProfileUser> {

                override fun onFailure(call: Call<ProfileUser>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(
                    call: Call<ProfileUser>,
                    response: Response<ProfileUser>
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
