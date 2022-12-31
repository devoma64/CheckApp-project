package com.example.checkmeapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.checkmeapp.R
import com.example.checkmeapp.adapters.DiagnosAdapter
import com.example.checkmeapp.databinding.ActivityDashboardBinding
import com.example.checkmeapp.models.Diagnos
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter


class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: DiagnosAdapter
    private var diagnosList = mutableListOf<Diagnos>()
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStepViews()
    }

    fun setStepViews(){
        setUpFireStore()
        setUpDrawerLayout()
        setRecyclerView()
        setDatePicker()
    }

    private fun setDatePicker() {
       binding.btnDatePicker.setOnClickListener(){
           val datePicker = MaterialDatePicker.Builder.datePicker().build()
           datePicker.show(supportFragmentManager, "DatePicker")
           datePicker.addOnPositiveButtonClickListener {
               Log.d("DATEPICKER", datePicker.headerText)

               val dateFormatter = SimpleDateFormat("dd-mm-yyyy")
               val date = dateFormatter.format(Date(it))
               val  intent = Intent(this, QuestionActivity::class.java)
               intent.putExtra("DATE", date)
               startActivity(intent)
           }
           datePicker.addOnNegativeButtonClickListener(){
               Log.d("DATEPICKER", datePicker.headerText)

           }
           datePicker.addOnCancelListener(){
               Log.d("DATEPICKER", "Date Picker Cancelled")
           }
       }
    }

    private fun setUpFireStore() {
        firestore  = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("diagnoses")
        collectionReference.addSnapshotListener(){value, error ->
            if (value == null || error != null){
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(Diagnos::class.java).toString())
            diagnosList.clear()
            diagnosList.addAll(value.toObjects(Diagnos::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setRecyclerView() {
        val diagnosRecyclerView = binding.diagnosRecyclerView
        adapter = DiagnosAdapter(this, diagnosList)
        diagnosRecyclerView.layoutManager = GridLayoutManager(this, 2)
        diagnosRecyclerView.adapter = adapter
    }

    fun setUpDrawerLayout() {
         val appBar = binding.appBar
         val mainDrawer = binding.mainDrawer
        setSupportActionBar(appBar)
         actionBarDrawerToggle = ActionBarDrawerToggle(this, mainDrawer,
             R.string.app_name,
             R.string.app_name
         )
         actionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
