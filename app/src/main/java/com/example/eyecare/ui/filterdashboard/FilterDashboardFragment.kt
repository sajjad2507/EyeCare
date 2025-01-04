package com.example.eyecare.ui.filterdashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.eyecare.R
import com.example.eyecare.databinding.FragmentFilterDashboardBinding
import com.example.eyecare.databinding.ItemTempLayoutBinding
import com.example.eyecare.ui.utils.Utils.launchWhenStarted
import com.example.eyecare.ui.utils.Utils.navigateBack
import com.example.eyecare.ui.utils.Utils.onBackPressed
import com.example.eyecare.ui.utils.Utils.setSingleClickListener
import com.example.eyecare.ui.utils.constants.Constants
import com.example.eyecare.ui.utils.preferences.EasyPrefs
import com.example.eyecare.ui.utils.services.OverlayService
import com.example.eyecare.ui.utils.services.TimeCheckService
import kotlinx.coroutines.flow.collectLatest

class FilterDashboardFragment : Fragment() {
    private var _binding: FragmentFilterDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilterDashboardViewModel by viewModels()
    private val adapter : FilterDashboardAdapter by lazy {
        FilterDashboardAdapter(onItemClick = { position,binding->
            onItemClicked(position,binding)
        })
    }
    private val itemsList = mutableListOf<Int>()

    init {
        itemsList.apply {
            add(R.drawable.ic_moon)
            add(R.drawable.ic_eye_care)
            add(R.drawable.ic_sunrise)
            add(R.drawable.ic_sundown)
            add(R.drawable.ic_candle)
            add(R.drawable.ic_trees_night)
            add(R.drawable.ic_tourch)
            add(R.drawable.ic_fire)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterDashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        allObservers()
        dimLevelSetup()
        intensitySetup()
        filterSwitchSetup()
        setUpPause()
        setupLayoutInitially()
        onBackPressed {
            navigateBack()
        }
    }

    private fun setupLayout(){
        binding.apply {
            itemRecyclerView.layoutManager = GridLayoutManager(requireContext(),5)
            itemRecyclerView.adapter = adapter
            adapter.submitList(itemsList)
            backIcon.setSingleClickListener {

            }
        }
    }

    private fun setupLayoutInitially() {
        if(EasyPrefs.isFilterEnabled()){
            binding.switchOverlay.isChecked = true
        }else{
            binding.switchOverlay.isChecked = false
        }
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
                Log.d("Filter",it.toString())
                binding.switchOverlay.isChecked = it
                EasyPrefs.setFilterEnabled(it)
                if(it){
                    Log.d("Start Service","Start Service")
                    OverlayService.start(requireContext())
                }else{
                    Log.d("Stop Service","Stop Service")
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

    private fun setupTemperatureLayout(temperature: String) {
        binding.apply {
            temperaturePercentage.text = temperature
        }
    }

    private fun onItemClicked(position: Int, itemBinding: ItemTempLayoutBinding) {
        when (position) {
            0 -> {
                EasyPrefs.setColorTemperature(Constants.MOON_LIGHT_VALUE)
                viewModel.setUpTemperature(Constants.MOON_LIGHT_VALUE)
            }
            1 -> {
                EasyPrefs.setColorTemperature(Constants.EYE_CARE_VALUE)
                viewModel.setUpTemperature(Constants.EYE_CARE_VALUE)
            }
            2 -> {
                EasyPrefs.setColorTemperature(Constants.SUN_RISE_LIGHT_VALUE)
                viewModel.setUpTemperature(Constants.SUN_RISE_LIGHT_VALUE)
            }
            3 -> {
                EasyPrefs.setColorTemperature(Constants.SUN_DOWN_LIGHT_VALUE)
                viewModel.setUpTemperature(Constants.SUN_DOWN_LIGHT_VALUE)
            }
            4 -> {
                EasyPrefs.setColorTemperature(Constants.CANDLE_LIGHT_VALUE)
                viewModel.setUpTemperature(Constants.CANDLE_LIGHT_VALUE)
            }
            5 -> {
                EasyPrefs.setColorTemperature(Constants.TREES_LIGHT_VALUE)
                viewModel.setUpTemperature(Constants.TREES_LIGHT_VALUE)
            }
            6 -> {
                EasyPrefs.setColorTemperature(Constants.NORMAL_LIGHT_VALUE)
                viewModel.setUpTemperature(Constants.NORMAL_LIGHT_VALUE)
            }
            7 -> {
                EasyPrefs.setColorTemperature(Constants.NORMAL_FIRE_VALUE)
                viewModel.setUpTemperature(Constants.NORMAL_FIRE_VALUE)
            }
        }
        adapter.prefObservers(itemBinding,position)
        adapter.notifyDataSetChanged()
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
                    if(fromUser){
                        viewModel.setDimLevel(progress)
                    }
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
                    if(fromUser){
                        viewModel.setIntensityLevel(progress)
                    }
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
            binding.switchOverlay.isChecked = isChecked
            Log.d("OverlaySwitchClick","Swicth$isChecked")
            EasyPrefs.setFilterEnabled(isChecked)
            if(isChecked){
                OverlayService.start(requireContext())
            }else{
                OverlayService.stop(requireContext())
            }
            viewModel.setUpSwitch(isChecked)
        }
    }

    private fun setUpPause() {
        binding.pause.setSingleClickListener {
            Log.d("TimeCheckService","TimeCheckServie")
            if(!EasyPrefs.isPauseEnable()){
                viewModel.setUpPause(false)
                startTimeCheckService(requireContext())
            } else{
                TimeCheckService.stop(requireContext())
                EasyPrefs.setPauseEnable(false)
                viewModel.setUpPause(true)
                binding.pauseTitle.text = requireContext().getString(R.string._60s_pause)
            }
        }
    }
    private fun startTimeCheckService(context: Context) {
        val serviceIntent = Intent(context, TimeCheckService::class.java)
        ContextCompat.startForegroundService(context, serviceIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onResume() {
//        super.onResume()
//        prefsObserver()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        EasyPrefs.getSecondsLive().removeObservers(viewLifecycleOwner)
//        EasyPrefs.getFilterSwitchLive().removeObservers(viewLifecycleOwner)
//    }
}