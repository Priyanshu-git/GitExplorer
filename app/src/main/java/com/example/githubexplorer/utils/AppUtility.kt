package com.example.githubexplorer.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

class AppUtility {
    companion object {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun showToast(context: Context, message: String, duration: Int) {
            Toast.makeText(context, message, duration).show()
        }

        fun openUrl(context: Context, htmlUrl: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl))
            context.startActivity(intent)
        }
    }
}