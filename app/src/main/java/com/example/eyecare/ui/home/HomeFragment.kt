package com.example.eyecare.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.eyecare.databinding.FragmentHomeBinding
import com.example.eyecare.ui.utils.Utils.launchWhenStarted
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
    }

    private fun allObservers() {
        // Dim Level Observer
        launchWhenStarted {
            viewModel.dimProgressFlow.collectLatest {
                EasyPrefs.setDimLevel(it)
                binding.dimPercentage.text = "$it%"
                OverlayService.start(requireContext())
            }
        }
        // Intensity Level Observer
        launchWhenStarted {
            viewModel.intensityProgressFlow.collectLatest {
                EasyPrefs.setIntensity(it)
                binding.intensityPercentage.text = "$it%"
            }
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
}