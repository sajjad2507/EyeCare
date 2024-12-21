package com.example.eyecare.ui.reminder.reminderlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.eyecare.R
import com.example.eyecare.databinding.FragmentReminderListBinding
import com.example.eyecare.ui.utils.Utils.launchWhenStarted
import com.example.eyecare.ui.utils.Utils.setSingleClickListener
import com.example.eyecare.ui.utils.roomdb.AppDatabase
import com.example.eyecare.ui.utils.roomdb.repository.ReminderRepository
import com.example.eyecare.ui.utils.roomdb.viewmodel.ReminderViewModel
import kotlinx.coroutines.flow.collectLatest

class ReminderListFragment : Fragment() {
    private var _binding: FragmentReminderListBinding? = null
    private val binding get() = _binding!!
    private lateinit var reminderViewModel: ReminderViewModel
    private val viewModel: ReminderListViewModel by viewModels()
    private val adapter: ReminderAdapter by lazy {
        ReminderAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = ReminderRepository(AppDatabase.getDatabase(requireContext()).reminderDao())
        reminderViewModel = ViewModelProvider(
            this, ReminderViewModel.ReminderViewModelFactory(repository)
        )[ReminderViewModel::class.java]
        setUpLayout()
        allObservers()
    }

    private fun setUpLayout() {
        binding.apply {
            addReminderBtn.setSingleClickListener {
                findNavController().navigate(R.id.action_reminderListFragment_to_addReminderFragment)
            }
            remindersRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            remindersRecyclerView.adapter = adapter
            allTypes.setSingleClickListener {
                launchWhenStarted {
                    viewModel.selectedTypePosition.emit(0)
                }
            }
            personalType.setSingleClickListener {
                launchWhenStarted {
                    viewModel.selectedTypePosition.emit(1)
                }
            }
            workTypes.setSingleClickListener {
                launchWhenStarted {
                    viewModel.selectedTypePosition.emit(2)
                }
            }
            studyTypes.setSingleClickListener {
                launchWhenStarted {
                    viewModel.selectedTypePosition.emit(3)
                }
            }
        }
    }
    private fun allObservers(){
        launchWhenStarted {
            reminderViewModel.allReminders.observe(viewLifecycleOwner){
                Log.d("Reminders",it.toString())
                adapter.submitList(it)
            }
        }
    }

//    private fun allObservers() {
//        launchWhenStarted {
//            reminderViewModel.allReminders.observe(viewLifecycleOwner) { allReminders ->
//                launchWhenStarted {
//                    viewModel.selectedTypePosition.collectLatest { selectedType ->
//                        val filteredList = when (selectedType) {
//                            0 -> allReminders
//                            1 -> allReminders.filter { it.reminderType == "Personal" }
//                            2 -> allReminders.filter { it.reminderType == "Work" }
//                            3 -> allReminders.filter { it.reminderType == "Study" }
//                            else -> allReminders
//                        }
//                        adapter.submitList(filteredList)
//                    }
//                }
//            }
//        }
//    }
}