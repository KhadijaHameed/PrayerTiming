package com.sixlogs.pt.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.sixlogs.pt.R
import com.sixlogs.pt.data.remoteRepo.BaseRepository
import com.sixlogs.pt.data.remoteRepo.RemoteDataSource

abstract class BaseActivity<B: ViewBinding, VM: ViewModel,R : BaseRepository>: AppCompatActivity() {

    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getActivityBinding(layoutInflater)
        setContentView(binding.root)
        val factory = ViewModelFactory(getRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
    }

    abstract fun getActivityBinding(inflater: LayoutInflater): B
    abstract fun getViewModel(): Class<VM>
    abstract fun getRepository(): R

}