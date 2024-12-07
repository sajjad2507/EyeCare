package com.example.eyecare.ui.onboarding

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eyecare.R
import com.example.eyecare.ui.utils.Utils.setSingleClickListener
import com.example.eyecare.databinding.FragmentOnBoardingBinding
import com.example.eyecare.ui.utils.Utils
import com.example.eyecare.ui.utils.Utils.hasOverlayPermission

class OnBoardingFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardingBinding
    private var count = 0
    private val requestOverlayPermissionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (Settings.canDrawOverlays(requireContext())) {
            findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
        } else {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLayout()
    }

    private fun setUpLayout() {
        binding.nextBtn.setSingleClickListener {
//            binding.nextBtn.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue))
//            binding.nextBtn.imageTintMode = PorterDuff.Mode.MULTIPLY
            when (count) {
                0 -> {
                    count++
                    updateLayout(
                        getString(R.string.melatonin_impact),
                        getString(
                            R.string.blue_light_lowers_melatonin_levels_which_affects_your_natural_sleep_wake_cycle_and_sleep_quality
                        ),
                        R.drawable.ic_onboarding_2,
                        R.drawable.ic_indicator_2
                    )
                }

                1 -> {
                    count++
                    updateLayout(
                        getString(R.string.set_filters_for_safety),
                        getString(
                            R.string.use_filters_to_gradually_reduce_blue_light_exposure_helping_protect_your_eyes_and_support_better_sleep
                        ),
                        R.drawable.ic_onboarding_3,
                        R.drawable.ic_indicator_3
                    )
                }

                2 -> {
                    count++
                    updateLayout(
                        getString(R.string.restore_healthy_sleep),
                        getString(
                            R.string.our_app_promotes_eye_protection_and_better_sleep_helping_you_to_maintain_a_natural_sleep_cycle
                        ),
                        R.drawable.ic_onboarding_4,
                        R.drawable.ic_indicator_4
                    )
                }

                else -> {
                    if(requireContext().hasOverlayPermission()) {
                        findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
                    }
                    else{
                        handleOverlayPermission()
                    }
                }
            }
        }
    }

    private fun updateLayout(
        title: String,
        subTitle: String,
        image: Int,
        indicator: Int,
    ) {
        binding.apply {
            titleText.text = title
            subtitle.text = subTitle
            imageView.setImageResource(image)
            indicatorIcon.setImageResource(indicator)
        }
    }

    private fun handleOverlayPermission() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
            data = Uri.parse("package:${requireContext().packageName}")
        }
        requestOverlayPermissionLauncher.launch(intent)
    }

}