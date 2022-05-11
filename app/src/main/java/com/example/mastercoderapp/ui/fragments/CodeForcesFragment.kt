package com.example.mastercoderapp.ui.fragments

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mastercoderapp.R
import com.example.mastercoderapp.data.model.ContestDetailsItem
import com.example.mastercoderapp.databinding.CodeChefFragmentBinding
import com.example.mastercoderapp.databinding.CodeForcesFragmentBinding
import com.example.mastercoderapp.ui.activities.UserDetailActivity
import com.example.mastercoderapp.ui.adapters.CodeChefAdapter
import com.example.mastercoderapp.ui.adapters.CodeForcesAdapter
import com.example.mastercoderapp.ui.adapters.TopCoderAdapter
import com.example.mastercoderapp.ui.viewModels.CodeChefViewModel
import com.example.mastercoderapp.ui.viewModels.CodeForcesViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.code_forces_fragment.*

class CodeForcesFragment : Fragment() {

    private lateinit var codeforcesViewModel: CodeForcesViewModel
    // data binding
    private var _binding: CodeForcesFragmentBinding? = null
    private val binding get() = _binding!!
    // initialise adapter
    lateinit var adapter: CodeForcesAdapter
    // graph variables
    private var yrating = ArrayList<Int>()
    private lateinit var lineChart: LineChart
    private var ysize = 0
    private var contestArticlesOn = mutableListOf<ContestDetailsItem>()
    private var contestArticlesFut = mutableListOf<ContestDetailsItem>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        _binding = CodeForcesFragmentBinding.inflate(inflater, container, false)
        codeforcesViewModel = ViewModelProvider(this)[CodeForcesViewModel::class.java]
        binding.apply {
            codeforcesRecyclerOn.apply {
                codeforcesRecyclerOn.layoutManager = LinearLayoutManager(activity)
                codeforcesRecyclerFut.layoutManager = LinearLayoutManager(activity)
            }
            codeforcesViewModel.message.observe(this@CodeForcesFragment.viewLifecycleOwner) {
                if (it == "failure") {
                    Toast.makeText(requireContext(), "failed", Toast.LENGTH_LONG)
                    activity?.let{
                        val intent = Intent (it, UserDetailActivity::class.java)
                        it.startActivity(intent)
                    }
                }
            }
            codeforcesViewModel.profileLiveData.observe(this@CodeForcesFragment.viewLifecycleOwner) {
                if (it != null) {
                    cfProgressBar.visibility = View.GONE
                    re.visibility = View.VISIBLE
                    context?.let { it1 -> Glide.with(it1).load(it.result[0].avatar).into(pf) }
                    user.text = "@"+it.result[0].handle
                    currRating.text = "Rating : " + it.result[0].rating
                    maxRating.text = "Max Rating : " + it.result[0].maxRating
                    maxRank.text = "Country Rank : " + it.result[0].maxRank
                    minRank.text = "Global Rank : " + it.result[0].rank
                    val division = it.result[0].rating
                    if(division <= 1199){
                        div.text = "Div : 4"
                    }
                    else if(division in 1200..1599){
                        div.text = "Div : 3"
                    }
                    else if(division in 1600..1999){
                        div.text = "Div : 2"
                    }
                    else if(division >= 2000){
                        div.text = "Div : 1"
                    }
                }
                codeforcesViewModel.ratingLiveData.observe(this@CodeForcesFragment.viewLifecycleOwner) {
                    if (it != null) {
                        for (i in it.result) {
                            yrating.add(i.newRating)
                        }
                    }
                    ysize = yrating.size
                    Log.d("kalp", "${yrating.size}")
                    lineChart = binding.line
                    initLineChart()
                    lineChart()
                }
                codeforcesViewModel.contestLiveData.observe(this@CodeForcesFragment.viewLifecycleOwner){
                    if (it != null) {
                        for(element in it) {
                            if (element.status == "BEFORE") {
                                contestArticlesFut.add(element)
                                adapter = CodeForcesAdapter(this@CodeForcesFragment, contestArticlesFut)
                                codeforcesRecyclerFut.adapter = adapter
                                adapter.notifyDataSetChanged()
                            } else {
                                contestArticlesOn.add(element)
                                adapter = CodeForcesAdapter(this@CodeForcesFragment, contestArticlesOn)
                                codeforcesRecyclerOn.adapter = adapter
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
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
        lineChart.xAxis.isEnabled = false
        lineChart.legend.formLineWidth = 20F

        //remove description label
        lineChart.description.isEnabled = false
        lineChart.legend.formLineDashEffect
        lineChart.legend.textSize = 20F

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.setDrawLabels(false)
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
        lineDataset.valueTextColor = Color.BLACK
        lineDataset.valueTextSize = 10F
        lineDataset.setDrawFilled(true)
        lineDataset.setCircleColor(R.color.red)
        lineDataset.isHighlightEnabled = false
        val data = LineData(lineDataset)
        lineChart.data = data
        lineChart.setBackgroundColor(Color.WHITE)
    }


}