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
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.FragmentHomeBinding
import com.example.githubexplorer.models.user.GithubUserModel
import com.example.githubexplorer.networking.ApiStatus
import com.example.githubexplorer.utils.AppConstants
import com.example.githubexplorer.utils.AppUtility
import com.example.githubexplorer.viewmodels.GitViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val gitViewModel: GitViewModel by activityViewModels()
    private var currentModel: GithubUserModel? = null

    val navController
        get() = findNavController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            val username = binding.username.text.toString()
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
        if (navController.currentDestination?.label != "ProfileFragment") {
            val bundle = Bundle()
            bundle.putParcelable(AppConstants.USER_MODEL_KEY, it)
            currentModel = it
            navController.navigate(R.id.profileFragment, bundle)
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