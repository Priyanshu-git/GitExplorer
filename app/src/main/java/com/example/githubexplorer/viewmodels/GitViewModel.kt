package com.example.githubexplorer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubexplorer.repository.GitRepository
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class GitViewModel : ViewModel() {
    val repository = GitRepository()

    val gitUserFlow = repository.gitUserFlow.distinctUntilChanged()
    suspend fun getGitUserData(username: String) {
        viewModelScope.launch {
            repository.getGithubUser(username)
        }
    }

    val gitAllReposFlow = repository.gitAllReposFlow
    suspend fun getGitAllReposData(username: String) {
        viewModelScope.launch {
            repository.getAllReposOfUser(username)
        }
    }

    val gitRepoFlow = repository.gitAllReposFlow
    suspend fun getGitRepoData(username: String, repositoryName: String) {
        viewModelScope.launch {
            repository.getRepoOfUser(username, repositoryName)
        }
    }

}