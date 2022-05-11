package com.example.mastercoderapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.Navigation
import com.example.mastercoderapp.R
import com.example.mastercoderapp.databinding.FragmentHomeBinding
import com.example.mastercoderapp.ui.activities.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.btnCC.setOnClickListener {
            view?.let { Navigation.findNavController(it) }
                ?.navigate(R.id.action_homeFragment_to_codeChefFragment)
        }
        binding.btnLC.setOnClickListener {
            view?.let { Navigation.findNavController(it) }
                ?.navigate(R.id.action_homeFragment_to_leetCodeFragment)
        }
        binding.btnTC.setOnClickListener {
            view?.let { Navigation.findNavController(it) }
                ?.navigate(R.id.action_homeFragment_to_topCoderFragment)
        }
        binding.btnAT.setOnClickListener {
            view?.let { Navigation.findNavController(it) }
                ?.navigate(R.id.action_homeFragment_to_atCoderFragment)
        }
        binding.btnHE.setOnClickListener {
            view?.let { Navigation.findNavController(it) }
                ?.navigate(R.id.action_homeFragment_to_hackerEarthFragment)
        }
        binding.btnCF.setOnClickListener {
            view?.let { Navigation.findNavController(it) }
                ?.navigate(R.id.action_homeFragment_to_codeForcesFragment)
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ham.setOnClickListener{
            val popup = PopupMenu(requireContext(), it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.logout -> {
                        firebaseAuth.signOut()
                        checkUser()
                    }
                }
                true
            }
            popup.show()
        }
    }
    private fun checkUser() {
        // check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            // user null , user is logged out
            activity?.let{
                val intent = Intent (it, LoginActivity::class.java)
                it.startActivity(intent)
            }
        }
    }
}