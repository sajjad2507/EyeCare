package com.example.eyecare.ui.filterdashboard

import FilterDashboardAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.R
import com.example.eyecare.databinding.FragmentFilterDashboardBinding
import com.example.eyecare.databinding.FragmentHomeBinding
import com.example.eyecare.ui.home.HomeFragmentViewModel
import com.example.eyecare.ui.utils.Utils.launchWhenStarted

class FilterDashboardFragment : Fragment() {
    private lateinit var binding: FragmentFilterDashboardBinding
    private val viewModel: FilterDashboardViewModel by viewModels()
    private val adapter : FilterDashboardAdapter by lazy {
        FilterDashboardAdapter()
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
    }

    private fun setupLayout(){
        binding.apply {
            itemRecyclerView.layoutManager = GridLayoutManager(requireContext(),5)
            itemRecyclerView.adapter = adapter
        }
    }
    private fun allObservers() {
        launchWhenStarted {
            viewModel.items.observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        }
    }

}