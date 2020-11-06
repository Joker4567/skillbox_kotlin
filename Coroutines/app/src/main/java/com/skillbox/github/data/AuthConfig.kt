package com.skillbox.github.data

import net.openid.appauth.ResponseTypeValues

object AuthConfig {

    const val AUTH_URI = "https://github.com/login/oauth/authorize"
    const val TOKEN_URI = "https://github.com/login/oauth/access_token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "user,repo"

    const val CLIENT_ID = "b799ffe52cda6193c04f"
    const val CLIENT_SECRET = "26cd51f6bcacb41e25f003750ee287ae7564a7ae"
    const val CALLBACK_URL = "skillbox://skillbox.ru/callback"
}