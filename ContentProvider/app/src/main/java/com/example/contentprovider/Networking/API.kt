package com.example.contentprovider.Networking

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface API {
    @GET
    suspend fun downloadFile(@Url link: String): ResponseBody
}