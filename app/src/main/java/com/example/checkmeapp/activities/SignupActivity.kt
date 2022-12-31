package com.example.checkmeapp.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.checkmeapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater);
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnSignUp.setOnClickListener(){
            signUpUser()
        }
        binding.goToLogin.setOnClickListener(){
            switchPhase()
        }
    }

    private fun switchPhase() {
       val intent = Intent(this, LoginActivity::class.java);
        startActivity(intent)
        finish()
    }

    private fun signUpUser(){
        val email = binding.etEmailAddressSignup.text.toString()
        val password = binding.etcPassword.text.toString()
        val confirmPassword = binding.etcPassword.text.toString()

        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }
        if (password != confirmPassword){
            Toast.makeText(this, "Passwords Mismatch", Toast.LENGTH_SHORT).show()
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Error Creating User", Toast.LENGTH_SHORT).show()
            }
        }
    }
}