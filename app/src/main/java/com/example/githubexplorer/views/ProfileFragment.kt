package com.example.githubexplorer.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.githubexplorer.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    val args: ProfileFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvGitUserName.text = args.githubUserModel.name
        binding.tvFollowersCount.text = args.githubUserModel.followers.toString()
        binding.tvFollowingCount.text = args.githubUserModel.following.toString()
//        binding.tvRepoCount.text = args.githubUserModel.public_repos.toString()
        binding.imageView.setImageURI(args.githubUserModel.avatar_url)
    }

}