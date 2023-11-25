package com.example.githubexplorer.repository.retrofit

import com.example.githubexplorer.models.repos.GithubRepos
import com.example.githubexplorer.models.user.GithubUser
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{username}")
    suspend fun getGithubUser(@Path("username") username: String): GithubUser

    @GET("users/{username}/repos")
    suspend fun getGithubUserRepos(@Path("username") username: String): GithubRepos
}
