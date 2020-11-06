package com.skillbox.github.ui.repository_list

import com.skillbox.github.models.Repositories
import com.skillbox.github.models.ServerItemsWrapper
import com.skillbox.github.network.Networking
import kotlinx.coroutines.*
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RepositoryListRep {
    suspend fun getRepositories(
    ): List<Repositories> {
        return withContext(Dispatchers.IO) {
            Networking.githubApi.getRepositories()
        }
    }

    suspend fun getCheckStarred(
        owner_path: String,
        repo_path: String
    ): Boolean {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val code =
                        Networking.githubApi.checkStarred(
                            owner_path = owner_path,
                            repo_path = repo_path
                        )
                            .code()
                    when (code) {
                        204 -> continuation.resume(true)
                        404 -> continuation.resume(false)
                        403 -> continuation.resumeWithException(
                            RuntimeException("Forbidden")
                        )
                        401 -> continuation.resumeWithException(
                            RuntimeException("Requires authentication")
                        )
                        304 -> continuation.resumeWithException(
                            RuntimeException("Not modified")
                        )
                        else -> continuation.resumeWithException(RuntimeException("incorrect status code"))
                    }
                } catch (t:Throwable){
                    continuation.resumeWithException(t)
                }
            }
        }
    }

    suspend fun putStarred(
        owner_path: String,
        repo_path: String
    ): Boolean {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val code =
                        Networking.githubApi.putStarred(
                            owner_path = owner_path,
                            repo_path = repo_path
                        )
                            .code()
                    when (code) {
                        204 -> continuation.resume(true)
                        404 -> continuation.resume(false)
                        403 -> continuation.resumeWithException(
                            RuntimeException("Forbidden")
                        )
                        401 -> continuation.resumeWithException(
                            RuntimeException("Requires authentication")
                        )
                        304 -> continuation.resumeWithException(
                            RuntimeException("Not modified")
                        )
                        else -> continuation.resumeWithException(RuntimeException("incorrect status code"))
                    }
                } catch (t:Throwable){
                    continuation.resumeWithException(t)
                }
            }
        }
    }

    suspend fun deleteStarred(
        owner_path: String,
        repo_path: String
    ): Boolean {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val code =
                        Networking.githubApi.deleteStarred(
                            owner_path = owner_path,
                            repo_path = repo_path
                        )
                            .code()
                    when (code) {
                        204 -> continuation.resume(true)
                        404 -> continuation.resume(false)
                        403 -> continuation.resumeWithException(
                            RuntimeException("Forbidden")
                        )
                        401 -> continuation.resumeWithException(
                            RuntimeException("Requires authentication")
                        )
                        304 -> continuation.resumeWithException(
                            RuntimeException("Not modified")
                        )
                        else -> continuation.resumeWithException(RuntimeException("incorrect status code"))
                    }
                } catch (t:Throwable){
                    continuation.resumeWithException(t)
                }
            }
        }
    }
}