package com.example.eyecare.ui.permission

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eyecare.R
import com.example.eyecare.databinding.FragmentPermissionHandlingBinding
import com.example.eyecare.ui.utils.PermissionHelper.isAccessibilityServiceEnabled
import com.example.eyecare.ui.utils.PermissionHelper.isOverlayServiceEnabled
import com.example.eyecare.ui.utils.Utils.hasOverlayPermission
import com.example.eyecare.ui.utils.Utils.setSingleClickListener
import com.example.eyecare.ui.utils.services.OverlayService

class PermissionHandlingFragment : Fragment() {
    private lateinit var binding: FragmentPermissionHandlingBinding

    private val requestOverlayPermissionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (Settings.canDrawOverlays(requireContext())) {
            if(!isOverlayServiceEnabled(requireContext())){
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                requestAccessibilityPermissionLauncher.launch(intent)
            } else{
                findNavController().navigate(R.id.action_permissionHandlingFragment_to_homeFragment)
            }
        }
    }

    private val requestAccessibilityPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
            Log.d("Result","Result")
            if (isOverlayServiceEnabled(requireContext())) {
                findNavController().navigate(R.id.action_permissionHandlingFragment_to_homeFragment)
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionHandlingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
    }

    private fun setupLayout() {
        //todo also need to set the image accordingly
        binding.apply {
            titleText.text = getString(R.string.enable_overlay_service_for_eye_care)
            subtitle.text =
                getString(R.string.permission_is_needed_to_apply_a_blue_light_filter_for_reduced_eye_strain_and_better_comfort_especially_in_low_light_conditions)
            skipBtn.apply {
                text = context.getString(R.string.skip_for_now)
                setSingleClickListener{
                    findNavController().navigate(R.id.action_permissionHandlingFragment_to_homeFragment)
                }
            }
            allowBtn.apply {
                text = context.getString(R.string.allow)
                setSingleClickListener{
                    if (requireContext().hasOverlayPermission()) {
                        if (!isOverlayServiceEnabled(requireContext())) {
                            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            requestAccessibilityPermissionLauncher.launch(intent)
                        }
                    } else {
                        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                            data = Uri.parse("package:${requireContext().packageName}")
                        }
                        requestOverlayPermissionLauncher.launch(intent)
                    }
                }
            }
        }
    }
}