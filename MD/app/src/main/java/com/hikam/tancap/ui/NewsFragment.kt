package com.hikam.tancap.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hikam.tancap.R
import com.hikam.tancap.adapter.NewsAdapter
import com.hikam.tancap.databinding.FragmentNewsBinding
import com.hikam.tancap.viewmodel.NewsViewModel
import com.hikam.tancap.viewmodel.ViewModelFactory

class NewsFragment : Fragment(R.layout.fragment_news) {
    private val viewModel: NewsViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.rvReview
        val adapter = NewsAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.news.observe(viewLifecycleOwner) { articles ->
            articles?.let { adapter.submitList(it) }
        }
    }

}
