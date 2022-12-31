package com.example.checkmeapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.checkmeapp.activities.SignupActivity
import com.example.checkmeapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener(){
            login()
        }
        binding.goToSignup.setOnClickListener(){
            switchPhase()
        }
    }

    private fun switchPhase(){
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun login(){

        var email = binding.etEmailAddressLogin.text.toString()
        var password = binding.etPasswordLogin.text.toString()

        if (email.isBlank() || password.isBlank()){
            Toast.makeText(this, "Email or Password can not be empty", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
            if (it.isSuccessful){
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Error Login user in", Toast.LENGTH_SHORT).show()
            }
        }
    }
}