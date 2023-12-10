package com.example.githubexplorer.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.ActivityMainBinding
import com.example.githubexplorer.viewmodels.GitViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    val gitViewModel: GitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeMainUI()
    }

    private fun initializeMainUI() {
        binding.next.setOnClickListener {
            val username= binding.username.text.toString()
            lifecycleScope.launch{
                gitViewModel.getGitUserData(username)
            }
        }

        lifecycleScope.launch {
            gitViewModel.gitUserFlow.collect{
                if (it != null)
                    Toast.makeText(this@MainActivity, "Profile Found!", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this@MainActivity, "Profile Not Found!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}