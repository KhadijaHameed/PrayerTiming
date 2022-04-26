package com.sixlogs.activity.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.sixlogs.activity.MainActivity
import com.sixlogs.base.BaseActivity
import com.sixlogs.pt.databinding.ActivitySplashBinding

class Splash: BaseActivity<ActivitySplashBinding, SplashViewModel>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashThread()
    }

    private fun splashThread(){
        object : Thread(){
            override fun run() {
                sleep(2000)
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


}