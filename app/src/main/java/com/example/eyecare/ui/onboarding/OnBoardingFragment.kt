package com.example.eyecare.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eyecare.R
import com.example.eyecare.databinding.FragmentOnBoardingBinding
import com.example.eyecare.ui.utils.PermissionHelper.isOverlayServiceEnabled
import com.example.eyecare.ui.utils.Utils.hasOverlayPermission
import com.example.eyecare.ui.utils.Utils.launchWhenStarted
import com.example.eyecare.ui.utils.Utils.setSingleClickListener
import com.example.eyecare.ui.utils.preferences.EasyPrefs
import kotlinx.coroutines.flow.collectLatest

class OnBoardingFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardingBinding
    private val viewModel: OnBoardingViewModel by viewModels()
    private var x1: Float = 0.0f
    private var x2: Float = 0.0f
    private val SWIPE_THRESHOLD = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpGestureDetector()
        setUpLayout()
        allObservers()
    }

    private fun setUpGestureDetector() {
        binding.root.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x1 = event.x
                    true
                }
                MotionEvent.ACTION_UP -> {
                    x2 = event.x
                    val deltaX = x2 - x1
                    if (Math.abs(deltaX) > SWIPE_THRESHOLD) {
                        if (deltaX > 0) {
                            onSwipeRight() // Right swipe
                        } else {
                            onSwipeLeft()  // Left swipe
                        }
                    }
                    true
                }
                else -> false
            }
        }


    }

    private fun onSwipeRight() {
        viewModel.updateCounterRight()
    }

    private fun onSwipeLeft() {
        viewModel.updateCounter()
    }

    private fun setUpLayout() {
        EasyPrefs.setOnBoardingEnable(false)
        binding.nextBtn.setSingleClickListener {
            viewModel.updateCounter()
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

    private fun allObservers(){
        launchWhenStarted {
            viewModel.count.collectLatest { count->
                when (count) {
                    0->{
                        updateLayout(
                            getString(R.string.blue_light_sleep),
                            getString(
                                R.string.studies_shows_that_screen_blue_light_before_bed_can_disrupt_sleep_making_it_harder_to_fall_asleep
                            ),
                            R.drawable.ic_onboarding_1,
                            R.drawable.ic_indicator_1
                        )
                    }
                    1 -> {
                        updateLayout(
                            getString(R.string.melatonin_impact),
                            getString(
                                R.string.blue_light_lowers_melatonin_levels_which_affects_your_natural_sleep_wake_cycle_and_sleep_quality
                            ),
                            R.drawable.ic_onboarding_2,
                            R.drawable.ic_indicator_2
                        )
                    }

                    2 -> {
                        updateLayout(
                            getString(R.string.set_filters_for_safety),
                            getString(
                                R.string.use_filters_to_gradually_reduce_blue_light_exposure_helping_protect_your_eyes_and_support_better_sleep
                            ),
                            R.drawable.ic_onboarding_3,
                            R.drawable.ic_indicator_3
                        )
                    }

                    3 -> {
                        updateLayout(
                            getString(R.string.restore_healthy_sleep),
                            getString(
                                R.string.our_app_promotes_eye_protection_and_better_sleep_helping_you_to_maintain_a_natural_sleep_cycle
                            ),
                            R.drawable.ic_onboarding_4,
                            R.drawable.ic_indicator_4
                        )
                    }

                    4 -> {
                        if (requireContext().hasOverlayPermission()
                            && isOverlayServiceEnabled(requireContext())
                        ) {
                                findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
                            } else{
                                findNavController().navigate(R.id.action_onBoardingFragment_to_permissionHandlingFragment)
                            }
                    }
                }
            }
        }
    }
}
