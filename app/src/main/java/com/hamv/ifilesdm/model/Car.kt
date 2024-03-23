package com.hamv.ifilesdm.model

data class Car(
    var id: Int = System.currentTimeMillis().toInt(), //Va a generar un id único cada vez
    var marca: String? = null,
    var modelo: String? = null,
    var anio: Int? = null,
    var numeroSerie: String? = null
)
