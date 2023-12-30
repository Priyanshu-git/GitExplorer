package com.example.githubexplorer.views.profile

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.githubexplorer.databinding.NoDataItemBinding
import com.example.githubexplorer.databinding.RepoItemBinding
import com.example.githubexplorer.models.repos.GithubReposItem
import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.utils.AppUtility
import com.example.githubexplorer.views.ui.TagChip

class RepositoryAdapter(var data: GithubReposModel) : RecyclerView.Adapter<ViewHolder>() {
    companion object{
        private const val VT_REPO_ITEM = 0
        private const val VT_NO_DATA = 1
        private const val VT_LOADER = 2
    }

    var userName: String? = ""
    lateinit var mContext:Context
    private val TAG = "RepositoryAdapter"
    private var showLoader = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        when(viewType) {
            VT_REPO_ITEM -> {
                val binding = RepoItemBinding.inflate(LayoutInflater.from(mContext))
                return RepoViewHolder(binding)
            }

            VT_NO_DATA ->{
                val binding = NoDataItemBinding.inflate(LayoutInflater.from(mContext))
                return NoDataViewHolder(binding)
            }
            else ->{
                val binding = NoDataItemBinding.inflate(LayoutInflater.from(mContext))
                return NoDataViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder.itemViewType) {
            VT_REPO_ITEM -> {
                holder as RepoViewHolder
                val model = data[position]
                Log.d(TAG, "${model.name}, ${model.topics}")
                setTitleAndDescription(holder,model)
                setTopics(holder, model)
                setLanguage(holder, model)
                setLastupdated(holder,model)
            }

            VT_NO_DATA ->{
                holder as NoDataViewHolder
                if (showLoader) {
                    holder.binding.loader.show()
                    holder.binding.loader.visibility = View.VISIBLE
                    holder.binding.tvStatus.text = "Loading"
                } else{
                    holder.binding.tvStatus.text = "$userName doesn't have any public repositories yet."
                    holder.binding.loader.hide()
                    holder.binding.loader.visibility = View.GONE
                }
            }
        }
    }

    private fun setLastupdated(holder: RepoViewHolder, model: GithubReposItem) {
        if (model.updated_at.isNullOrBlank()){
            holder.binding.lastUpdatedContainer.visibility = View.GONE
        } else {
            holder.binding.lastUpdatedContainer.visibility = View.VISIBLE
            holder.binding.tvLastUpdated.text = "Updated on ${AppUtility.getFormattedDate(model.updated_at)}"
        }
    }

    private fun setLanguage(holder: RepoViewHolder, model: GithubReposItem) {
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

    private fun setTopics(holder: RepoViewHolder, model: GithubReposItem) {
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

    private fun setTitleAndDescription(holder: RepoViewHolder, model: GithubReposItem) {
        holder.binding.tvRepoName.text = model.name

        if (!model.description.isNullOrBlank())
            holder.binding.tvDescription.text = model.description
        else
            holder.binding.tvDescription.visibility = View.GONE
    }

    fun showLoader(show: Boolean) {
        showLoader = show
    }
    fun updateData(it: GithubReposModel?) {
        if (it != null) {
            data = it
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        if (data.size==0)
            return 1
        else return data.size
    }

    override fun getItemViewType(position: Int): Int {
        if (data.size==0)
            return VT_NO_DATA
        else return VT_REPO_ITEM
    }

    class RepoViewHolder(val binding: RepoItemBinding) : ViewHolder(binding.root) {
        init {
            binding.cardContainer.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
    class NoDataViewHolder(val binding: NoDataItemBinding) : ViewHolder(binding.root) {
        init {
            binding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
}