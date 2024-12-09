package com.example.fintech_nocountry

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.fintech_nocountry.consumoApiRest.ICrudATablas
import com.example.fintech_nocountry.consumoApiRest.RetrofitClient
import com.example.fintech_nocountry.consumoApiRest.dto.MensajeDTO
import com.example.fintech_nocountry.consumoApiRest.dto.UsuarioDTO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

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

    suspend fun datosRepetidos(email: String, context: Context): Boolean{
        var flag = true
        try{
            val response = api.getEnTabla(
                "usuario",
                "correo = '$email'",
                "*")
            if( response[0] is MensajeDTO
                && (response[0] as MensajeDTO).message!! == "No hay coincidencias")
                 flag = false
            else
                MainScope().launch {Toast.makeText(context, "Ya existe un usuario con ese correo", Toast.LENGTH_SHORT).show()}
        } catch (e: Exception){
            Log.e("ApiError", e.message!!)
        }
        return flag
    }

    suspend fun insertarUsuario(email: String, contrasenia: String, nombre: String){
        try{
            val map = mapOf(
                "valores" to "'$email', '$contrasenia', '$nombre'",
                "columnas" to "correo, contrasenia, nombre"
            )
            val response = api.postEnTabla("usuario", map)
            Log.i("Api", (response as MensajeDTO).message!!)
        } catch (e: Exception){
            Log.e("ApiError", e.message!!)
        }
    }
}