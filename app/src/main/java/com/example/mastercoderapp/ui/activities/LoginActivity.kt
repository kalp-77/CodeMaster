package com.example.mastercoderapp.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDelegate
import com.example.mastercoderapp.MainActivity
import com.example.mastercoderapp.R
import com.example.mastercoderapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    // view binding
    private lateinit var binding : ActivityLoginBinding

    //progressDialog
    private lateinit var progressDialog : ProgressDialog

    // firebase auth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email= ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // configure progressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)
        // firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // handle click, open sign up activity
        binding.noAcc.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        // handle click, begin log in
        binding.saveBtn.setOnClickListener {
            // before logging in validate data
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
                binding.email.error ="Invalid email format"
                Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
        }
        else if(TextUtils.isEmpty(password)){
            // no password entered
            binding.password.error = "please enter password"
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
        }
        else{
            // data is validated , begin login
            firebaseLogin()
        }

    }

    private fun firebaseLogin() {
        // show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener{
                //login success
                progressDialog.dismiss()
                // get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Logged In as $email", Toast.LENGTH_LONG).show()
                // open profile activity
                startActivity(Intent(this,UserDetailActivity::class.java))
                finish()
            }
            .addOnFailureListener{
                // login failed
                progressDialog.dismiss()
                Toast.makeText(this, "login failed due to ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun checkUser() {
        // if user is already logged in go to main activity
        // get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}