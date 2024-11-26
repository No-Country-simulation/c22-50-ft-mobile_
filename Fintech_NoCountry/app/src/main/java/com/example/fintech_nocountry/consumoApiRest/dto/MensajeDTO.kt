package com.example.fintech_nocountry.consumoApiRest.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class MensajeDTO (
    private val message: String?
) {
    /*La idea es que todos los DTOs hereden de MensajeDTO para que la interfaz
    ICrudATablas y sus operaciones sean genericas, ahorrando codigo.*/

    constructor() : this(null)

    override fun toString(): String {
        return "MensajeDTO[Message= $message]"
    }
}