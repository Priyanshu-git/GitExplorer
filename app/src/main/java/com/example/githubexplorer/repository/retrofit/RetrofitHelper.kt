package com.example.githubexplorer.repository.retrofit

import com.example.githubexplorer.models.repos.GithubReposItem
import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.models.user.FollowersModel
import com.example.githubexplorer.models.user.GithubUserModel

class RetrofitHelper(private val apiService: ApiService) : ApiInterface {
    override suspend fun getGithubUser(username:String): GithubUserModel {
        return apiService.getGithubUser(username)
    }

    override suspend fun getGithubUserRepository(username: String): GithubReposModel {
        return apiService.getGithubUserRepository(username)
    }

    override suspend fun getGithubUserRepository(username: String, repository:String): GithubReposItem {
        return apiService.getGithubUserRepository(username, repository)
    }

    override suspend fun getGithubUserFollowers(username: String): FollowersModel {
        return apiService.getGithubUserFollowers(username)

    }

    override suspend fun getGithubUserFollowing(username: String): FollowersModel {
        return apiService.getGithubUserFollowing(username)
    }
}
