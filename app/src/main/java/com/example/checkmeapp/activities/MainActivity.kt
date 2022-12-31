package com.example.checkmeapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.checkmeapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isLoggedIn()
        binding.getStarted.setOnClickListener(){
            redirect("LOGIN")
        }
    }

    private fun isLoggedIn(){
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser !=null){
            Toast.makeText(this, "User is already logged in!", Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }
    }
    private fun redirect(name:String){
        val intent = when(name){
            "LOGIN" -> Intent(this, LoginActivity::class.java)
            "MAIN" -> Intent(this, DashboardActivity::class.java)
            else -> throw Exception("no path exist")
        }
        startActivity(intent)
        finish()
    }
}