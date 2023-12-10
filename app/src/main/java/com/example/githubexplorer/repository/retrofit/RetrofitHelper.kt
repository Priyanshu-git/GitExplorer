package com.example.githubexplorer.repository.retrofit

import com.example.githubexplorer.models.repos.GithubReposItem
import com.example.githubexplorer.models.repos.GithubReposModel
import com.example.githubexplorer.models.user.FollowersModel
import com.example.githubexplorer.models.user.GithubUserModel

class RetrofitHelper(private val apiService: ApiService) : ApiInterface {
    override suspend fun getGithubUser(username:String): GithubUserModel? {
        return try {
            apiService.getGithubUser(username)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getGithubUserRepository(username: String): GithubReposModel? {
        return try {
            apiService.getGithubUserRepository(username)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getGithubUserRepository(username: String, repository:String): GithubReposItem? {
        return try {
            apiService.getGithubUserRepository(username, repository)
        } catch (e:Exception){
            null
        }
    }

    override suspend fun getGithubUserFollowers(username: String): FollowersModel? {
        return try {
            apiService.getGithubUserFollowers(username)
        }catch (e:Exception){
            null
        }
    }

    override suspend fun getGithubUserFollowing(username: String): FollowersModel? {
        return try {
            apiService.getGithubUserFollowing(username)
        } catch (e: Exception){
            null
        }
    }
}
