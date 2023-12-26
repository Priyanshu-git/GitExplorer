package com.example.githubexplorer.repository.retrofit

import com.example.githubexplorer.models.repos.GithubReposItem
import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.models.user.FollowersModel
import com.example.githubexplorer.models.user.GithubUserModel
import com.example.githubexplorer.networking.ApiResult

interface ApiInterface {
    suspend fun getGithubUser(username:String): ApiResult<GithubUserModel?>
    suspend fun getGithubUserRepository(username:String): ApiResult<GithubReposModel?>
    suspend fun getGithubUserRepository(username:String, repository:String): ApiResult<GithubReposItem?>
    suspend fun getGithubUserFollowers(username:String): ApiResult<FollowersModel?>
    suspend fun getGithubUserFollowing(username:String): ApiResult<FollowersModel?>

}
