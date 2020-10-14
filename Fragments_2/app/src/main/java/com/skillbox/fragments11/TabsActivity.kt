package com.skillbox.fragments11

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import com.skillbox.fragments11.adapter.OnboardingAdapter
import com.skillbox.fragments11.fragment.ConfirmationDialogFragment
import com.skillbox.fragments11.models.OnboardingScreen
import com.skillbox.fragments11.service.ArticleTag
import com.skillbox.fragments11.service.FormState
import com.skillbox.fragments11.service.ItemSelectListener
import com.skillbox.fragments11.service.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.activity_tabs.*
import kotlin.random.Random

class TabsActivity : AppCompatActivity(R.layout.activity_tabs), ItemSelectListener {

    private var state: FormState = FormState(emptyMap<String, Boolean>().toMutableMap())

    private var listArticleTag = mutableMapOf<String, Boolean>(
        ArticleTag.Intel.toString() to true,
        ArticleTag.Apple.toString() to true,
        ArticleTag.Technologies.toString() to true,
        ArticleTag.Phone.toString() to true
    )
    private var listBadge = mutableListOf<Int>(0, 0, 0)
    private val screens: List<OnboardingScreen> = listOf(
        OnboardingScreen(
            "Intel",
            textRes = R.string.onboarding_text_1,
            bgColorRes = R.color.onboarding_color_1,
            drawableRes = R.drawable.image1,
            textDescriptionRes = R.string.oboardtextdescription_text_1,
            tags = listOf(ArticleTag.Intel, ArticleTag.Technologies)
        ),
        OnboardingScreen(
            "Apple",
            textRes = R.string.onboarding_text_2,
            bgColorRes = R.color.onboarding_color_2,
            drawableRes = R.drawable.image2,
            textDescriptionRes = R.string.oboardtextdescription_text_2,
            tags = listOf(ArticleTag.Apple, ArticleTag.Phone)
        ),
        OnboardingScreen(
            "Intel",
            textRes = R.string.onboarding_text_3,
            bgColorRes = R.color.onboarding_color_3,
            drawableRes = R.drawable.image3,
            textDescriptionRes = R.string.oboardtextdescription_text_3,
            tags = listOf(ArticleTag.Intel)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = OnboardingAdapter(screens, this)
        viewPager.adapter = adapter
        viewPager.setPageTransformer(ZoomOutPageTransformer())
        viewPager.offscreenPageLimit = 1
        dots_indicator.setViewPager2(viewPager)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = screens[position].shortText
        }.attach()
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.removeBadge()
                listBadge[position] = 0
            }
        })
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action -> {
                    showDialogFragment()
                    true
                }
                else -> false
            }
        }
    }

    override fun onClick() {
        val position = Random.nextInt(0, screens.size)
        tabLayout.getTabAt(position)?.orCreateBadge?.apply {
            number += 2
            badgeGravity = BadgeDrawable.TOP_END
            listBadge[position] = number
        }
    }

    override fun setList(state: MutableMap<String, Boolean>) {
        listArticleTag = state
        var tempTags: MutableList<ArticleTag> = mutableListOf()
        state.forEach {
            when (it.key) {
                ArticleTag.Apple.toString() -> {
                    if (it.value) {
                        tempTags.add(ArticleTag.Apple)
                    }
                }
                ArticleTag.Phone.toString() -> {
                    if (it.value) {
                        tempTags.add(ArticleTag.Phone)
                    }
                }
                ArticleTag.Technologies.toString() -> {
                    if (it.value) {
                        tempTags.add(ArticleTag.Technologies)
                    }
                }
                ArticleTag.Intel.toString() -> {
                    if (it.value) {
                        tempTags.add(ArticleTag.Intel)
                    }
                }
            }
        }
        sortList(tempTags)
    }
    //Сохранение состояния сессии
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        state.save(listArticleTag, listBadge)
        outState.putParcelable(KEY_ACTIVITY, state)
    }
    //Восстановление сессии
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state = savedInstanceState.getParcelable(KEY_ACTIVITY) ?: error("unreachable")
        if(state != null) {
            listBadge = state.listBadge
            setList(state.articleTags)
            restoreBadge()
        }
    }
    //Восстановление badge.number
    private fun restoreBadge() {
        for (i in listBadge.indices) {
            if(tabLayout.getTabAt(i)?.badge?.number != null){
                if(listBadge[i] != 0)
                    tabLayout.getTabAt(i)?.badge?.number = listBadge[i]
            } else {
                if(listBadge[i] != 0) {
                tabLayout.getTabAt(i)?.orCreateBadge?.apply {
                        number = listBadge[i]
                        badgeGravity = BadgeDrawable.TOP_END
                    }
                }
            }
        }
    }
    //Сортировка элементов adapter
    private fun sortList(list: List<ArticleTag>) {
        val tempScreen: MutableList<OnboardingScreen> = mutableListOf()
        screens.forEach { onboardingScreen ->
            var flag = false
            onboardingScreen.tags.forEach {
                if (list.contains(it)) flag = true
            }
            if (flag) tempScreen.add(onboardingScreen)
        }
        val adapter = OnboardingAdapter(tempScreen, this)
        viewPager.adapter = adapter//задаём новые элементы adapter-а
        dots_indicator.setViewPager2(viewPager)//устанавливаем в indicator новую коллекцию
        dots_indicator.refreshDrawableState()//обновляем состояние индикатора
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tempScreen[position].shortText
        }.attach()
    }
    //Показать диалог фильтрации
    private fun showDialogFragment() {
        ConfirmationDialogFragment
            .newInstance(listArticleTag)
            .show(supportFragmentManager, "confirmationDialogTag")
    }
    //Скрытие диалога
    private fun hideDialog() {
        supportFragmentManager.findFragmentByTag("confirmationDialogTag")
            ?.let { it as? ConfirmationDialogFragment }
            ?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideDialog()
    }

    companion object {
        const val KEY_ACTIVITY: String = "TabsActivity"
    }
}