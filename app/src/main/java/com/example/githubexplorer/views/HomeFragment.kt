package com.example.githubexplorer.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.githubexplorer.views.ui.CustomToast
import com.example.githubexplorer.databinding.FragmentHomeBinding
import com.example.githubexplorer.models.user.GithubUserModel
import com.example.githubexplorer.networking.ApiStatus
import com.example.githubexplorer.utils.AppUtility
import com.example.githubexplorer.utils.NavigationHelper
import com.example.githubexplorer.viewmodels.GitViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val gitViewModel: GitViewModel by activityViewModels()
    private var currentModel: GithubUserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationHelper.setUpNavController(findNavController())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeMainUI()
    }

    private fun initializeMainUI() {
        binding.btnNext.setOnClickListener {
            showLoader()
            val username = binding.username.text.toString().trim()
            lifecycleScope.launch {
                if (username == gitViewModel.currentUser && currentModel != null) {
                    openProfile(currentModel)
                    hideLoader()
                } else {
                    gitViewModel.getGitUserData(username)
                        .collect {
                            when (it.status) {
                                ApiStatus.SUCCESS -> {
                                    AppUtility.showToast("Profile Found!", CustomToast.LENGTH_SHORT)
                                    openProfile(it.data)
                                    hideLoader()
                                }

                                ApiStatus.ERROR -> {
                                    AppUtility.showToast(it.message!!)
                                    hideLoader()
                                }

                                ApiStatus.LOADING -> showLoader()
                            }
                        }
                }
            }
        }
    }

    private fun openProfile(it: GithubUserModel?) {
        if (NavigationHelper.getCurrentFragment() != "ProfileFragment") {
            currentModel = it
            NavigationHelper.openProfileFragment(it)
        }
    }

    private fun showLoader(){
        binding.loader.visibility = View.VISIBLE
        binding.loader.show()
        binding.imgNext.visibility = View.GONE
    }
    private fun hideLoader(){
        binding.loader.visibility = View.GONE
        binding.loader.hide()
        binding.imgNext.visibility = View.VISIBLE
    }
}