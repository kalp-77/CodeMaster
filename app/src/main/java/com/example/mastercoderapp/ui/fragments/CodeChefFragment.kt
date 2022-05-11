package com.example.mastercoderapp.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mastercoderapp.data.model.ContestDetailsItem
import com.example.mastercoderapp.databinding.CodeChefFragmentBinding
import com.example.mastercoderapp.ui.activities.LoginActivity
import com.example.mastercoderapp.ui.activities.UserDetailActivity
import com.example.mastercoderapp.ui.adapters.CodeChefAdapter
import com.example.mastercoderapp.ui.adapters.CodeForcesAdapter
import com.example.mastercoderapp.ui.viewModels.CodeChefViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.*

class CodeChefFragment : Fragment() {

    // initialize viewModel
    private lateinit var codechefViewModel: CodeChefViewModel
    // data binding
    private var _binding: CodeChefFragmentBinding? = null
    private val binding get() = _binding!!
    // initialise adapter
    lateinit var adapter: CodeChefAdapter
    // graph variables
    private var yrating = ArrayList<Int>()
    private lateinit var lineChart: LineChart
    private var ysize = 0
    private var contestArticlesFut = mutableListOf<ContestDetailsItem>()
    private var contestArticlesOn = mutableListOf<ContestDetailsItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        _binding = CodeChefFragmentBinding.inflate(inflater, container, false)
        codechefViewModel = ViewModelProvider(this)[CodeChefViewModel::class.java]
        binding.apply {
            codechefRecyclerOn.apply {
                codechefRecyclerOn.layoutManager = LinearLayoutManager(activity)
                codechefRecyclerFut.layoutManager = LinearLayoutManager(activity)
            }
            codechefViewModel.message.observe(this@CodeChefFragment.viewLifecycleOwner) {
                if (it == "failure") {
                    Toast.makeText(requireContext(), "failed", Toast.LENGTH_LONG)
                    activity?.let{
                        val intent = Intent (it, UserDetailActivity::class.java)
                        it.startActivity(intent)
                    }
                }

            }
            codechefViewModel.boardsLiveData.observe(this@CodeChefFragment.viewLifecycleOwner) {
                if (it != null) {
                    boardsProgressBar.visibility = View.GONE
                    re.visibility = View.VISIBLE
                    context?.let { it1 -> Glide.with(it1).load(it.avatar).into(pf) }
                    user.text = it.username
                    stars.text = it.stars
                    currRating.text = "Rating : " + it.rating
                    maxRating.text = "Max Rating : " + it.maxRating
                    maxRank.text = "Country Rank : " + it.countryRank.toString()
                    minRank.text = "Global Rank : " + it.globalRank.toString()
                    div.text = it.div
                    for (i in it.contests) {
                        yrating.add(i)
                    }
                    ysize = yrating.size
                    Log.d("kalp", "${yrating.size}")
                    lineChart = binding.line
                    initLineChart()
                    lineChart()
                }
                else{
                    Toast.makeText(requireContext(),"Invalid user name",Toast.LENGTH_LONG).show()
                    activity?.let{
                        val intent = Intent (it, UserDetailActivity::class.java)
                        it.startActivity(intent)
                    }
                }
            }
            codechefViewModel.contestLiveData.observe(this@CodeChefFragment.viewLifecycleOwner) {
                if (it != null) {
                    for(element in it) {
                        if (element.status == "BEFORE") {
                            contestArticlesFut.add(element)
                            adapter = CodeChefAdapter(this@CodeChefFragment, contestArticlesFut)
                            codechefRecyclerFut.adapter = adapter

                        } else {
                            contestArticlesOn.add(element)
                            adapter = CodeChefAdapter(this@CodeChefFragment, contestArticlesOn)
                            codechefRecyclerOn.adapter = adapter
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }

        }

        return binding.root
    }

    private fun initLineChart() {
//       hide grid lines
        lineChart.axisLeft.setDrawGridLines(true)
        val xAxis: XAxis = lineChart.xAxis

        xAxis.setDrawGridLines(true)
        xAxis.setDrawAxisLine(true)
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.isHighlightPerTapEnabled = false

        //remove right y-axis
        lineChart.axisRight.isEnabled = false

        //remove legend
        lineChart.legend.isEnabled = false
        lineChart.xAxis.isEnabled = true

        //remove description label
        lineChart.description.isEnabled = false

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.setDrawLabels(true)
    }
    private fun lineChart() {
        Log.d("kalp", "$ysize")
        val lineEntry = ArrayList<Entry>()
        for(i in 0 until ysize) {
            val rating = yrating[i]
            lineEntry.add(Entry(i.toFloat(), rating.toFloat()))
        }
        val lineDataset = LineDataSet(lineEntry,"First")
        lineDataset.setColors(Color.BLACK)
        lineDataset.valueTextColor = Color.BLUE
        lineDataset.setDrawFilled(true)

        val data = LineData(lineDataset)
        lineChart.data = data
        lineChart.setBackgroundColor(Color.WHITE)
        lineDataset.isHighlightEnabled = false
        lineDataset.valueTextColor = Color.BLACK
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

