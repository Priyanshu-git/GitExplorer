package com.example.githubexplorer.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.FragmentHomeBinding
import com.example.githubexplorer.models.user.GithubUserModel
import com.example.githubexplorer.utils.AppConstants
import com.example.githubexplorer.viewmodels.GitViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val gitViewModel: GitViewModel by activityViewModels()
    private var currentModel: GithubUserModel? = null
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
        binding.next.setOnClickListener {
            val username = binding.username.text.toString()
            lifecycleScope.launch {
                if (username == gitViewModel.currentUser && currentModel!=null) {
                    openProfile(currentModel)
                } else
                    gitViewModel.getGitUserData(username)
            }
        }

        lifecycleScope.launch {
            gitViewModel.gitUserFlow.collect {
                if (it != null) {
                    Toast.makeText(requireContext(), "Profile Found!", Toast.LENGTH_SHORT).show()
                    openProfile(it)
                } else
                    Toast.makeText(requireContext(), "Profile Not Found!", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    private fun openProfile(it: GithubUserModel?) {
        val bundle = Bundle()
        bundle.putParcelable(AppConstants.USER_MODEL_KEY, it)
        currentModel = it
        findNavController().navigate(R.id.profileFragment, bundle)
    }
}