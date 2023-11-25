package com.example.githubexplorer.repository.retrofit

import com.example.githubexplorer.models.repos.GithubRepos
import com.example.githubexplorer.models.user.GithubUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RetrofitHelper(private val apiService: ApiService) : ApiInterface {
    override fun getGithubUser(username:String): Flow<GithubUser> {
        return flow {
            emit(apiService.getGithubUser(username))
        }
    }

    override fun getGithubUserRepos(username: String): Flow<GithubRepos> {
        return flow {
            emit(apiService.getGithubUserRepos(username))
        }
    }
}
