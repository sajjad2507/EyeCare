package com.example.eyecare.ui.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.eyecare.R
import com.example.eyecare.databinding.DialogTimePickerBinding
import com.example.eyecare.databinding.FragmentHomeBinding
import com.example.eyecare.ui.utils.Utils
import com.example.eyecare.ui.utils.Utils.launchWhenStarted
import com.example.eyecare.ui.utils.Utils.setSingleClickListener
import com.example.eyecare.ui.utils.Utils.showToast
import com.example.eyecare.ui.utils.constants.Constants
import com.example.eyecare.ui.utils.preferences.EasyPrefs
import com.example.eyecare.ui.utils.services.OverlayService
import com.example.eyecare.ui.utils.services.TimeCheckService
import com.example.eyecare.ui.utils.workmanager.OverlayWorker
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeFragmentViewModel by viewModels()
    private var backPressedTime: Long = 0
    private val doubleBackToExitPressedMessage = "Press back again to exit"
    private val postNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            if(!EasyPrefs.isPauseEnable()){
                viewModel.startTimer(60000)
                viewModel.setUpPause(false)
                startTimeCheckService(requireContext())
            } else{
                viewModel.stopTimer()
                TimeCheckService.stop(requireContext())
                EasyPrefs.setPauseEnable(false)
                viewModel.setUpPause(true)
                binding.pauseText.text = requireContext().getString(R.string._60s_pause)
            }
        } else {
            requireContext().showToast("Notification Permission Required")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dimLevelSetup()
        intensitySetup()
        allObservers()
        filterSwitchSetup()
        setUpPause()
        setupLayoutInitially()
        setUpAutoTimer()

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (backPressedTime + 2000 > System.currentTimeMillis()) {
                        requireActivity().finish()
                        return
                    } else {
                        Toast.makeText(
                            requireContext(),
                            doubleBackToExitPressedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    backPressedTime = System.currentTimeMillis()
                }
            })
    }

    private fun setupLayoutInitially() {
        if(EasyPrefs.isFilterEnabled()){
            binding.switchOverlay.isChecked = true
        }else{
            binding.switchOverlay.isChecked = false
        }
        binding.apply {
            menu.setSingleClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
            }
            if(EasyPrefs.getSeconds() == 60){
                pauseText.text = requireContext().getString(R.string._60s_pause)
            }
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
        launchWhenStarted {
            viewModel.timeRemaining.observe(viewLifecycleOwner) { secondsRemaining ->
                if (secondsRemaining > 0) {
                    binding.pauseText.text = "00 : ${secondsRemaining}"
                    OverlayService.stop(requireContext())
                } else {
                    binding.pauseText.text = requireContext().getString(R.string._60s_pause)
                    binding.switchOverlay.isChecked = true
                    OverlayService.start(requireContext())
                }
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
                    if (fromUser) {
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
                    if (fromUser) {
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
        binding.pauseTimerLayout.setSingleClickListener {
            if(Utils.checkAndroidVersion()){
                if(!EasyPrefs.isPauseEnable()){
                    viewModel.setUpPause(false)
                    startTimeCheckService(requireContext())
                    viewModel.startTimer(60000)
                } else{
                    TimeCheckService.stop(requireContext())
                    viewModel.stopTimer()
                    EasyPrefs.setPauseEnable(false)
                    viewModel.setUpPause(true)
                    binding.pauseText.text = requireContext().getString(R.string._60s_pause)
                }
            }
            else {
                if(ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED){
                    Log.d("TimeCheckService","TimeCheckServie")
                    if(!EasyPrefs.isPauseEnable()){
                        viewModel.startTimer(60000)
                        viewModel.setUpPause(false)
                        startTimeCheckService(requireContext())
                    } else{
                        TimeCheckService.stop(requireContext())
                        viewModel.stopTimer()
                        EasyPrefs.setPauseEnable(false)
                        viewModel.setUpPause(true)
                        binding.pauseText.text = requireContext().getString(R.string._60s_pause)
                    }
                } else{
                    postNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun startTimeCheckService(context: Context) {
        val serviceIntent = Intent(context, TimeCheckService::class.java)
         ContextCompat.startForegroundService(context, serviceIntent)
    }
    private fun setUpAutoTimer() {
        binding.autoTimer.setSingleClickListener {
            dialogSetup()
        }
    }

    private fun dialogSetup() {
        val dialogBinding = DialogTimePickerBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext(), R.style.FullWidthDialog)
            .setView(dialogBinding.root).create()
        dialogBinding.apply {
            doneBtn.setSingleClickListener {
                // Retrieve time in 12-hour format
                val startHour = startTimePicker.hour
                val startMinute = startTimePicker.minute
                val startPeriod = if (startHour >= 12) "PM" else "AM"
                val formattedStartHour = if (startHour == 0 || startHour == 12) 12 else startHour % 12

                val endHour = endTimePicker.hour
                val endMinute = endTimePicker.minute
                val endPeriod = if (endHour >= 12) "PM" else "AM"
                val formattedEndHour = if (endHour == 0 || endHour == 12) 12 else endHour % 12

                // Optional: Show a Toast with the selected times
                Toast.makeText(
                    context,
                    "Start Time: $formattedStartHour:$startMinute $startPeriod\nEnd Time: $formattedEndHour:$endMinute $endPeriod",
                    Toast.LENGTH_LONG
                ).show()

                // Schedule tasks with the selected times
                scheduleOverlayService(
                    requireContext(),
                    formattedStartHour,
                    startMinute,
                    startPeriod,
                    formattedEndHour,
                    endMinute,
                    endPeriod
                )

                dialog.dismiss()
            }
        }


        dialog.show()
    }

    private fun scheduleOverlayService(
        context: Context,
        startHour: Int,
        startMinute: Int,
        startPeriod: String,
        endHour: Int,
        endMinute: Int,
        endPeriod: String
    ) {
        // Convert 12-hour format with AM/PM to 24-hour format
        val startHour24 = if (startPeriod == "PM" && startHour != 12) startHour + 12 else if (startPeriod == "AM" && startHour == 12) 0 else startHour
        val endHour24 = if (endPeriod == "PM" && endHour != 12) endHour + 12 else if (endPeriod == "AM" && endHour == 12) 0 else endHour

        val currentTime = System.currentTimeMillis()

        // Calculate delay for the start and stop workers
        val startCalendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, startHour24)
            set(Calendar.MINUTE, startMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val endCalendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, endHour24)
            set(Calendar.MINUTE, endMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // Handle scheduling for the next day if necessary
        if (startCalendar.timeInMillis < currentTime) {
            startCalendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        if (endCalendar.timeInMillis < currentTime) {
            endCalendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        val startDelay = startCalendar.timeInMillis - currentTime
        val endDelay = endCalendar.timeInMillis - currentTime

        // Schedule the "START" worker
        val startRequest = OneTimeWorkRequestBuilder<OverlayWorker>()
            .setInitialDelay(startDelay, TimeUnit.MILLISECONDS)
            .setInputData(workDataOf("ACTION" to "START"))
            .build()

        // Schedule the "STOP" worker
        val stopRequest = OneTimeWorkRequestBuilder<OverlayWorker>()
            .setInitialDelay(endDelay, TimeUnit.MILLISECONDS)
            .setInputData(workDataOf("ACTION" to "STOP"))
            .build()

        // Enqueue the work requests
        WorkManager.getInstance(context).enqueue(startRequest)
        WorkManager.getInstance(context).enqueue(stopRequest)

        Log.d("OverlayWorker", "Scheduled START at ${startCalendar.time}")
        Log.d("OverlayWorker", "Scheduled STOP at ${endCalendar.time}")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        Log.d("OnResume","OnResume")
        viewModel.setUpFilter()
        viewModel.setUpTemperature()
    }
}