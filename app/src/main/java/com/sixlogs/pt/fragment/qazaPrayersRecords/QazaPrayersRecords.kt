package com.sixlogs.pt.fragment.qazaPrayersRecords

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sixlogs.pt.activity.MainActivity
import com.sixlogs.pt.adapter.QazaPrayersAdapter
import com.sixlogs.pt.base.BaseFragment
import com.sixlogs.pt.data.network.TodayPrayerAPI
import com.sixlogs.pt.data.remoteRepo.AuthRepository
import com.sixlogs.pt.databinding.FragmentQazaPrayerBinding
import com.sixlogs.pt.model.QazaPrayerModel
import com.sixlogs.pt.storage.PTDatabase
import com.sixlogs.pt.storage.dao.QazaPrayerDao
import com.sixlogs.pt.storage.entities.QazaPrayerEntities
import java.lang.Exception
import java.util.ArrayList

class QazaPrayersRecords : BaseFragment<FragmentQazaPrayerBinding, QazaPrayersRecordsViewModel, AuthRepository>(),
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

        db = PTDatabase.getAppDatabase(requireContext())

        initUI(view)
        setAdapter()
    }

    private lateinit var db: PTDatabase

    private fun setAdapter() {
        //get all qaza from db
        val qazaPrayerDaoDao: QazaPrayerDao = db.qazaPrayerDao()
         val qazaPrayersList : ArrayList<QazaPrayerModel> = qazaPrayerDaoDao.getAllQazaPrayer() as ArrayList<QazaPrayerModel>
        Log.d("testing"  , "qaza List\n $qazaPrayersList")

   var adapter = QazaPrayersAdapter(requireContext())
        adapter.setList(qazaPrayersList)
        binding.rvQazaPrayer.adapter = adapter
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

    override fun getRepository(): AuthRepository {
        return AuthRepository(
            remoteDataSource.buildApi(TodayPrayerAPI::class.java)
        )
    }

    override fun onClick(v: View) {
        when (v.id) {
            // R.id.menu_bar -> {
            // setFragmentResult("position", bundleOf("ind" to 0))
            //  }
        }
    }


}