package com.example.fintech_nocountry.consumoApiRest.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
abstract class TablaDTO (

) {
    /*La idea es que todos los DTOs hereden de esta clase para que la interfaz
    ICrudATablas y sus operaciones sean genericas, ahorrando codigo.*/
}