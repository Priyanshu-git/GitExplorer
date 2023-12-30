package com.example.githubexplorer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.models.user.GithubUserModel
import com.example.githubexplorer.networking.ApiResult
import com.example.githubexplorer.networking.ApiStatus
import com.example.githubexplorer.repository.GitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class GitViewModel : ViewModel() {
    val repository = GitRepository()
    var currentUser = ""
    suspend fun getGitUserData(username: String): Flow<ApiResult<GithubUserModel?>> {
        return repository.getGithubUser(username)
            .onEach {
                if (it.status == ApiStatus.SUCCESS)
                    currentUser = it.data?.login!!
            }
    }

    val gitAllReposFlow = repository.gitAllReposFlow
        .asLiveData(viewModelScope.coroutineContext)
    suspend fun getGitAllReposData(username: String): LiveData<ApiResult<GithubReposModel?>> {
        return repository.getAllReposOfUser(username)
            .onStart {
                emit(ApiResult.Loading(true))
            }
            .asLiveData(viewModelScope.coroutineContext)
    }

    val gitRepoFlow = repository.gitAllReposFlow
    suspend fun getGitRepoData(username: String, repositoryName: String) {
        viewModelScope.launch {
            repository.getRepoOfUser(username, repositoryName)
        }
    }

}