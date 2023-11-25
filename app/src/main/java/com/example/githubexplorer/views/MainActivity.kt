package com.example.githubexplorer.views

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.githubexplorer.R
import com.example.githubexplorer.repository.retrofit.RetrofitHelper
import com.example.githubexplorer.repository.retrofit.RetrofitBuilder
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val apiHelper = RetrofitHelper(RetrofitBuilder.apiService)
        lifecycleScope.launch {
            apiHelper.getGithubUserRepository("priyanshu-git").collect{
                Log.d(TAG, "onCreate: $it")
            }
        }
    }
}