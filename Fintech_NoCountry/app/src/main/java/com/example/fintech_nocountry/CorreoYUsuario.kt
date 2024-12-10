package com.example.fintech_nocountry

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.fintech_nocountry.consumoApiRest.ICrudATablas
import com.example.fintech_nocountry.consumoApiRest.RetrofitClient
import com.example.fintech_nocountry.consumoApiRest.dto.CrowdfundingDTO
import com.example.fintech_nocountry.consumoApiRest.dto.MensajeDTO
import com.example.fintech_nocountry.consumoApiRest.dto.UsuarioDTO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.time.LocalDate

object CorreoYUsuario {
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

    suspend fun existeEmprendedor(idUsuario: Int): Boolean{
        var flag = false
        try{
            val response = api.getEnTabla("emprendedor", "usuario_id = $idUsuario", "*")
            if(response.isEmpty())
                flag = true
            else if(response[0] is MensajeDTO)
                Log.e("ApiError", (response[0] as MensajeDTO).message!!)
        } catch (e: Exception){
            Log.e("ApiError", e.message!!)
        }
        return flag
    }

    suspend fun insertarCrowdfunding(titulo: String, descripcion: String, montoObjetivo: Double,
                                     fechaFinalizacion: String, usuarioId: Int, urlImagen: String = ""): Int{
        var id = 0
        try{
            if(!existeEmprendedor(usuarioId)){
                Log.i("Api", api.postEnTabla("emprendedor", mapOf(
                    "columnas" to "usuario_id, descripcion",
                    "valores" to "$usuarioId, ''"
                )
                ).toString())
            }
            val auxFecha = LocalDate.now()
            val map: Map<String, String>
            if(urlImagen.isNotEmpty()){
                map = mapOf(
                    "columnas" to "titulo, descripcion, fecha_finalizacion, monto_objetivo, url_imagen, emprendedor_id, fecha_creacion",
                    "valores" to "'$titulo', '$descripcion', '$fechaFinalizacion', $montoObjetivo, '$urlImagen', $usuarioId, '$auxFecha'"
                )
            } else {
                map = mapOf(
                    "columnas" to "titulo, descripcion, fecha_finalizacion, monto_objetivo, emprendedor_id, fecha_creacion",
                    "valores" to "'$titulo', '$descripcion', '$fechaFinalizacion', $montoObjetivo, $usuarioId, '$auxFecha'")
            }
            val response = api.postEnTabla("crowdfunding", map)
            Log.e("APIERROR", response.toString())
            val crowd = api.getEnTabla("crowdfunding", "emprendedor_id = $usuarioId AND fecha_creacion = '$auxFecha'", "id")
            if(crowd.isNotEmpty() && crowd[0] is CrowdfundingDTO){
                id = (crowd[0] as CrowdfundingDTO).id!!
            }
            else if(crowd.isNotEmpty() && crowd[0] is MensajeDTO)
                Log.e("ApiError", (crowd[0] as MensajeDTO).message!!)
            else
                Log.e("ApiError", crowd.toString())
        } catch (e: Exception){
            Log.e("ApiError", e.message!!)
        }
        return id
    }
}