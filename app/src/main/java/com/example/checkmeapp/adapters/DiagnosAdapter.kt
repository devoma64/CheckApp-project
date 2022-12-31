package com.example.checkmeapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.checkmeapp.R
import com.example.checkmeapp.activities.QuestionActivity
import com.example.checkmeapp.models.Diagnos
import com.example.checkmeapp.utils.ColorPicker
import com.example.checkmeapp.utils.IconPicker

class DiagnosAdapter(val context: Context, val diagnoses: List<Diagnos>):
    RecyclerView.Adapter<DiagnosAdapter.DiagnosViewHolder>() {
    inner class DiagnosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var textViewTitle: TextView = itemView.findViewById(R.id.diagnosTitle)
        var iconView: ImageView = itemView.findViewById(R.id.diagnosIcon)
        var cardContainer: CardView = itemView.findViewById(R.id.cardContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiagnosViewHolder {
        val view  = LayoutInflater.from(context).inflate(R.layout.dagnos_item, parent, false)
        return DiagnosViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiagnosViewHolder, position: Int) {
        holder.textViewTitle.text = diagnoses[position].title
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.iconView.setImageResource(IconPicker.getIcon())
        holder.itemView.setOnClickListener(){
            Toast.makeText(context, diagnoses[position].title, Toast.LENGTH_SHORT).show()
            val intent = Intent(context, QuestionActivity::class.java)
            intent.putExtra("DATE", diagnoses[position].title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return diagnoses.size
    }
}