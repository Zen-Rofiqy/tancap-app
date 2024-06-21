package com.hikam.tancap.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hikam.tancap.R
import com.hikam.tancap.adapter.CatalogAdapter
import com.hikam.tancap.databinding.FragmentCatalogBinding
import com.hikam.tancap.databinding.FragmentNewsBinding
import com.hikam.tancap.viewmodel.CatalogViewModel
import com.hikam.tancap.viewmodel.ViewModelFactory

class CatalogFragment : Fragment(R.layout.fragment_catalog) {
    private val viewModel: CatalogViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.rvCatalog
        val adapter = CatalogAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.catalogs.observe(this) { catalogs ->
            catalogs?.let { adapter.submitList(it) }
        }
    }
}