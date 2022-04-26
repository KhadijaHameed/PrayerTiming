package com.sixlogs.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.sixlogs.workwallet.base.ViewModelFactory

abstract class BaseActivity<B: ViewBinding, VM: ViewModel>: AppCompatActivity() {

    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getActivityBinding(layoutInflater)
        setContentView(binding.root)
        val factory = ViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
    }

    abstract fun getActivityBinding(inflater: LayoutInflater): B
    abstract fun getViewModel(): Class<VM>

}