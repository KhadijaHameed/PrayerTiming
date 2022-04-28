package com.sixlogs.pt.fragment.qazaPrayersRecords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sixlogs.pt.MainActivity
import com.sixlogs.pt.base.BaseFragment
import com.sixlogs.pt.databinding.FragmentQazaPrayerBinding
import java.lang.Exception

class QazaPrayersRecords : BaseFragment<FragmentQazaPrayerBinding, QazaPrayersRecordsViewModel>(),
    View.OnClickListener {

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            (activity as MainActivity).bottomAppBar.visibility = VISIBLE
            (activity as MainActivity).getActivityBinding(layoutInflater).bottomNavigation.menu.getItem(
                0
            ).isChecked = true
        } catch (e: Exception) {
        }
        initUI(view)

    }

    private fun initUI(view: View) {
        navController = Navigation.findNavController(view)
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentQazaPrayerBinding {
        return FragmentQazaPrayerBinding.inflate(inflater, container, false)
    }

    override fun getViewModel() = QazaPrayersRecordsViewModel::class.java

    override fun onClick(v: View) {
        when (v.id) {
            // R.id.menu_bar -> {
            // setFragmentResult("position", bundleOf("ind" to 0))
            //  }
        }
    }


}