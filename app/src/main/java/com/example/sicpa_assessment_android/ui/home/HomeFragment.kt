package com.example.sicpa_assessment_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sicpa_assessment_android.R
import com.example.sicpa_assessment_android.databinding.FragmentHomeBinding
import com.example.sicpa_assessment_android.shared.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setupUI() {
        binding.searchLayout.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        binding.mostViewedLayout.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mostViewedArticleFragment)
        }
        binding.mostSharedLayout.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mostSharedArticleFragment)
        }
        binding.mostEmailedLayout.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mostEmailedArticleFragment)
        }
    }
}