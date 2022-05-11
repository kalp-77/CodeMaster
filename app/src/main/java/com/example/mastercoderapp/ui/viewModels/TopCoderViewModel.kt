package com.example.mastercoderapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastercoderapp.data.api.ContestApi
import com.example.mastercoderapp.data.api.ContestHelper
import com.example.mastercoderapp.data.model.ContestDetails
import com.example.mastercoderapp.data.model.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class TopCoderViewModel : ViewModel() {

    private val _contestLivedata = MutableLiveData<ContestDetails>()
    val contestLiveData : LiveData<ContestDetails> = _contestLivedata

    private val statusMessage = MutableLiveData<String>()
    val message : LiveData<String>
        get() = statusMessage

    init {
        viewModelScope.launch {
            try {
                val contest = ContestHelper.getInstance1().create(ContestApi::class.java)
                val con = contest.getContest1("top_coder").body()
                _contestLivedata.value = con!!
            }
            catch (e : Exception) {
                statusMessage.value = "failure"
            }
        }
    }
}