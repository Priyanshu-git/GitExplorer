package com.example.githubexplorer.repository.retrofit

import com.example.githubexplorer.models.repos.GithubReposItem
import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.models.user.FollowersModel
import com.example.githubexplorer.models.user.GithubUserModel
import com.example.githubexplorer.networking.ApiResult
import retrofit2.Response

class RetrofitHelper(private val apiService: ApiService) : ApiInterface {

    private suspend fun <T> callApi(apiCall: suspend () -> Response<T>): ApiResult<T?> {
        try {
            val result = apiCall.invoke()
            if (result.isSuccessful) {
                return ApiResult.Success(result.body())
            } else {
                if (result.code() == 404)
                    return ApiResult.Error("Not Found")
                return ApiResult.Error(result.message())
            }
        } catch (e: Exception) {
            return ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getGithubUser(username:String): ApiResult<GithubUserModel?> {
        return callApi { apiService.getGithubUser(username) }
    }

    override suspend fun getGithubUserRepository(username: String): ApiResult<GithubReposModel?> {
        return callApi { apiService.getGithubUserRepository(username) }
    }

    override suspend fun getGithubUserRepository(username: String, repository:String): ApiResult<GithubReposItem?> {
        return callApi { apiService.getGithubUserRepository(username,repository) }
    }

    override suspend fun getGithubUserFollowers(username: String): ApiResult<FollowersModel?> {
        return callApi {apiService.getGithubUserFollowers(username)}
    }

    override suspend fun getGithubUserFollowing(username: String): ApiResult<FollowersModel?> {
        return callApi {apiService.getGithubUserFollowing(username)}
    }
}
