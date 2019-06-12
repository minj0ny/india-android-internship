package com.example.user.newsapi

import android.support.v4.app.ActivityCompat.invalidateOptionsMenu
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.example.user.newsapi.ui.fragment.BusinessFragment
import com.example.user.newsapi.ui.fragment.NewsFragment
import com.example.user.newsapi.ui.fragment.TechnologyFragment

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                NewsFragment()
            }
            1 -> {
                TechnologyFragment()
            }
            else -> {
                return BusinessFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Sports"
            1 -> "Science"
            else -> {
                return "Business"
            }
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }
}