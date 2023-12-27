package com.example.githubexplorer.views.profile

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.Shape
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.githubexplorer.databinding.RepoItemBinding
import com.example.githubexplorer.models.repos.GithubReposItem
import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.utils.AppUtility
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data[position]
        Log.d(TAG, "${model.name}, ${model.topics}")
        setTitleAndDescription(holder, model)
        setTopics(holder, model)
        setLanguage(holder, model)
        setLastupdated(holder, model)
    }

    private fun setLastupdated(holder: ViewHolder, model: GithubReposItem) {
        if (model.updated_at.isNullOrBlank()){
            holder.binding.lastUpdatedContainer.visibility = View.GONE
        } else {
            holder.binding.lastUpdatedContainer.visibility = View.VISIBLE
            holder.binding.tvLastUpdated.text = "Updated on ${AppUtility.getFormattedDate(model.updated_at)}"
        }
    }

    private fun setLanguage(holder: ViewHolder, model: GithubReposItem) {
        if (model.language.isNullOrBlank()){
            holder.binding.languageContainer.visibility = View.GONE
        } else{
            holder.binding.languageContainer.visibility = View.VISIBLE
            holder.binding.tvLanguage.text = model.language

            val drawable = ShapeDrawable(OvalShape())
            drawable.paint.color = ContextCompat.getColor(mContext, AppUtility.getLanguageColor(model.language))
            holder.binding.languageColor.background = drawable
        }
    }

    private fun setTopics(holder: ViewHolder, model: GithubReposItem) {
        if (model.topics.isEmpty()){
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

    private fun setTitleAndDescription(holder: ViewHolder, model: GithubReposItem) {
        holder.binding.tvRepoName.text = model.name

        if (!model.description.isNullOrBlank())
            holder.binding.tvDescription.text = model.description
        else
            holder.binding.tvDescription.visibility = View.GONE
    }

    fun updateData(it: GithubReposModel?) {
        if (it != null) {
            data = it
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return data.size
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