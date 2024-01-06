package com.example.githubexplorer.utils

import android.os.Bundle
import androidx.navigation.NavController
import com.example.githubexplorer.R
import com.example.githubexplorer.models.user.GithubUserModel

class NavigationHelper {
    companion object {
        private lateinit var navController: NavController
        fun setUpNavController(controller: NavController) {
            navController = controller
        }
        fun getCurrentFragment(): String{
            return navController.currentDestination?.label.toString()
        }
        fun openProfileFragment(it: GithubUserModel?) {
            if (getCurrentFragment() != AppConstants.PROFILE_FRAGMENT) {
                val bundle = Bundle()
                bundle.putParcelable(AppConstants.USER_MODEL_KEY, it)
                navController.navigate(R.id.profileFragment, bundle)
            }
        }

        fun openSocialFragment(from: String, user: String, login: String) {
            if (getCurrentFragment() != AppConstants.SOCIAL_FRAGMENT) {
                val bundle = Bundle()
                bundle.putString(AppConstants.SOCIAL_SOURCE, from)
                bundle.putString(AppConstants.SOCIAL_USER, user)
                bundle.putString(AppConstants.SOCIAL_LOGIN, login)
                navController.navigate(R.id.socialFragment, bundle)
            }
        }

    }
}