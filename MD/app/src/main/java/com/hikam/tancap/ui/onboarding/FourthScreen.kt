package com.hikam.tancap.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hikam.tancap.R
import com.hikam.tancap.databinding.FragmentFourthScreenBinding
import com.hikam.tancap.ui.WelcomeActivity

class FourthScreen : Fragment() {

    private var _binding: FragmentFourthScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFourthScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.finish.setOnClickListener {
            onBoardingFinished()
            startActivity(Intent(requireContext(), WelcomeActivity::class.java))
            activity?.finish()  // Finish the OnboardingActivity
        }
        return view
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
