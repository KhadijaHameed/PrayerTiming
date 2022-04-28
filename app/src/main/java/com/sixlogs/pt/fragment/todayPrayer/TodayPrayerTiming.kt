package com.sixlogs.pt.fragment.todayPrayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sixlogs.pt.MainActivity
import com.sixlogs.pt.base.BaseFragment
import com.sixlogs.pt.base.util.requestFailed
import com.sixlogs.pt.data.network.TodayPrayerAPI
import com.sixlogs.pt.data.remoteRepo.AuthRepository
import com.sixlogs.pt.data.remoteRepo.Resource
import com.sixlogs.pt.data.request.Times
import com.sixlogs.pt.data.request.TodayPrayerResponse
import com.sixlogs.pt.databinding.FragmentTodayPrayerBinding
import java.lang.Exception

class TodayPrayerTiming : BaseFragment<FragmentTodayPrayerBinding, TodayPrayerTimingViewModel, AuthRepository>(),
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


        sendTimeRequest()
        viewModel.todayRes.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    handleResponse(it.value)
                }
                is Resource.Failure ->{
                    requestFailed(it, binding.todaysContainer)

                }
            }
        })

    }

    private fun sendTimeRequest() {
        viewModel.getTodays()
    }


    private fun handleResponse(response: TodayPrayerResponse) {
        if (response!=null){
        if ( response.code==200) {
//            response.results.datetime.get(0).date
            val namazTimeLists = response.results.datetime[0].times
            setTime(namazTimeLists)
        }

        }
       /* val userId = ApplicationSetting(requireActivity()).getUserId()

        val cardDetail: GetCardDetailResponse = value

        if (cardDetail != null) {
            val cardDetailEntity = CardDetailsEntity(
                0,
                userId!!.toInt(),
                cardDetail.brand,
                cardDetail.cardId,
                cardDetail.expirationMonth,
                cardDetail.expirationYear,
                cardDetail.last4)
            val database = HomeQDatabase.getAppDatabase(requireContext())
            database.cardDetailDao().insertCardDetails(cardDetailEntity)

        }*/
    }


    private fun initUI(view: View) {
        navController = Navigation.findNavController(view)
    }


    private fun setTime(time: Times) {
        binding.fajrrTime.text = time.Fajr
        binding.sunriseTime.text = time.Sunrise
        binding.dhuhrTime.text = time.Dhuhr
        binding.asrTime.text = time.Asr
        binding.maghribTime.text = time.Maghrib
        binding.ishaTime.text = time.Isha
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTodayPrayerBinding {
        return FragmentTodayPrayerBinding.inflate(inflater, container, false)
    }

    override fun getViewModel() = TodayPrayerTimingViewModel::class.java

    override fun onClick(v: View) {
        when (v.id) {
            // R.id.menu_bar -> {
            // setFragmentResult("position", bundleOf("ind" to 0))
            //  }
        }
    }

    override fun getRepository(): AuthRepository {
       return AuthRepository(
            remoteDataSource.buildApi(TodayPrayerAPI::class.java)
       )
    }


}