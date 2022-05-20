package com.rrg.sicpa_test.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rrg.sicpa_test.R
import com.rrg.sicpa_test.databinding.FragmentHomeBinding
import com.rrg.sicpa_test.models.keys.HomeKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home), HomeClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding)
    private val adapter: HomeAdapter by lazy {
        HomeAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onButtonClick(menu: HomeKeys) {
        val action = when(menu){
            HomeKeys.SearchArticles ->{
                HomeFragmentDirections.actionHomeFragmentToArticleListFragment()
            }
            HomeKeys.MostViewed, HomeKeys.MostShared, HomeKeys.MostEmailed-> {
                HomeFragmentDirections.actionHomeFragmentToPopularArticlesFragment(menu)
            }
            else -> {
                throw IllegalArgumentException("Invalid CLick Action")
            }
        }

        findNavController().navigate(action)
    }
}