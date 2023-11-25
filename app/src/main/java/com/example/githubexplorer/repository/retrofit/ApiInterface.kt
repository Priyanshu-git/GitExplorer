package com.example.githubexplorer.repository.retrofit

import com.example.githubexplorer.models.repos.GithubReposItem
import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.models.user.FollowersModel
import com.example.githubexplorer.models.user.GithubUserModel
import kotlinx.coroutines.flow.Flow

interface ApiInterface {
    fun getGithubUser(username:String): Flow<GithubUserModel>
    fun getGithubUserRepository(username:String): Flow<GithubReposModel>
    fun getGithubUserRepository(username:String, repository:String): Flow<GithubReposItem>

    fun getGithubUserFollowers(username:String): Flow<FollowersModel>
    fun getGithubUserFollowing(username:String): Flow<FollowersModel>

}
