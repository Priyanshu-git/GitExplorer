package com.example.githubexplorer.views.social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.FragmentSocialBinding
import com.example.githubexplorer.utils.AppConstants
import com.example.githubexplorer.viewmodels.SocialViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.ViewPagerOnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class SocialFragment : Fragment() {
    lateinit var binding: FragmentSocialBinding
    lateinit var source: String
    lateinit var name: String
    lateinit var login: String

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?,): View {
        binding = FragmentSocialBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        source = arguments?.getString(AppConstants.SOCIAL_SOURCE) ?: ""
        name = arguments?.getString(AppConstants.SOCIAL_USER) ?: ""
        login = arguments?.getString(AppConstants.SOCIAL_LOGIN) ?: ""

        setUpActionBar()
        setupViewPager()
    }

    private fun setupViewPager() {
        val pager = binding.viewpager
        val adapter = ViewPagerAdapter(this, login)
        pager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, pager, false) { tab, position ->
            when (position) {
                0 -> tab.text = requireContext().getString(R.string.followers)
                1 -> tab.text = requireContext().getString(R.string.following)
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tv = tab.customView as? TextView
                tv?.setTextColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.material_dynamic_primary50))
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tv = tab.customView as? TextView
                tv?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }


    private fun setUpActionBar() {
        val toolbar = binding.myToolbar
        toolbar.title = name
        requireActivity().setActionBar(toolbar)
        requireActivity().actionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}