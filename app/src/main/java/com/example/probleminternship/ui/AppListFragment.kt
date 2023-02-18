package com.example.probleminternship.ui

import androidx.lifecycle.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.probleminternship.AppDataAdapter
import com.example.probleminternship.database.AppDatabase
import com.example.probleminternship.databinding.FragmentAppListBinding
import com.example.probleminternship.viewmodels.AppListViewModel
import com.example.probleminternship.viewmodels.AppListViewModelFactory

class AppListFragment : Fragment() {

    private var _binding: FragmentAppListBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase

    private lateinit var listAppsViewModel: AppListViewModel

    private var adapter: AppDataAdapter = AppDataAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAppListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getDatabase(requireContext())
        val viewModelFactory = activity?.let { AppListViewModelFactory(it.application, db) }
        if (viewModelFactory != null) {
            this.listAppsViewModel = ViewModelProvider(this,
                viewModelFactory) [AppListViewModel::class.java]

            binding.appsList.adapter = adapter
            createAppsList()
        }
    }

    private fun createAppsList() {
        val apps = listAppsViewModel.appList

        apps.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}