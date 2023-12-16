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
import com.example.githubexplorer.databinding.FragmentHomeBinding
import com.example.githubexplorer.viewmodels.GitViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val gitViewModel: GitViewModel by activityViewModels()
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
            val username= binding.username.text.toString()
            lifecycleScope.launch{
                gitViewModel.getGitUserData(username)
            }
        }

        lifecycleScope.launch {
            gitViewModel.gitUserFlow.collect{
                if (it != null) {
                    Toast.makeText(requireContext(), "Profile Found!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment(it))
                }
                else
                    Toast.makeText(requireContext(), "Profile Not Found!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}