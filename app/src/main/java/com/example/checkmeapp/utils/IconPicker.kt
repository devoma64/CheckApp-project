package com.example.checkmeapp.utils

import com.example.checkmeapp.R

object IconPicker{
    var icons = arrayOf(
        R.drawable.ic_hambuger,
        R.drawable.wave,
        R.drawable.icon3,
        R.drawable.icon4,
        R.drawable.icon5,
        R.drawable.icon6,
        R.drawable.icon7,
        R.drawable.icon8,
        R.drawable.icon9
        )
    var currentIcon = 0
    fun getIcon(): Int{
        currentIcon = (currentIcon +1) % icons.size
        return icons[currentIcon]
    }

}