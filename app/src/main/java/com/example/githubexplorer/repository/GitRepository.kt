package com.example.githubexplorer.repository

import com.example.githubexplorer.models.user.GithubUserModel
import com.example.githubexplorer.repository.retrofit.RetrofitBuilder
import com.example.githubexplorer.repository.retrofit.RetrofitHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class GitRepository {
    private val apiHelper = RetrofitHelper(RetrofitBuilder.apiService)

    private val _gitUserFlow = MutableSharedFlow<GithubUserModel?>()
    val gitUserFlow = _gitUserFlow.asSharedFlow()
    suspend fun getGithubUser(username: String) {
        _gitUserFlow.emit(apiHelper.getGithubUser(username))
    }

}