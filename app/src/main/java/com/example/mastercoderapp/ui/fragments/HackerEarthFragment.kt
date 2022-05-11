package com.example.mastercoderapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mastercoderapp.R
import com.example.mastercoderapp.data.model.ContestDetailsItem
import com.example.mastercoderapp.databinding.AtCoderFragmentBinding
import com.example.mastercoderapp.databinding.HackerEarthFragmentBinding
import com.example.mastercoderapp.ui.adapters.AtCoderAdapter
import com.example.mastercoderapp.ui.adapters.HackerEarthAdapter
import com.example.mastercoderapp.ui.viewModels.AtCoderViewModel
import com.example.mastercoderapp.ui.viewModels.HackerEarthViewModel

class HackerEarthFragment : Fragment() {

    private lateinit var hackerEarthViewModel: HackerEarthViewModel
    private var _binding: HackerEarthFragmentBinding? = null
    private val binding get() = _binding!!

    // initialise adapter
    private lateinit var adapter: HackerEarthAdapter
    private var contestArticlesOn = mutableListOf<ContestDetailsItem>()
    private var contestArticlesFu = mutableListOf<ContestDetailsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HackerEarthFragmentBinding.inflate(inflater, container, false)
        hackerEarthViewModel = ViewModelProvider(this)[HackerEarthViewModel::class.java]
        binding.apply {
            hackerEarthRecyclerOn.apply {
                hackerEarthRecyclerOn.layoutManager = LinearLayoutManager(activity)
                hackerEarthRecyclerFut.layoutManager = LinearLayoutManager(activity)

            }
            hackerEarthViewModel.contestLiveData.observe(this@HackerEarthFragment.viewLifecycleOwner) {
                if (it != null) {
                    for(element in it) {
                        if (element.status == "BEFORE") {
                            contestArticlesFu.add(element)
                            adapter = HackerEarthAdapter(this@HackerEarthFragment, contestArticlesFu)
                            hackerEarthRecyclerFut.adapter = adapter
                            adapter.notifyDataSetChanged()
                        } else {
                            contestArticlesOn.add(element)
                            adapter =HackerEarthAdapter(this@HackerEarthFragment, contestArticlesOn)
                            hackerEarthRecyclerOn.adapter = adapter
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}