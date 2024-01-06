package com.example.githubexplorer.views.social

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment, val login: String) : FragmentStateAdapter(fragment) {
    private val NUM_TABS = 2
    var followers : PagerFragment ?= null
    var following : PagerFragment? = null

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                if (followers==null)
                    followers = PagerFragment(position, login)
                return followers!!
            }
            1 -> {
                if (following==null)
                    following = PagerFragment(position, login)
                return following!!
            }
            else -> return PagerFragment(position, login)
        }
    }
}
