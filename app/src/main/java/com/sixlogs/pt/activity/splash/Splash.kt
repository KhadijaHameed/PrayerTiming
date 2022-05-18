package com.sixlogs.pt.activity.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.sixlogs.pt.activity.MainActivity
import com.sixlogs.pt.base.BaseActivity
import com.sixlogs.pt.data.network.TodayPrayerAPI
import com.sixlogs.pt.data.remoteRepo.AuthRepository
import com.sixlogs.pt.databinding.ActivitySplashBinding

class Splash: BaseActivity<ActivitySplashBinding, SplashViewModel, AuthRepository>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashThread()
    }

    private fun splashThread(){
        object : Thread(){
            override fun run() {
                sleep(3000)
                startMainActivity()
            }
        }.start()
    }

    private fun startMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun getActivityBinding(inflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(inflater)
    }

    override fun getViewModel() = SplashViewModel::class.java

    override fun getRepository(): AuthRepository {
        return AuthRepository(
            remoteDataSource.buildApi(
                TodayPrayerAPI::class.java,
            )
        )
    }


}