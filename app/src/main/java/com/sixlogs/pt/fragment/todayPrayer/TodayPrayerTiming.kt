package com.sixlogs.pt.fragment.todayPrayer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.work.*
import com.sixlogs.homeq.applicationsetting.ApplicationSetting
import com.sixlogs.pt.MainActivity
import com.sixlogs.pt.base.BaseFragment
import com.sixlogs.pt.base.util.requestFailed
import com.sixlogs.pt.data.network.TodayPrayerAPI
import com.sixlogs.pt.data.remoteRepo.AuthRepository
import com.sixlogs.pt.data.remoteRepo.Resource
import com.sixlogs.pt.data.request.todayprayer.Timings
import com.sixlogs.pt.data.request.todayprayer.TodayPrayerRes
import com.sixlogs.pt.databinding.FragmentTodayPrayerBinding
import com.sixlogs.pt.service.PTService
import com.sixlogs.pt.storage.PTPreferences
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class TodayPrayerTiming :
    BaseFragment<FragmentTodayPrayerBinding, TodayPrayerTimingViewModel, AuthRepository>(),
    View.OnClickListener {

    private lateinit var navController: NavController
    val TAG = "PT"

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
            binding.sk.visibility = GONE
            when (it) {
                is Resource.Success -> {
                    Log.d("test", "code ${it.value.code} ${it.value.data}")
                    handleResponse(it.value)
                }
                is Resource.Failure -> {
                    Log.d("test", "code error 404  ${it.errorCode.toString()}")
                    requestFailed(it, binding.todaysContainer)

                }
            }
            /*  }else{
                  Log.d("test", "code error   ${it.data.toString()}")
              }*/
        })

    }

    private fun sendTimeRequest() {
        viewModel.getTodays()
    }


    @SuppressLint("SetTextI18n")
    private fun handleResponse(response: TodayPrayerRes) {
        if (response != null) {

//            response.results.datetime.get(0).date
            val namazTimeLists = response.data.timings
            val day = response.data.date.hijri.day
            val month = response.data.date.hijri.month.ar
            val year = response.data.date.hijri.year
            val hijri = " $day $month $year"
            binding.todayHijriDate.text = "\uD83C\uDF19$hijri"
            binding.todayEnDate.text = "~${response.data.date.readable}~"
            setTime(namazTimeLists)



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



    suspend fun check(workName: String) {
        Log.d(TAG, "check: $workName")
        val workManager = WorkManager.getInstance()
        val workInfos = workManager.getWorkInfosForUniqueWork(workName).await()
        if (workInfos.size == 1) {
            val workInfo = workInfos[0]

            if (workInfo.state == WorkInfo.State.BLOCKED || workInfo.state == WorkInfo.State.ENQUEUED || workInfo.state == WorkInfo.State.RUNNING) {
                Log.d(TAG, "check isAlive")
            } else {
                Log.d(TAG, "check isDead")
                startBackgroundJob()

            }
        } else {
            Log.d(TAG, "check notFound")
            startBackgroundJob()
            //  Timber.d("notFound")
        }
    }

    private  fun startBackgroundJob() {

        val uploadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<PTService>().build()
        //val uploadWorkRequest: PeriodicWorkRequest.Builder = PeriodicWorkRequestBuilder<PTService>()
        // (1, TimeUnit.HOURS, 15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(requireContext()).enqueue(uploadWorkRequest)
    }

    private fun initUI(view: View) {
        navController = Navigation.findNavController(view)
    }


    private fun setTime(time: Timings) {
        binding.fajrrTime.text = time.Fajr
        binding.sunriseTime.text = time.Sunrise
        binding.dhuhrTime.text = time.Dhuhr
        binding.asrTime.text = time.Asr
        binding.maghribTime.text = time.Maghrib
        binding.ishaTime.text = time.Isha

        //add into preferences
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.FAJAR, time.Fajr)
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.DUHAR, time.Dhuhr)
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.ASAR, time.Asr)
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.MAGHRIB, time.Maghrib)
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.ISHA, time.Isha)

    /*
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.FAJAR, "17:30")
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.DUHAR, "17:31")
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.ASAR, "17:32")
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.MAGHRIB, "17:33")
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.ISHA, "17:34")*/


        GlobalScope.launch {
            check(TAG)
        }

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