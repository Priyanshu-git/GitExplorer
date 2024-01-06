package com.example.githubexplorer.views.social

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.SocialItemBinding
import com.example.githubexplorer.models.user.FollowersModel
import com.example.githubexplorer.models.user.FollowersModelItem
import com.example.githubexplorer.models.user.GithubUserModel
import com.example.githubexplorer.networking.ApiStatus
import com.example.githubexplorer.utils.AppUtility
import com.example.githubexplorer.utils.NavigationHelper
import com.example.githubexplorer.views.ui.CustomToast
import kotlinx.coroutines.launch

class SocialAdapter(val fragment: UsersListFragment, var data: FollowersModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val binding = SocialItemBinding.inflate(LayoutInflater.from(mContext))
        return SocialViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SocialViewHolder
        if (data.size>0) {
            val model = data[position]
            setTitleAndHandle(holder, model)
        }
    }

    private fun setTitleAndHandle(holder: SocialViewHolder, model: FollowersModelItem) {
        holder.binding.tvUsername.text = model.login
        holder.binding.simpleDraweeView.setImageURI(model.avatar_url)
        holder.binding.container.setOnClickListener {
            showLoader(holder)
            fragment.gitViewModel.viewModelScope.launch {
                fragment.gitViewModel.getGitUserData(model.login)
                    .collect{
                        when (it.status) {
                            ApiStatus.SUCCESS -> {
                                openProfile(it.data)
                                hideLoader(holder)
                            }

                            ApiStatus.ERROR -> {
                                AppUtility.showToast(it.message!!)
                                hideLoader(holder)
                            }

                            ApiStatus.LOADING -> {
                                showLoader(holder)
                            }
                        }
                    }
            }

        }
    }

    private fun hideLoader(holder: SocialViewHolder) {
        holder.binding.container.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
        holder.binding.loader.visibility = ViewGroup.GONE
        holder.binding.loader.hide()
    }

    private fun showLoader(holder: SocialViewHolder) {
        holder.binding.container.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dim))
        holder.binding.loader.visibility = ViewGroup.VISIBLE
        holder.binding.loader.show()
    }

    private fun openProfile(data: GithubUserModel?) {
        if (NavigationHelper.getCurrentFragment() != "ProfileFragment") {
            NavigationHelper.openProfileFragment(data)
        }
    }

    fun updateData(it: FollowersModel?) {
        if (it != null) {
            data = it
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class SocialViewHolder(val binding: SocialItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.frameRoot.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
}