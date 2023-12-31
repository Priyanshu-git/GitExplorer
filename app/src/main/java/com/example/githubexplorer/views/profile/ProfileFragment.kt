package com.example.githubexplorer.views.profile

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.githubexplorer.databinding.FragmentProfileBinding
import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.models.user.GithubUserModel
import com.example.githubexplorer.networking.ApiStatus
import com.example.githubexplorer.utils.AppConstants
import com.example.githubexplorer.utils.AppUtility
import com.example.githubexplorer.utils.NavigationHelper
import com.example.githubexplorer.viewmodels.GitViewModel
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    val viewmodel: GitViewModel by activityViewModels()
    val spaceSize = 16
    lateinit var model: GithubUserModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = arguments?.getParcelable(AppConstants.USER_MODEL_KEY)!!

        setUpProfileData(model)
        setUpReposList(model)
        var name = model.name
        if (name.isNullOrBlank())
            name = model.login
        binding.followersContainer.setOnClickListener{
            NavigationHelper.openSocialFragment("Followers", name!!, model.login!!)
        }
        binding.followingContainer.setOnClickListener{
            NavigationHelper.openSocialFragment("Following", name!!, model.login!!)
        }

    }

    private fun setUpReposList(model: GithubUserModel) {
        val adapter = RepositoryAdapter(GithubReposModel())
        binding.rvReposList.adapter = adapter
        adapter.userName = model.login
        binding.rvReposList.addItemDecoration(object: ItemDecoration(){
            override fun getItemOffsets(outRect: Rect,view: View,parent: RecyclerView,state: RecyclerView.State) {
                with(outRect) {
                    if (parent.getChildAdapterPosition(view) == 0)
                        top = spaceSize
                    bottom = if (parent.getChildAdapterPosition(view) == adapter.itemCount -1)
                        spaceSize *2
                    else spaceSize
                    left = spaceSize
                    right = spaceSize
                }
            }
        })

        lifecycleScope.launch {
            viewmodel.getGitAllReposData(model.login!!)
                .observe(viewLifecycleOwner){
                when(it.status){
                    ApiStatus.SUCCESS ->{
                        adapter.updateData(it.data)
                        adapter.showLoader(false)
                    }

                    ApiStatus.ERROR -> {
                        AppUtility.showToast("Unable to fetch repository data")
                        adapter.showLoader(false)
                    }
                    ApiStatus.LOADING -> {
                        adapter.showLoader(true)
                    }
                }
            }
        }
    }

    private fun setUpProfileData(model: GithubUserModel) {
        binding.tvGitUserName.text = model.name
        binding.tvFollowersCount.text = model.followers.toString()
        binding.tvFollowingCount.text = model.following.toString()
        binding.imageView.setImageURI(model.avatar_url)

        if (model.bio.isNullOrBlank())
            binding.tvUserBio.visibility = View.GONE
        else
            binding.tvUserBio.text = model.bio
        binding.tvRepoCount.text = "(${model.public_repos})"
        if (model.html_url.isNullOrBlank())
            binding.profileExternalLink.visibility = View.GONE
        else {
            binding.profileExternalLink.setOnClickListener {
                AppUtility.openUrl(requireContext(), model.html_url)
            }
        }
    }
}