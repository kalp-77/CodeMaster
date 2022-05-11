package com.example.mastercoderapp.data.model


import com.google.gson.annotations.SerializedName

data class LeetcodeDetails(
    @SerializedName("acceptance_rate")
    val acceptanceRate: String,
    @SerializedName("contribution_points")
    val contributionPoints: String,
    @SerializedName("contribution_problems")
    val contributionProblems: String,
    @SerializedName("contribution_testcases")
    val contributionTestcases: String,
    @SerializedName("easy_acceptance_rate")
    val easyAcceptanceRate: String,
    @SerializedName("easy_problems_submitted")
    val easyProblemsSubmitted: String,
    @SerializedName("easy_questions_solved")
    val easyQuestionsSolved: String,
    @SerializedName("hard_acceptance_rate")
    val hardAcceptanceRate: String,
    @SerializedName("hard_problems_submitted")
    val hardProblemsSubmitted: String,
    @SerializedName("hard_questions_solved")
    val hardQuestionsSolved: String,
    @SerializedName("medium_acceptance_rate")
    val mediumAcceptanceRate: String,
    @SerializedName("medium_problems_submitted")
    val mediumProblemsSubmitted: String,
    @SerializedName("medium_questions_solved")
    val mediumQuestionsSolved: String,
    @SerializedName("ranking")
    val ranking: String,
    @SerializedName("reputation")
    val reputation: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("total_easy_questions")
    val totalEasyQuestions: String,
    @SerializedName("total_hard_questions")
    val totalHardQuestions: String,
    @SerializedName("total_medium_questions")
    val totalMediumQuestions: String,
    @SerializedName("total_problems_solved")
    val totalProblemsSolved: String,
    @SerializedName("total_problems_submitted")
    val totalProblemsSubmitted: String
)