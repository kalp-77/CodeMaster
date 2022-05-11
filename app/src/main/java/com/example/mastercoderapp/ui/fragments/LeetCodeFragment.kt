package com.example.mastercoderapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mastercoderapp.R
import com.example.mastercoderapp.data.model.ContestDetailsItem
import com.example.mastercoderapp.databinding.LeetCodeFragmentBinding
import com.example.mastercoderapp.ui.adapters.LeetCodeAdapter
import com.example.mastercoderapp.ui.viewModels.LeetCodeViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.leet_code_fragment.*


class LeetCodeFragment : Fragment() {

    // initialize viewModel
    private lateinit var leetcodeViewModel: LeetCodeViewModel
    // data binding
    private var _binding: LeetCodeFragmentBinding? = null
    private val binding get() = _binding!!
    // initialise adapter
    private lateinit var adapter: LeetCodeAdapter
    private var contestArticles = mutableListOf<ContestDetailsItem>()

    private lateinit var pieChart: PieChart
    private var entries = ArrayList<Int>()
    private var entrySize = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        leetcodeViewModel = ViewModelProvider(this)[LeetCodeViewModel::class.java]
        _binding = LeetCodeFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            lcRecycleOn.apply {
                lcRecycleOn.layoutManager = LinearLayoutManager(activity)
            }
            leetcodeViewModel.boardsLiveData.observe(this@LeetCodeFragment.viewLifecycleOwner) {
                if (it != null) {
                    val total_problems = it.hardProblemsSubmitted.toInt() + it.totalEasyQuestions.toInt() + it.totalMediumQuestions.toInt()
                    leetCode_easy_solved.text = "${it.easyProblemsSubmitted}/${it.totalEasyQuestions}"
                    leetCode_hard_solved.text = "${it.hardProblemsSubmitted}/${it.totalHardQuestions}"
                    leetCode_medium_solved.text = "${it.mediumProblemsSubmitted}/${it.totalMediumQuestions}"
                    leetCode_total_solved_problems.text = "${it.totalProblemsSubmitted}/${total_problems}"
                    leetCode_acceptance_rate.text = it.acceptanceRate
                    pieChart = binding.line
                    entries.add(it.hardQuestionsSolved.toInt())
                    entries.add(it.easyQuestionsSolved.toInt())
                    entries.add(it.mediumQuestionsSolved.toInt())
                    entrySize = entries.size
                    Log.d("kalp", "${entries.size}")
                    pieChart()
                    setUpPieChart()
                }
            }
            leetcodeViewModel.contestLiveData.observe(this@LeetCodeFragment.viewLifecycleOwner) {
                if (it != null) {
                    contestArticles.addAll(it)
                    contestArticles.reverse()
                    Log.d("kalp0","${contestArticles.size}")
                    adapter = LeetCodeAdapter(this@LeetCodeFragment, contestArticles)
                    lcRecycleOn.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
        }
        return binding.root
    }
    private fun setUpPieChart(){
        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText = "Chart"
        pieChart.setCenterTextSize(16F)
        pieChart.description.isEnabled = false
        pieChart.isHighlightPerTapEnabled = true
        pieChart.transparentCircleRadius = 52F
        pieChart.holeRadius = 40F
        pieChart.animateY(1400, Easing.EaseOutQuad);
        pieChart.legend.isEnabled = false;
//        val legend = pieChart.legend
//        legend.form = Legend.LegendForm.CIRCLE
//        legend.textSize = 12f
//        legend.formSize = 12f
//        legend.formToTextSpace = 2f
        pieChart.invalidate()

    }
    private fun pieChart(){
        val pie = ArrayList<PieEntry>()
        val colours = ArrayList<Int>()
        colours.add(Color.parseColor("#FF6363"))
        colours.add(Color.parseColor("#C0EDA6"))
        colours.add(Color.parseColor("#FDD7AA"))

        Log.d("kp", "${colours.size}")
        pie.add(PieEntry(entries[0].toFloat(),"hard"))
        pie.add(PieEntry(entries[1].toFloat(),"easy"))
        pie.add(PieEntry(entries[2].toFloat(),"medium"))

        val pieDataset = PieDataSet(pie,"leetcode")
        val data = PieData(pieDataset)
        pieChart.data = data
        pieChart.setBackgroundColor(Color.WHITE)
        pieDataset.colors = colours
        pieDataset.valueTextColor = Color.BLACK
        pieDataset.valueTextSize = 8F
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}