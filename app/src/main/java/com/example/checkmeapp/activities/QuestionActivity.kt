package com.example.checkmeapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.checkmeapp.adapters.OptionAdapter
import com.example.checkmeapp.databinding.ActivityQuestionBinding
import com.example.checkmeapp.models.Diagnos
import com.example.checkmeapp.models.Question
import com.google.firebase.firestore.FirebaseFirestore

class QuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionBinding

    var diagnoses : MutableList<Diagnos> ? = null
    var questions: MutableMap<String, Question>? = null
    var index = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpFirestore()
    }

    private fun setUpFirestore() {

        val firestore = FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if(date !=null){
            firestore.collection("diagnoses").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if(it != null && !it.isEmpty) {
                        Log.d("DATA", it.toObjects(Diagnos::class.java).toString())
                        diagnoses = it.toObjects(Diagnos::class.java)
                        questions = diagnoses!![0].questions
                        bindViews()
                }
            }
        }
    }

    private fun bindViews(){
        binding.btnPrevious.visibility = View.GONE
        binding.btnSubmit.visibility = View.GONE
        binding.btnNext.visibility = View.GONE

        if(index == 1)
        {
            binding.btnNext.visibility = View.VISIBLE
        }
        else if(index == questions!!.size){
            binding.btnSubmit.visibility = View.VISIBLE
            binding.btnPrevious.visibility = View.VISIBLE

        }else{
            binding.btnPrevious.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE
        }

        val question = questions!!["question$index"]

        question?.let {
            binding.description.text = it.description
            val optionAdapter = OptionAdapter(this, it)
            binding.optionList.layoutManager = LinearLayoutManager(this)
            binding.optionList.adapter = optionAdapter
            binding.optionList.setHasFixedSize(true)
        }
    }
}