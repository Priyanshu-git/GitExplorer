package com.example.githubexplorer.utils

import android.content.Context
import android.widget.Toast

class AppUtility {
    companion object {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun showToast(context: Context, message: String, duration: Int) {
            Toast.makeText(context, message, duration).show()
        }
    }
}