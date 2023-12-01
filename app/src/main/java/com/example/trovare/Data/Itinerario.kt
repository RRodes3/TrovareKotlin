package com.example.trovare.Data

import androidx.annotation.DrawableRes
import java.time.LocalDate

data class Itinerario(
    var nombre: String,
    var autor: String,
    var lugares: MutableList<Lugar>?
)

data class Lugar(
    val id: String,
    val nombreLugar: String,
)

val itinerarioPrueba: Itinerario = Itinerario(
    nombre = "Itinerario",
    autor = "",
    lugares = null
)

data class Actividad(
    val nombre: String
)
