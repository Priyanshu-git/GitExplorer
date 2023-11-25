package com.example.githubexplorer.repository.retrofit

import com.example.githubexplorer.models.repos.GithubRepos
import com.example.githubexplorer.models.user.GithubUser
import kotlinx.coroutines.flow.Flow

interface ApiInterface {
    fun getGithubUser(username:String): Flow<GithubUser>
    fun getGithubUserRepos(username:String): Flow<GithubRepos>
}
