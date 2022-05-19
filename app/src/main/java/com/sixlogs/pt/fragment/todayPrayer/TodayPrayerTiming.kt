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
import com.sixlogs.pt.activity.MainActivity
import com.sixlogs.pt.R
import com.sixlogs.pt.applicationsetting.ApplicationSetting
import com.sixlogs.pt.base.BaseFragment
import com.sixlogs.pt.base.util.alertDialog
import com.sixlogs.pt.base.util.checkInternetConnection
import com.sixlogs.pt.base.util.requestFailed
import com.sixlogs.pt.data.network.TodayPrayerAPI
import com.sixlogs.pt.data.remoteRepo.AuthRepository
import com.sixlogs.pt.data.remoteRepo.Resource
import com.sixlogs.pt.data.request.todayprayer.Timings
import com.sixlogs.pt.data.request.todayprayer.TodayPrayerRes
import com.sixlogs.pt.databinding.FragmentTodayPrayerBinding
import com.sixlogs.pt.service.PTService
import com.sixlogs.pt.storage.PTDatabase
import com.sixlogs.pt.storage.PTPreferences
import com.sixlogs.pt.storage.dao.QazaPrayerDao
import com.sixlogs.pt.storage.entities.QazaPrayerEntities
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class TodayPrayerTiming :
    BaseFragment<FragmentTodayPrayerBinding, TodayPrayerTimingViewModel, AuthRepository>(),
    View.OnClickListener {

    private lateinit var navController: NavController
    val TAG = "PT"
    private lateinit var db: PTDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = PTDatabase.getAppDatabase(requireContext())
    }
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
        setListeners()

        sendTimeRequest()
        viewModel.todayRes.observe(viewLifecycleOwner, Observer {
            binding.sk.visibility = GONE
            binding.ivRetry.visibility = GONE
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
        })


        addColumnForTodayInDB()
    }

    private fun addColumnForTodayInDB(){
     //   val gymBDatabase = PTDatabase.getAppDatabase(requireContext())

        //add onyl once
    /*  var dateListDummy  = arrayListOf<String>("13/05/2022","14/05/2022","15/05/2022","16/05/2022","17/05/2022","18/05/2022","19/05/2022")
            for (i in 0 until 7){
            val qazaPrayerDaoDao: QazaPrayerDao = db.qazaPrayerDao()
            val todayEntry = QazaPrayerEntities(0, dateListDummy.get(i),true,true,false,false,true)
            qazaPrayerDaoDao.insertPrayer(todayEntry)
        }*/
   /*  val qazaPrayerDaoDao: QazaPrayerDao = db.qazaPrayerDao()
        val todayEntry = QazaPrayerEntities(0, getTodayDate(),true,true,false,false,false)
        qazaPrayerDaoDao.insertPrayer(todayEntry)
*/
    }


    private fun getTodayDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy")
        // val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        Log.d("test", "TodayDate  $currentDate")
        return currentDate
    }


    private fun setListeners() {
        binding.ivRetry.setOnClickListener(this)
    }

    private fun sendTimeRequest() {
        if (checkInternetConnection(this.requireContext())) {
            viewModel.getTodays()
        } else {
            setTimeFromPref()
        }
    }

    private fun setTimeFromPref() {
        binding.sk.visibility = GONE
        binding.ivRetry.visibility = VISIBLE
        alertDialog(requireActivity(), resources.getString(R.string.internnetConnectionDialog), "Error")
        val fjr = PTPreferences(requireContext()).getStringValue(ApplicationSetting.FAJAR)
        val sunRise = PTPreferences(requireContext()).getStringValue(ApplicationSetting.SUNRISE)
        val duhar = PTPreferences(requireContext()).getStringValue(ApplicationSetting.DUHAR)
        val asar = PTPreferences(requireContext()).getStringValue(ApplicationSetting.ASAR)
        val maghrib = PTPreferences(requireContext()).getStringValue(ApplicationSetting.MAGHRIB)
        val isha = PTPreferences(requireContext()).getStringValue(ApplicationSetting.ISHA)

        binding.fajrrTime.text = fjr
        binding.sunriseTime.text = sunRise
        binding.dhuhrTime.text = duhar
        binding.asrTime.text = asar
        binding.maghribTime.text = maghrib
        binding.ishaTime.text = isha
        binding.todayHijriDate.text = ""
        binding.todayEnDate.text = ""

        GlobalScope.launch {
            check(TAG)
        }

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
            setTimeFromAPI(namazTimeLists)



        }
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


    private fun setTimeFromAPI(time: Timings) {
        binding.fajrrTime.text = time.Fajr
        binding.sunriseTime.text = time.Sunrise
        binding.dhuhrTime.text = time.Dhuhr
        binding.asrTime.text = time.Asr
        binding.maghribTime.text = time.Maghrib
        binding.ishaTime.text = time.Isha

        //add into preferences
   /*     PTPreferences(requireContext()).setStringValue(ApplicationSetting.FAJAR, time.Fajr)
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.SUNRISE, time.Sunrise)
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.DUHAR, time.Dhuhr)
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.ASAR, time.Asr)
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.MAGHRIB, time.Maghrib)
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.ISHA, time.Isha)
*/
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.FAJAR, "12:8")
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.DUHAR, "12:9")
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.ASAR, "12:10")
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.MAGHRIB, "12:11")
        PTPreferences(requireContext()).setStringValue(ApplicationSetting.ISHA, "12:12")


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
             R.id.iv_retry-> {
                 sendTimeRequest()
              }
        }
    }

    override fun getRepository(): AuthRepository {
        return AuthRepository(
            remoteDataSource.buildApi(TodayPrayerAPI::class.java)
        )
    }


}