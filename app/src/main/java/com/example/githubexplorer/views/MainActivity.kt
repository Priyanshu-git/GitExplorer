package com.example.githubexplorer.views

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.ActivityMainBinding
import com.example.githubexplorer.viewmodels.GitViewModel
import java.lang.Exception

class MainActivity : FragmentActivity() {
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
        onBackPressedDispatcher.addCallback{
            val controller = Navigation.findNavController(binding.navHostFragment)
            try {
                if (controller.currentDestination?.label!! == "HomeFragment")
                    finishAffinity()
                else
                    controller.popBackStack()
            } catch (e:Exception){
                controller.popBackStack()
            }
        }
    }
}