package com.example.eyecare.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eyecare.R
import com.example.eyecare.databinding.FragmentSettingBinding
import com.example.eyecare.ui.reminder.reminderlist.ReminderListViewModel
import com.example.eyecare.ui.utils.Utils.setSingleClickListener


class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReminderListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLayout()
    }

    private fun setUpLayout() {
        binding.apply {
            goBtn.setSingleClickListener{
                findNavController().navigate(R.id.action_settingFragment_to_reminderListFragment)
            }
        }
    }
}