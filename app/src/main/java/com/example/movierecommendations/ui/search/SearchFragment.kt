package com.example.movierecommendations.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movierecommendations.databinding.FragmentSearchBinding
import com.example.movierecommendations.ui.adapters.MovieAdapter
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        binding.recyclerSearch.layoutManager = LinearLayoutManager(requireContext())
        adapter = MovieAdapter(emptyList())
        binding.recyclerSearch.adapter = adapter

        searchViewModel.filteredMovies.observe(viewLifecycleOwner) {
            adapter = MovieAdapter(it)
            binding.recyclerSearch.adapter = adapter
        }

        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchViewModel.updateQuery(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}