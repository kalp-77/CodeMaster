package com.example.mastercoderapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastercoderapp.data.api.ContestApi
import com.example.mastercoderapp.data.api.ContestHelper
import com.example.mastercoderapp.data.model.ContestDetails
import com.example.mastercoderapp.data.model.LeetcodeDetails
import com.example.mastercoderapp.data.model.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class LeetCodeViewModel : ViewModel() {
    private var usernameee = ""
    private val auth = FirebaseAuth.getInstance()
    val uid = auth.uid
    private val database = FirebaseDatabase.getInstance().getReference("UserName")

    private val _profileLivedata = MutableLiveData<LeetcodeDetails>()
    val boardsLiveData : LiveData<LeetcodeDetails> = _profileLivedata

    private val _contestLivedata = MutableLiveData<ContestDetails>()
    val contestLiveData : LiveData<ContestDetails> = _contestLivedata

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
                                this@LeetCodeViewModel.usernameee = snapshot.child("lcUser").value.toString()
                                Log.d("kalp", "$usernameee")
                                viewModelScope.launch {
                                    if(usernameee!=null) {
                                        val api = ContestHelper.getInstance3()
                                            .create(ContestApi::class.java)
                                        val ui = api.getContest3("leetcode", usernameee).body()
                                        _profileLivedata.value = ui!!
                                        val contest = ContestHelper.getInstance1()
                                            .create(ContestApi::class.java)
                                        val con = contest.getContest1("leet_code").body()
                                        _contestLivedata.value = con!!
                                        Log.d("kalp", "${con.size}")
                                    }
                                    else {
                                        Log.d("kalp", "failed")
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