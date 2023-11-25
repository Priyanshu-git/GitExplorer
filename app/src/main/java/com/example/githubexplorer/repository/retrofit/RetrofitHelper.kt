package com.example.githubexplorer.repository.retrofit

import com.example.githubexplorer.models.repos.GithubReposItem
import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.models.user.FollowersModel
import com.example.githubexplorer.models.user.GithubUserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RetrofitHelper(private val apiService: ApiService) : ApiInterface {
    override fun getGithubUser(username:String): Flow<GithubUserModel> {
        return flow {
            emit(apiService.getGithubUser(username))
        }
    }

    override fun getGithubUserRepository(username: String): Flow<GithubReposModel> {
        return flow {
            emit(apiService.getGithubUserRepos(username))
        }
    }

    override fun getGithubUserRepository(username: String, repository:String): Flow<GithubReposItem> {
        return flow {
            emit(apiService.getGithubUserRepository(username, repository))
        }
    }

    override fun getGithubUserFollowers(username: String): Flow<FollowersModel> {
        return flow {
            emit(apiService.getGithubUserFollowers(username))
        }
    }

    override fun getGithubUserFollowing(username: String): Flow<FollowersModel> {
        return flow {
            emit(apiService.getGithubUserFollowing(username))
        }
    }
}
