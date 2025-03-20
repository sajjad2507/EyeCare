package com.example.eyecare.ui.language

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eyecare.R
import com.example.eyecare.databinding.FragmentLanguageBinding
import com.example.eyecare.ui.language.adapter.LanguageAdapter
import com.example.eyecare.ui.language.datamodel.LanguageModel

class LanguageFragment : Fragment(), LanguageAdapter.LanguageSelectCallback {
    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!
    private var selectedLanguageCode: String = "en"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedLanguageCode = requireContext().getSharedPreferences(
            "appSharedPref",
            Context.MODE_PRIVATE
        ).getString("selectedLanguageCode", "en")
            .toString()
        setRCV()
        setUpLayout()
        showAd()
    }

    private fun setUpLayout() {
        binding.icLanguageSelected.setOnClickListener {
            saveLanguage()
            goNext()
        }

//        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                goNext()
//            }
//        })
    }

    private fun setRCV() {
        binding.languageRcv.layoutManager = LinearLayoutManager(requireContext())
        binding.languageRcv.adapter =
            LanguageAdapter(
                this,
                requireActivity(),
                availableLanguage(),
                null,
                selectedLanguageCode
            )
    }

    private fun showAd() {}
    private fun availableLanguage(): ArrayList<LanguageModel> {
        return arrayListOf(
            LanguageModel(
                "English",
                ContextCompat.getDrawable(
                    requireContext(),
                    com.hbb20.R.drawable.flag_united_states_of_america
                )!!,
                "en"
            ),
            LanguageModel(
                "Español",
                ContextCompat.getDrawable(requireContext(), com.hbb20.R.drawable.flag_spain)!!,
                "es"
            ),
            LanguageModel(
                "العربية",
                ContextCompat.getDrawable(
                    requireContext(),
                    com.hbb20.R.drawable.flag_saudi_arabia
                )!!,
                "ar"
            ),
            LanguageModel(
                "Português",
                ContextCompat.getDrawable(requireContext(), com.hbb20.R.drawable.flag_portugal)!!,
                "pt"
            ),
            LanguageModel(
                "Italiano",
                ContextCompat.getDrawable(requireContext(), com.hbb20.R.drawable.flag_italy)!!,
                "it"
            ),
            LanguageModel(
                "Deutsch",
                ContextCompat.getDrawable(requireContext(), com.hbb20.R.drawable.flag_germany)!!,
                "de"
            ),
            LanguageModel(
                "Français",
                ContextCompat.getDrawable(requireContext(), com.hbb20.R.drawable.flag_france)!!,
                "fr"
            ),
            LanguageModel(
                "한국어",
                ContextCompat.getDrawable(
                    requireContext(),
                    com.hbb20.R.drawable.flag_south_korea
                )!!,
                "ko"
            ),
            LanguageModel(
                "Русский",
                ContextCompat.getDrawable(
                    requireContext(),
                    com.hbb20.R.drawable.flag_russian_federation
                )!!,
                "ru"
            )
        )
    }

    private fun saveLanguage() {
        requireContext().getSharedPreferences("appSharedPref", Context.MODE_PRIVATE)
            .edit().putString("selectedLanguageCode", selectedLanguageCode).apply()
    }

    private fun goNext() {
        findNavController().navigate(R.id.action_languageFragment_to_settingFragment)
    }

    override fun languageItemSelected(language: LanguageModel, position: Int) {
        selectedLanguageCode = language.code
    }
}