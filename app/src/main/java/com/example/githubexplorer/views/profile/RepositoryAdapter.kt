package com.example.githubexplorer.views.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubexplorer.databinding.RepoItemBinding
import com.example.githubexplorer.models.repos.GithubReposModel

class RepositoryAdapter(var data: GithubReposModel) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    lateinit var binding: RepoItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RepoItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvRepoName.text = data[position].name
        if (!data[position].description.isNullOrBlank())
            holder.binding.tvDescription.text = data[position].description
        else
            holder.binding.tvDescription.visibility = View.GONE
    }

    fun updateData(it: GithubReposModel?) {
        if (it != null) {
            data = it
            notifyDataSetChanged()
        }
    }

    class ViewHolder(val binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cardContainer.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
}