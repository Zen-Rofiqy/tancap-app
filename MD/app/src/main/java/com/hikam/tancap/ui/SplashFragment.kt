package com.hikam.tancap.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hikam.tancap.R

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        Handler().postDelayed({
            val navController = findNavController()
            when {
                !onBoardingFinished() -> {
                    if (navController.currentDestination?.id != R.id.viewPageFragment) {
                        navController.navigate(R.id.action_splashFragment_to_viewPageFragment)
                    }
                }
                !isLoggedIn() -> {
                    if (navController.currentDestination?.id != R.id.viewPageFragment) {
                        navController.navigate(R.id.action_splashFragment_to_viewPageFragment)
                    }
                }
                else -> {
                    if (navController.currentDestination?.id != R.id.homeFragment) {
                        navController.navigate(R.id.action_splashFragment_to_homeFragment)
                    }
                }
            }
        }, 3000)

        return view
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

    private fun isLoggedIn(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("userSession", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isLoggedIn", false)
    }
}
