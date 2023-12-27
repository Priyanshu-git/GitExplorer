package com.example.githubexplorer.views.profile

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubexplorer.databinding.RepoItemBinding
import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.views.ui.TagChip

class RepositoryAdapter(var data: GithubReposModel) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    lateinit var binding: RepoItemBinding
    lateinit var mContext:Context
    private val TAG = "RepositoryAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        binding = RepoItemBinding.inflate(LayoutInflater.from(mContext))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data[position]
        Log.d(TAG, "${model.name}, ${model.topics}")
        holder.binding.tvRepoName.text = model.name

        if (!model.description.isNullOrBlank())
            holder.binding.tvDescription.text = model.description
        else
            holder.binding.tvDescription.visibility = View.GONE

        if (model.topics.size == 0){
            holder.binding.flexboxTags.visibility = View.GONE
        } else {
            holder.binding.flexboxTags.visibility = View.VISIBLE
            holder.binding.flexboxTags.removeAllViews()
            model.topics.forEach {
                holder.binding.flexboxTags.addView(TagChip(mContext, it))
            }
            holder.binding.flexboxTags.refreshDrawableState()
        }
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