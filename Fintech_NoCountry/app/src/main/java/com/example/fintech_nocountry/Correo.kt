package com.example.fintech_nocountry

import android.util.Log
import com.example.fintech_nocountry.consumoApiRest.ICrudATablas
import com.example.fintech_nocountry.consumoApiRest.RetrofitClient
import com.example.fintech_nocountry.consumoApiRest.dto.MensajeDTO
import com.example.fintech_nocountry.consumoApiRest.dto.UsuarioDTO

object Correo {
    private val api = RetrofitClient.retrofit.create(ICrudATablas::class.java)
    fun esEmailValido(email: String): Boolean{
        var flag = false
        if(email.contains('@')
            && email.substringAfter('@').contains('.')
            && email.substringAfter('@').substringAfter('.').isNotEmpty()){
            flag = true
        }
        return flag
    }

    suspend fun verificarDatos(email: String, contrasenia: String): UsuarioDTO?{
        var usuarioDTO: UsuarioDTO? = null
        try{
            val response = api.getEnTabla(
                "usuario",
                "correo = '$email' AND contrasenia = '$contrasenia'",
                "*")
            if(response.isNotEmpty() && response[0] is UsuarioDTO)
                usuarioDTO = response[0] as UsuarioDTO
            else if(response.isNotEmpty())
                Log.e("ApiError", (response[0] as MensajeDTO).message!!)
        } catch(e: Exception){
            Log.e("ApiError", e.message!!)
        }
        return usuarioDTO
    }

    suspend fun datosRepetidos(email: String): Boolean{
        var flag = true
        try{
            val response = api.getEnTabla(
                "usuario",
                "correo = '$email'",
                "*")
            if(response.isEmpty())
                 flag = false
            else if(response.isNotEmpty() && response[0] is MensajeDTO)
                Log.e("ApiError", (response[0] as MensajeDTO).message!!)
        } catch (e: Exception){
            Log.e("ApiError", e.message!!)
        }
        return flag
    }

    suspend fun insertarUsuario(email: String, contrasenia: String, nombre: String){
        try{
            val map = mapOf(
                "correo" to email,
                "contrasenia" to contrasenia,
                "nombre" to nombre
            )
            val response = api.postEnTabla("usuario", map)
            Log.e("Api", (response as MensajeDTO).message!!)
        } catch (e: Exception){
            Log.e("ApiError", e.message!!)
        }
    }
}