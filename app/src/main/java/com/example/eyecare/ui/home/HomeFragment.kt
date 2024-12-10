package com.example.eyecare.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eyecare.R
import com.example.eyecare.databinding.FragmentHomeBinding
import com.example.eyecare.ui.utils.Utils.launchWhenStarted
import com.example.eyecare.ui.utils.Utils.setSingleClickListener
import com.example.eyecare.ui.utils.constants.Constants
import com.example.eyecare.ui.utils.preferences.EasyPrefs
import com.example.eyecare.ui.utils.services.OverlayService
import kotlinx.coroutines.flow.collectLatest

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dimLevelSetup()
        intensitySetup()
        allObservers()
        filterSwitchSetup()
    }

    private fun allObservers() {
        // Dim Level Observer
        launchWhenStarted {
            viewModel.dimProgressFlow.collectLatest {
                binding.apply {
                    dimPercentage.text = "$it%"
                    dimSeekbar.progress = it
                }
                EasyPrefs.setDimLevel(it)
            }
        }
        // Intensity Level Observer
        launchWhenStarted {
            viewModel.intensityProgressFlow.collectLatest {
                binding.apply {
                    intensityPercentage.text = "$it%"
                    intensitySeekbar.progress = it
                }
                EasyPrefs.setIntensity(it)
            }
        }
        launchWhenStarted {
            viewModel.isFilterEnable.collectLatest {
                binding.switchOverlay.isChecked = it
                Log.d("Filter",it.toString())
                EasyPrefs.setFilterEnabled(it)
                if(it){
                    OverlayService.start(requireContext())
                }else{
                    OverlayService.stop(requireContext())
                }
            }
        }
        launchWhenStarted {
            viewModel.tempValueFlow.collectLatest {
                setupTemperatureLayout(it)
            }
        }
    }

    // Setup Temperature Layout
    private fun setupTemperatureLayout(temp: String) {
            when (temp) {
                Constants.EYE_CARE_VALUE -> {
                    setTemp(R.drawable.ic_eye_care,"Eye Care Light")
                }
                Constants.MOON_LIGHT_VALUE -> {
                    setTemp(R.drawable.ic_moon,"Moon Light")
                }
                Constants.SUN_RISE_LIGHT_VALUE ->{
                    setTemp(R.drawable.ic_sunrise,"Sunrise Light")
                }
                Constants.SUN_DOWN_LIGHT_VALUE ->{
                    setTemp(R.drawable.ic_sundown,"Sunset Light")
                }
                Constants.CANDLE_LIGHT_VALUE ->{
                    setTemp(R.drawable.ic_candle,"Candle Light")
                }
                Constants.TREES_LIGHT_VALUE ->{
                    setTemp(R.drawable.ic_trees_night,"Trees Light")
                }
                Constants.NORMAL_LIGHT_VALUE ->{
                    setTemp(R.drawable.ic_tourch,"Torch Light")
                }
                Constants.NORMAL_FIRE_VALUE ->{
                    setTemp(R.drawable.ic_fire,"Fire Light")
                }
            }
        binding.apply {
            selectFilterBtn.setSingleClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_filterDashboardFragment)
            }
        }
    }

    private fun setTemp(icon: Int, title: String) {
        binding.apply {
            filterIcon.setImageResource(icon)
            selectedFilterTitle.text = title
        }
    }

    // Dim Level Setup
    private fun dimLevelSetup() {
        binding.apply {
            dimSeekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    viewModel.setDimLevel(progress)
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            })
        }
    }
    // Intensity Level Setup
    private fun intensitySetup() {
        binding.apply {
            intensitySeekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    viewModel.setIntensityLevel(progress)
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            })
        }
    }
    private fun filterSwitchSetup() {
        binding.switchOverlay.setOnCheckedChangeListener{ _,isChecked->
            viewModel.setUpSwitch(binding.switchOverlay.isChecked)
            binding.switchOverlay.isChecked = isChecked
            EasyPrefs.setFilterEnabled(isChecked)
            if(isChecked){
                OverlayService.start(requireContext())
            }else{
                OverlayService.stop(requireContext())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.setTemperature()
        viewModel.setUpFilter()
    }
}