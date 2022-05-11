package com.example.mastercoderapp.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDelegate
import com.example.mastercoderapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    // View Binding
    private lateinit var binding : ActivitySignUpBinding
    // Progress Dialog
    private lateinit var progressDialog: ProgressDialog
    // Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        //configure progressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        // handle click, begin signup
        binding.signupBtn.setOnClickListener {
            //validate data
            validateData()
        }

    }

    private fun validateData() {
        // get data
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        // validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            // invalid email format
            binding.email.error = "Invalid email format"
        }
        else if(TextUtils.isEmpty(password)){
            binding.password.error = "please enter password"
        }
        else if(password.length < 6){
            binding.password.error = "Password characters must be greater than 6"
        }
        else{
            // data is valid continue sign up
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        // show progress
        progressDialog.show()

        // create account in firebase
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //sign up success
                progressDialog.dismiss()
                // get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Account created successfully with email $email", Toast.LENGTH_LONG).show()

                // open UserDetail activity
                startActivity(Intent(this, UserDetailActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                // sign up failure
                progressDialog.dismiss()
                Toast.makeText(this,"SignUp Failed due to ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    override fun onSupportNavigateUp() : Boolean {
        onBackPressed() // go back to previous activity, when back button of actionbar pressed
        return super.onSupportNavigateUp()
    }
}