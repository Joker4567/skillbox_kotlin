package ru.skillbox.dependency_injection.data

import okhttp3.Interceptor
import okhttp3.Response
import ru.skillbox.dependency_injection.BuildConfig
import javax.inject.Inject

class AppVersionInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("android_ver", BuildConfig.VERSION_NAME)
                .build()

        val requestBuilder = original.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

