package com.example.githubexplorer.repository

import com.example.githubexplorer.models.repos.GithubReposItem
import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.models.user.GithubUserModel
import com.example.githubexplorer.networking.ApiResult
import com.example.githubexplorer.repository.retrofit.RetrofitBuilder
import com.example.githubexplorer.repository.retrofit.RetrofitHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow

class GitRepository {
    private val apiHelper = RetrofitHelper(RetrofitBuilder.apiService)
    suspend fun getGithubUser(username: String): Flow<ApiResult<GithubUserModel?>> {
        return flow{
            emit(apiHelper.getGithubUser(username))
        }
    }

    private val _gitAllRepoFlow = MutableSharedFlow<ApiResult<GithubReposModel?>>()
    val gitAllReposFlow = _gitAllRepoFlow.asSharedFlow()
    suspend fun getAllReposOfUser(username: String): Flow<ApiResult<GithubReposModel?>> {
        return flow{
            emit(apiHelper.getGithubUserRepository(username))
        }
    }

    private val _gitRepoFlow = MutableSharedFlow<ApiResult<GithubReposItem?>>()
    val gitRepoFlow = _gitRepoFlow.asSharedFlow()
    suspend fun getRepoOfUser(username: String, repositoryName:String) {
        _gitRepoFlow.emit(apiHelper.getGithubUserRepository(username, repositoryName))
    }

}