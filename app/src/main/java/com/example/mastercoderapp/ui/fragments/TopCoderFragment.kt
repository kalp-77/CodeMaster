package com.example.mastercoderapp.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mastercoderapp.R
import com.example.mastercoderapp.data.model.ContestDetailsItem
import com.example.mastercoderapp.databinding.LeetCodeFragmentBinding
import com.example.mastercoderapp.databinding.TopCoderFragmentBinding
import com.example.mastercoderapp.ui.adapters.AtCoderAdapter
import com.example.mastercoderapp.ui.adapters.LeetCodeAdapter
import com.example.mastercoderapp.ui.adapters.TopCoderAdapter
import com.example.mastercoderapp.ui.viewModels.LeetCodeViewModel
import com.example.mastercoderapp.ui.viewModels.TopCoderViewModel
import kotlinx.android.synthetic.main.leet_code_fragment.*
import kotlinx.android.synthetic.main.top_coder_fragment.*

class TopCoderFragment : Fragment() {

    private lateinit var topCoderViewModel: TopCoderViewModel
    // data binding
    private var _binding: TopCoderFragmentBinding? = null
    private val binding get() = _binding!!
    // initialise adapter
    private lateinit var adapter: TopCoderAdapter
    private var contestArticlesOn = mutableListOf<ContestDetailsItem>()
    private var contestArticlesFu = mutableListOf<ContestDetailsItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TopCoderFragmentBinding.inflate(inflater, container, false)
        topCoderViewModel = ViewModelProvider(this)[TopCoderViewModel::class.java]
        binding.apply {
            topCoderRecycleOn.apply {
                topCoderRecycleOn.layoutManager = LinearLayoutManager(activity)
                topCoderRecycleFut.layoutManager = LinearLayoutManager(activity)
            }
            topCoderViewModel.contestLiveData.observe(this@TopCoderFragment.viewLifecycleOwner) {
                if (it != null) {
                    for(element in it) {
                        if (element.status == "BEFORE") {
                            contestArticlesFu.add(element)
                            adapter = TopCoderAdapter(this@TopCoderFragment, contestArticlesFu)
                            topCoderRecycleFut.adapter = adapter
                            adapter.notifyDataSetChanged()
                        } else {
                            contestArticlesOn.add(element)
                            adapter = TopCoderAdapter(this@TopCoderFragment, contestArticlesOn)
                            topCoderRecycleOn.adapter = adapter
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