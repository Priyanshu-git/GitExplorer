package com.example.githubexplorer.views.social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubexplorer.databinding.PagerFragmentBinding
import com.example.githubexplorer.models.user.FollowersModel
import com.example.githubexplorer.networking.ApiResult
import com.example.githubexplorer.networking.ApiStatus
import com.example.githubexplorer.utils.AppUtility
import com.example.githubexplorer.viewmodels.GitViewModel
import com.example.githubexplorer.viewmodels.SocialViewModel
import kotlinx.coroutines.launch

class UsersListFragment(val position: Int, val login: String) : Fragment() {
    lateinit var binding:PagerFragmentBinding
    val viewmodel: SocialViewModel by activityViewModels()
    val gitViewModel: GitViewModel by activityViewModels()
    val adapter = SocialAdapter(this, FollowersModel())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PagerFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvList.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.rvList.adapter = adapter
        lifecycleScope.launch {
            if (position == 0) {
                viewmodel.getFollowersData(login).collect {
                    handleResponse(it)
                }
            } else if (position == 1){
                viewmodel.getFollowingData(login).collect {
                    handleResponse(it)
                }
            }
        }

    }

    private fun handleResponse(it: ApiResult<FollowersModel?>) {
        when(it.status){
            ApiStatus.SUCCESS -> {
                hideLoader()
                if (it.data!!.size>0) {
                    binding.rvList.visibility = View.VISIBLE
                    adapter.updateData(it.data)
                } else{
                    binding.rvList.visibility = View.GONE
                    if (position == 0)
                        binding.tvStatus.text = "This user has no followers"
                    else
                        binding.tvStatus.text = "This user has not followed anyone"
                }
            }
            ApiStatus.ERROR -> {
                AppUtility.showToast("Error: ${it.message}")
                hideLoader()
                binding.rvList.visibility = View.GONE
                binding.tvStatus.text = "Error: ${it.message}"
            }
            ApiStatus.LOADING -> {
                showLoader()
                binding.rvList.visibility = View.GONE
                binding.tvStatus.text = "Loading"
            }
        }
    }
    private fun showLoader(){
        binding.loader.visibility = View.VISIBLE
        binding.loader.show()
    }
    private fun hideLoader(){
        binding.loader.visibility = View.GONE
        binding.loader.hide()
    }
}