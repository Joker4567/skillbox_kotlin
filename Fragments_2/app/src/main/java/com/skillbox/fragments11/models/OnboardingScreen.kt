package com.skillbox.fragments11.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.skillbox.fragments11.service.ArticleTag

data class OnboardingScreen(
    val shortText:String,
    @StringRes val textRes: Int,
    @ColorRes val bgColorRes: Int,
    @DrawableRes val drawableRes: Int,
    @StringRes val textDescriptionRes:Int,
    val tags:List<ArticleTag>
)