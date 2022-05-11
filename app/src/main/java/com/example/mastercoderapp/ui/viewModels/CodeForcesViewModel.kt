package com.example.mastercoderapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastercoderapp.data.api.ContestApi
import com.example.mastercoderapp.data.api.ContestHelper
import com.example.mastercoderapp.data.model.CodeforcesContest
import com.example.mastercoderapp.data.model.CodeforcesDetails
import com.example.mastercoderapp.data.model.ContestDetails
import com.example.mastercoderapp.data.model.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import retrofit2.create

class CodeForcesViewModel : ViewModel() {
    private var usernameee = ""
    private val auth = FirebaseAuth.getInstance()
    val uid = auth.uid
    private val database = FirebaseDatabase.getInstance().getReference("UserName")

    private val _profileLivedata = MutableLiveData<CodeforcesDetails?>()
    val profileLiveData : MutableLiveData<CodeforcesDetails?> = _profileLivedata

// rating graph data
    private val _ratingLivedata = MutableLiveData<CodeforcesContest?>()
    val ratingLiveData : MutableLiveData<CodeforcesContest?> = _ratingLivedata

    private val _contestLivedata = MutableLiveData<ContestDetails?>()
    val contestLiveData : MutableLiveData<ContestDetails?> = _contestLivedata

    private val statusMessage = MutableLiveData<String>()
    val message : LiveData<String>
        get() = statusMessage

    init {
        viewModelScope.launch {
            try {
                if (uid != null) {
                    database.child(uid)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                // get username
                                this@CodeForcesViewModel.usernameee = snapshot.child("cfUser").value.toString()
                                Log.d("kalp", "$usernameee")
                                viewModelScope.launch {
                                    if(usernameee != null) {

                                        val api = ContestHelper.getInstance4().create(ContestApi::class.java)
                                        val ui = api.getContest4("kalp-77").body()
                                        _profileLivedata.value = ui

                                        val ratingData = ContestHelper.getInstance5().create(ContestApi::class.java)
                                        val ratings = ratingData.getContest5("kalp-77").body()
                                        _ratingLivedata.value = ratings

                                        val contest = ContestHelper.getInstance1().create(ContestApi::class.java)
                                        val con = contest.getContest1("codeforces").body()
                                        _contestLivedata.value = con
                                        if (con != null) {
                                            Log.d("kalp", "${con.size}")
                                        }
                                    }
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                Log.d("kalp", "error")
                            }
                        })
                }
            }
            catch (e : Exception) {
                statusMessage.value = "failure"
            }
        }
    }
}