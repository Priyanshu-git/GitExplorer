package com.example.githubexplorer.repository.retrofit

import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.models.repos.GithubReposItem
import com.example.githubexplorer.models.user.FollowersModel
import com.example.githubexplorer.models.user.GithubUserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{username}")
    suspend fun getGithubUser(@Path("username") username: String): Response<GithubUserModel?>

    @GET("users/{username}/repos")
    suspend fun getGithubUserRepository(@Path("username") username: String): Response<GithubReposModel?>

    @GET("repos/{username}/{repo}")
    suspend fun getGithubUserRepository(@Path("username") username: String, @Path("repo") repo: String): Response<GithubReposItem?>

    @GET("users/{username}/followers")
    suspend fun getGithubUserFollowers(@Path("username") username: String): Response<FollowersModel?>

    @GET("users/{username}/following")
    suspend fun getGithubUserFollowing(@Path("username") username: String): Response<FollowersModel?>
}
