package com.example.eyecare.ui.filterdashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.eyecare.R
import com.example.eyecare.databinding.FragmentFilterDashboardBinding
import com.example.eyecare.databinding.ItemTempLayoutBinding
import com.example.eyecare.ui.utils.Utils.launchWhenStarted
import com.example.eyecare.ui.utils.Utils.setSingleClickListener
import com.example.eyecare.ui.utils.constants.Constants
import com.example.eyecare.ui.utils.preferences.EasyPrefs

class FilterDashboardFragment : Fragment() {
    private lateinit var binding: FragmentFilterDashboardBinding
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
        binding = FragmentFilterDashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        allObservers()
        prefObservers()
    }

    private fun setupLayout(){
        binding.apply {
            itemRecyclerView.layoutManager = GridLayoutManager(requireContext(),5)
            itemRecyclerView.adapter = adapter
            adapter.submitList(itemsList)
        }
    }
    private fun prefObservers() {
        launchWhenStarted {
            val colorTemperateLiveData = EasyPrefs.colorTemperatureLiveData()
            val colorTemperateObserver = Observer<String> { value ->
                binding.temperaturePercentage.text = value
            }
            colorTemperateLiveData.observeForever(colorTemperateObserver)
        }
    }

    private fun allObservers() {
        launchWhenStarted {

        }
    }

    private fun onItemClicked(position: Int, itemBinding: ItemTempLayoutBinding) {
        when (position) {
            0 -> {
                EasyPrefs.setColorTemperature(Constants.EYE_CARE_VALUE)
            }
            1 -> {
                EasyPrefs.setColorTemperature(Constants.MOON_LIGHT_VALUE)
            }
            2 -> {
                EasyPrefs.setColorTemperature(Constants.SUN_RISE_LIGHT_VALUE)
            }
            3 -> {
                EasyPrefs.setColorTemperature(Constants.SUN_DOWN_LIGHT_VALUE)
            }
            4 -> {
                EasyPrefs.setColorTemperature(Constants.CANDLE_LIGHT_VALUE)
            }
            5 -> {
                EasyPrefs.setColorTemperature(Constants.TREES_LIGHT_VALUE)
            }
            6 -> {
                EasyPrefs.setColorTemperature(Constants.NORMAL_LIGHT_VALUE)
            }
            7 -> {
                EasyPrefs.setColorTemperature(Constants.NORMAL_FIRE_VALUE)
            }
        }
        adapter.prefObservers(itemBinding,position)
        adapter.notifyDataSetChanged()
    }

}