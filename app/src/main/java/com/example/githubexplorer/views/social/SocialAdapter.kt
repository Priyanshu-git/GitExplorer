package com.example.githubexplorer.views.social

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubexplorer.databinding.SocialItemBinding
import com.example.githubexplorer.models.user.FollowersModel
import com.example.githubexplorer.models.user.FollowersModelItem
import com.example.githubexplorer.utils.AppUtility

class SocialAdapter(var data: FollowersModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        holder.binding.card.setOnClickListener {
            AppUtility.showToast(model.login)
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
            binding.card.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
}