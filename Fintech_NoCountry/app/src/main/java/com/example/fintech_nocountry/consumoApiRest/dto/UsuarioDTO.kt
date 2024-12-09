package com.example.fintech_nocountry.consumoApiRest.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UsuarioDTO(
    val id : Int?,
    val nombre: String?,
    val correo: String?,
    val contrasenia: String?,
    @Json(name = "imagen_perfil") val  imagenPerfil: String?
): TablaDTO(), Parcelable

