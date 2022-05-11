package com.example.mastercoderapp.ui.viewModels

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastercoderapp.data.api.ContestApi
import com.example.mastercoderapp.data.api.ContestHelper
import com.example.mastercoderapp.data.model.ContestDetails
import com.example.mastercoderapp.data.model.UserDetails
import com.example.mastercoderapp.ui.activities.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CodeChefViewModel() : ViewModel() {

    private var usernameee = ""
    private val auth = FirebaseAuth.getInstance()
    val uid = auth.uid
    private val database = FirebaseDatabase.getInstance().getReference("UserName")

    private val _profileLivedata = MutableLiveData<UserDetails?>()
    val boardsLiveData : MutableLiveData<UserDetails?> = _profileLivedata

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
                                    this@CodeChefViewModel.usernameee = snapshot.child("ccUser").value.toString()
                                    Log.d("kalp", "$usernameee")
                                    viewModelScope.launch {
                                        if(usernameee != null) {
                                            val api = ContestHelper.getInstance2()
                                                .create(ContestApi::class.java)
                                            val ui = api.getContest2("codechef", usernameee).body()
                                            _profileLivedata.value = ui
                                            val contest = ContestHelper.getInstance1()
                                                .create(ContestApi::class.java)
                                            val con = contest.getContest1("code_chef").body()
                                            _contestLivedata.value = con
                                            if (con != null) {
                                                Log.d("kalp", "${con.size}")
                                            }
                                        }
                                        else{
                                            _profileLivedata.value = null
                                            _contestLivedata.value = null
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
                    val intent = Int
                }
        }
    }
}