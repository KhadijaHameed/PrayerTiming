package com.sixlogs.activity

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.sixlogs.base.BaseActivity
import com.sixlogs.pt.R
import com.sixlogs.pt.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>(){

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    lateinit var bottomAppBar: BottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()
    }



    private fun initUI(){
        bottomAppBar = findViewById(R.id.bottomAppBar)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = findNavController(R.id.nav_host_fragment_container)
        navController.setGraph(R.navigation.navigation_graph)
        navController.navigate(R.id.dashboard)
        //bottomnavigationView = findViewById(R.id.bottom_navigation)
        binding.bottomNavigation.background = null

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home ->{
                    navController.navigate(R.id.dashboard)
                    true
                   // return@setOnNavigationItemSelectedListener
                }
                R.id.nav_transaction ->{
                    navController.navigate(R.id.transactionHistory)
                    true

                }
                R.id.nav_wallet ->{
                    navController.navigate(R.id.walletDetails)
                    true

                }
                R.id.nav_profile ->{
                   navController.navigate(R.id.profile2)
                    true

                }
                else -> false
            }
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun getViewModel()= MainActivityViewModel::class.java


}