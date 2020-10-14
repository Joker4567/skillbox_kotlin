package com.skillbox.fragments11.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skillbox.fragments11.fragment.OnboardingFragment
import com.skillbox.fragments11.models.OnboardingScreen

class OnboardingAdapter(
    private val screens: List<OnboardingScreen>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        Log.d("viewPager", "OnboardingAdapter getItemCount")
        return screens.size
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("viewPager", "OnboardingAdapter createFragment $position")
        val screen: OnboardingScreen = screens[position]
        return OnboardingFragment.newInstance(
            textRes = screen.textRes,
            bgColorRes = screen.bgColorRes,
            drawableRes = screen.drawableRes,
            textDescRes = screen.textDescriptionRes
        )
    }
}