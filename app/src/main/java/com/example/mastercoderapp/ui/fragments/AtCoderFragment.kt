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
import com.example.mastercoderapp.databinding.AtCoderFragmentBinding
import com.example.mastercoderapp.databinding.TopCoderFragmentBinding
import com.example.mastercoderapp.ui.adapters.AtCoderAdapter
import com.example.mastercoderapp.ui.adapters.TopCoderAdapter
import com.example.mastercoderapp.ui.viewModels.AtCoderViewModel
import com.example.mastercoderapp.ui.viewModels.TopCoderViewModel

class AtCoderFragment : Fragment() {
    private lateinit var atCoderViewModel: AtCoderViewModel
    private var _binding: AtCoderFragmentBinding? = null
    private val binding get() = _binding!!
    // initialise adapter
    private lateinit var adapter: AtCoderAdapter
    private var contestArticlesOn = mutableListOf<ContestDetailsItem>()
    private var contestArticlesFu = mutableListOf<ContestDetailsItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AtCoderFragmentBinding.inflate(inflater, container, false)
        atCoderViewModel = ViewModelProvider(this)[AtCoderViewModel::class.java]
        binding.apply {
            atCoderRecycleOn.apply {
                atCoderRecycleOn.layoutManager = LinearLayoutManager(activity)
                atCoderRecycleFu.layoutManager = LinearLayoutManager(activity)
            }
            atCoderViewModel.contestLiveData.observe(this@AtCoderFragment.viewLifecycleOwner) {
                if (it != null) {
                    for(element in it) {
                        if (element.in24Hours == "No") {
                            contestArticlesFu.add(element)
                            adapter = AtCoderAdapter(this@AtCoderFragment, contestArticlesFu)
                            atCoderRecycleFu.adapter = adapter
                            adapter.notifyDataSetChanged()
                        } else {
                            contestArticlesOn.add(element)
                            adapter = AtCoderAdapter(this@AtCoderFragment, contestArticlesOn)
                            atCoderRecycleOn.adapter = adapter
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