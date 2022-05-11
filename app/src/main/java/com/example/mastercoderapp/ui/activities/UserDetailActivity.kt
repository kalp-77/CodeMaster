package com.example.mastercoderapp.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.mastercoderapp.MainActivity
import com.example.mastercoderapp.data.db.UserName
import com.example.mastercoderapp.databinding.ActivityMainBinding
import com.example.mastercoderapp.databinding.ActivityUserDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserDetailBinding
    // Database reference
    lateinit var database : DatabaseReference
    // auth reference
    lateinit var auth : FirebaseAuth

    //initializing the usernames
    private var ccname = ""
    private var cfname = ""
    private var lcname = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(binding.root)

        // initializing firebase instance
        database = FirebaseDatabase.getInstance().getReference("UserName")
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        binding.save.setOnClickListener {
            ccname = binding.nameEdtCc.text.toString()
            cfname = binding.nameEdtCf.text.toString()
            lcname = binding.nameEdtLc.text.toString()
            // save users data on database
            val username = UserName(ccname,cfname,lcname)
                if(uid!=null){
                    database.child(uid).setValue(username)
                        .addOnSuccessListener {
                            binding.nameEdtCc.text?.clear()
                            binding.nameEdtCf.text?.clear()
                            binding.nameEdtLc.text?.clear()
                            Toast.makeText(this, "successfully saved", Toast.LENGTH_LONG).show()
                            val intent= Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "failed saved", Toast.LENGTH_LONG).show()
                        }
                }

        }

    }
    override fun onBackPressed() {
        //startActivity(Intent(this, SplashScreen::class.java))
        moveTaskToBack(true)
    }
}