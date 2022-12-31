package com.example.checkmeapp.models

data class Diagnos(
    var id : String = "",
    var title : String = "",
    var questions: MutableMap<String, Question> = mutableMapOf()
)