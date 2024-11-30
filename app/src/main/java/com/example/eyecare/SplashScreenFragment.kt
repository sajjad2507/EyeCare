package com.example.eyecare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eyecare.databinding.FragmentSplashScreenBinding

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
    }

    private fun animationHandling() {
        binding.apply {
            loadingAnimation.playAnimation()
            loadingAnimation.postDelayed({
                loadingAnimation.cancelAnimation()
            },5000)
        }
    }
}