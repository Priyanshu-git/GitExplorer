package com.example.githubexplorer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubexplorer.repository.GitRepository
import kotlinx.coroutines.launch

class GitViewModel : ViewModel() {
    val repository = GitRepository()
    val gitUserFlow = repository.gitUserFlow
    suspend fun getGitUserData(username: String) {
        viewModelScope.launch {
            repository.getGithubUser(username)
        }
    }

}