package com.example.eyecare.ui.reminder.addreminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eyecare.R
import com.example.eyecare.databinding.FragmentAddReminderBinding
import com.example.eyecare.ui.utils.Utils.setSingleClickListener
import com.example.eyecare.ui.utils.roomdb.AppDatabase
import com.example.eyecare.ui.utils.roomdb.model.RemindersModel
import com.example.eyecare.ui.utils.roomdb.repository.ReminderRepository
import com.example.eyecare.ui.utils.roomdb.viewmodel.ReminderViewModel


class AddReminderFragment : Fragment() {
    private var _binding: FragmentAddReminderBinding? = null
    private lateinit var reminderViewModel: ReminderViewModel
    private val binding get() = _binding!!
    private val reminderType = arrayOf(
        "Personal",
        "Work",
        "Study"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddReminderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = ReminderRepository(AppDatabase.getDatabase(requireContext()).reminderDao())
        reminderViewModel = ViewModelProvider(
            this,
            ReminderViewModel.ReminderViewModelFactory(repository)
        )[ReminderViewModel::class.java]
        setupLayout()
    }

    private fun setupLayout() {
        binding.apply {
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, reminderType)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            selectTypeSpinner.adapter = adapter

            selectTypeSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        (view as TextView).also {
                            it.setTextColor(requireActivity().getColor(R.color.white))
                        }

                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Another callback interface
                    }
                }
            saveImageBtn.setSingleClickListener {
                val reminderModel = RemindersModel(
                    reminderTitle = headingTitle.text.toString(),
                    reminderType = selectTypeSpinner.selectedItem.toString(),
                    reminderDesc = descTitle.text.toString()
                )
                reminderViewModel.insert(reminderModel)
            }

        }
    }
}