package com.sixlogs.pt.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.sixlogs.pt.MainActivityViewModel
import com.sixlogs.pt.R
import com.sixlogs.pt.base.BaseActivity
import com.sixlogs.pt.data.network.TodayPrayerAPI
import com.sixlogs.pt.data.remoteRepo.AuthRepository
import com.sixlogs.pt.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel, AuthRepository>(){

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    lateinit var bottomAppBar: BottomAppBar

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            if(intent.extras!=null){
                val prayer = intent?.getStringExtra("Pr")
                Log.d("testing", "prayer $prayer")
            }else{
                Log.d("testing", "extras null")

            }
        }else{
            Log.d("testing", "intent null")

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        onNewIntent(intent)

        initUI()
        navigationTextFont(binding.bottomNavigation)

       // reminderNotification()



    }








    private fun initUI(){
        bottomAppBar = findViewById(R.id.bottomAppBar)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = findNavController(R.id.nav_host_fragment_container)
        navController.setGraph(R.navigation.navigation_graph)
        navController.navigate(R.id.todayPrayerTiming)
        //bottomnavigationView = findViewById(R.id.bottom_navigation)
        binding.bottomNavigation.background = null

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_today_prayers ->{
                    navController.navigate(R.id.todayPrayerTiming)
                    true
                   // return@setOnNavigationItemSelectedListener
                }
                R.id.nav_qaza__prayers ->{
                    navController.navigate(R.id.qazaPrayersRecords)
                    true

                }
                R.id.nav_tasbeehat ->{
                    navController.navigate(R.id.tasbeehat)
                    true

                }
               else -> false
            }
        }
    }

    private fun navigationTextFont(view: View) {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                navigationTextFont(child)
            }
        } else if (view is TextView) {
              view.typeface = ResourcesCompat.getFont(this, R.font.akaya_telivigala)
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun getViewModel()= MainActivityViewModel::class.java

    override fun getRepository(): AuthRepository {
        return AuthRepository(
            remoteDataSource.buildApi(
                TodayPrayerAPI::class.java,
            )
        )
    }


}