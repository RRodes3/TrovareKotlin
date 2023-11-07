package com.example.trovare.ui.theme.Data

import android.net.Uri
import com.google.android.libraries.places.api.model.OpeningHours

data class LugarAutocompletar(
    val id: String,
    val textoPrimario: String,
    val textoSecundario: String
)

data class Lugar(
    private var nombre: String,
    private var calificacion: Double,
    private var direccion: String,
    private var horario: OpeningHours?,
    private var pagina: Uri?,
){
    fun setNombre(nuevoNombre: String){
        nombre = nuevoNombre
    }
    fun setCalificacion(nuevaCalificacion: Double){
        calificacion = nuevaCalificacion
    }
    fun setDireccion(nuevaDireccion: String){
        direccion = nuevaDireccion
    }
    fun setHorario(nuevoHorario: OpeningHours?){
        horario = nuevoHorario
    }
    fun setPagina(nuevaPagina: Uri?){
        pagina = nuevaPagina
    }

    fun getNombre(): String{
        return nombre
    }

    fun getCalificacion(): Double{
        return calificacion
    }

    fun getDireccion(): String{
        return direccion
    }

}

val lugarPrueba = Lugar(
    nombre = "Nombre del lugar",
    calificacion = 4.5,
    direccion = "Dirección del lugar",
    horario = null,
    pagina = null,//Uri.parse("https://www.ejemplo.com") // Cambia la URL por la que desees
)

