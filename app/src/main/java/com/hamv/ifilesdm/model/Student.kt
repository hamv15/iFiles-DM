package com.hamv.ifilesdm.model

data class Student(
    var id: Int = System.currentTimeMillis().toInt(), //Va a generar un id único cada vez
    var name: String? = null
)
