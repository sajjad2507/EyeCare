package com.example.eyecare.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
            menuText.setSingleClickListener {
                findNavController().navigate(R.id.action_settingFragment_to_homeFragment)
            }
            goBtn.setSingleClickListener {
                findNavController().navigate(R.id.action_settingFragment_to_reminderListFragment)
            }
            language.setSingleClickListener {
                findNavController().navigate(R.id.action_settingFragment_to_languageFragment)
            }
            privacyPolicy.setSingleClickListener {
                setPrivacyPolicy()
            }
            rating.setSingleClickListener {
                setRating()
            }
            shareApp.setSingleClickListener {
                setShare()
            }
            about.setSingleClickListener {
                setAbout()
            }
            faqs.setSingleClickListener {
                setFaqs()
            }
        }
    }

    private fun setPrivacyPolicy() {
        Toast.makeText(requireContext(), "Suppose to set privacy policy", Toast.LENGTH_SHORT).show()
    }

    private fun setRating() {
        Toast.makeText(requireContext(), "Suppose to set rating", Toast.LENGTH_SHORT).show()
    }

    private fun setShare() {
        Toast.makeText(requireContext(), "Suppose to set rating", Toast.LENGTH_SHORT).show()
    }

    private fun setAbout() {
        Toast.makeText(requireContext(), "Suppose to set rating", Toast.LENGTH_SHORT).show()
    }

    private fun setFaqs() {
        Toast.makeText(requireContext(), "Suppose to set FAQs", Toast.LENGTH_SHORT).show()
    }
}