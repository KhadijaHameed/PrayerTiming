package com.sixlogs.pt.fragment.tasbeehat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sixlogs.pt.activity.MainActivity
import com.sixlogs.pt.R
import com.sixlogs.pt.activity.model.SliderData
import com.sixlogs.pt.adapter.SliderAdapter
import com.sixlogs.pt.base.BaseFragment
import com.sixlogs.pt.data.network.TodayPrayerAPI
import com.sixlogs.pt.data.remoteRepo.AuthRepository
import com.sixlogs.pt.databinding.FragmentTasbeehatBinding
import com.smarteist.autoimageslider.SliderView


class Tasbeehat : BaseFragment<FragmentTasbeehatBinding, TasbeehatViewModel, AuthRepository>(),
    View.OnClickListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        //make list
        val sliderDataArrayList: ArrayList<SliderData> = ArrayList()
        // adding the urls inside array list
        sliderDataArrayList.add(SliderData(R.drawable.tas0))
//        sliderDataArrayList.add(SliderData(R.drawable.tas2))
        sliderDataArrayList.add(SliderData(R.drawable.tas3))
        sliderDataArrayList.add(SliderData(R.drawable.tas4))
        sliderDataArrayList.add(SliderData(R.drawable.tas5))
        setAdapter(sliderDataArrayList)

    }

    private fun setAdapter(sliderDataArrayList: ArrayList<SliderData>) {
        // passing this array list inside our adapter class.
        // passing this array list inside our adapter class.
        val adapter = SliderAdapter(requireActivity(), sliderDataArrayList)

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        binding.slider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        // below method is used to
        // setadapter to sliderview.

        // below method is used to
        // setadapter to sliderview.
        binding.slider.setSliderAdapter(adapter)

        // below method is use to set
        // scroll time in seconds.

        // below method is use to set
        // scroll time in seconds.
        binding.slider.scrollTimeInSec = 3

        // to set it scrollable automatically
        // we use below method.

        // to set it scrollable automatically
        // we use below method.
        binding.slider.isAutoCycle = true

        // to start autocycle below method is used.

        // to start autocycle below method is used.
        binding.slider.startAutoCycle()
    }


    private fun initUI(view: View) {
        navController = Navigation.findNavController(view)
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTasbeehatBinding {
        return FragmentTasbeehatBinding.inflate(inflater, container, false)
    }

    override fun getViewModel() = TasbeehatViewModel::class.java

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