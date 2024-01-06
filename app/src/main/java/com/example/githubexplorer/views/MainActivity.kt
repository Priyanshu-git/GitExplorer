package com.example.githubexplorer.views

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.example.githubexplorer.views.ui.CustomToast
import com.example.githubexplorer.R
import com.example.githubexplorer.databinding.ActivityMainBinding
import com.example.githubexplorer.utils.AppConstants
import com.example.githubexplorer.utils.AppUtility
import com.example.githubexplorer.utils.NavigationHelper
import com.example.githubexplorer.viewmodels.GitViewModel
import java.lang.Exception

class MainActivity : FragmentActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomToast.initialize(this)
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
                if (NavigationHelper.getCurrentFragment() == AppConstants.HOME_FRAGMENT)
                    finishAffinity()
                else
                    controller.popBackStack()
            } catch (e:Exception){
                e.printStackTrace()
                AppUtility.showToast(e.message.toString())
            }
        }
    }
}