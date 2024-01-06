package com.example.githubexplorer.viewmodels

import androidx.lifecycle.ViewModel
import com.example.githubexplorer.models.user.FollowersModel
import com.example.githubexplorer.networking.ApiResult
import com.example.githubexplorer.repository.GitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class SocialViewModel : ViewModel() {
    val repository = GitRepository()
    suspend fun getFollowersData(username: String): Flow<ApiResult<FollowersModel?>> {
        return repository.getFollowers(username)
            .onStart {
                emit(ApiResult.Loading(true))
            }
    }
    suspend fun getFollowingData(username: String): Flow<ApiResult<FollowersModel?>> {
        return repository.getFollowing(username)
            .onStart {
                emit(ApiResult.Loading(true))
            }
    }
}