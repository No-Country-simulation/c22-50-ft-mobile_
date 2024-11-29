package com.example.fintech_nocountry.consumoApiRest.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsuarioDTO(
    val id : Int?,
    val nombre: String?,
    val correo: String?,
    val contrasenia: String?
): TablaDTO()

