package com.example.githubexplorer.repository.retrofit

import com.example.githubexplorer.models.repos.GithubReposItem
import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.models.user.FollowersModel
import com.example.githubexplorer.models.user.GithubUserModel

interface ApiInterface {
    suspend fun getGithubUser(username:String): GithubUserModel?
    suspend fun getGithubUserRepository(username:String): GithubReposModel?
    suspend fun getGithubUserRepository(username:String, repository:String): GithubReposItem?
    suspend fun getGithubUserFollowers(username:String): FollowersModel?
    suspend fun getGithubUserFollowing(username:String): FollowersModel?

}
