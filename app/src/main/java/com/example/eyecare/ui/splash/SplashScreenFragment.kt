package com.example.eyecare.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.eyecare.R
import com.example.eyecare.databinding.FragmentSplashScreenBinding
import com.example.eyecare.ui.utils.Utils
import com.example.eyecare.ui.utils.Utils.hasOverlayPermission
import com.example.eyecare.ui.utils.preferences.EasyPrefs
import com.example.eyecare.ui.utils.services.OverlayService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {
    private lateinit var binding : FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationHandling()
        navigateAfterDelay()
    }
    private fun animationHandling() {
        binding.apply {
            loadingAnimation.playAnimation()
            loadingAnimation.postDelayed({
                loadingAnimation.cancelAnimation()
            },5000)
        }
    }
    private fun navigateAfterDelay() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(5000)
            if(EasyPrefs.isOnBoardingEnable()){
                findNavController().navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
            } else {
                if(Utils.checkAndroidVersion()){
                    if(requireContext().hasOverlayPermission()) {
                        findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
                    } else{
                        findNavController().navigate(R.id.action_splashScreenFragment_to_permissionHandlingFragment)
                    }
                } else{
                    if(Utils.isAccessibilityServiceEnabled(requireContext(), OverlayService::class.java)){
                        findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
                    } else{
                        findNavController().navigate(R.id.action_splashScreenFragment_to_permissionHandlingFragment)
                    }
                }
            }
        }
    }

}