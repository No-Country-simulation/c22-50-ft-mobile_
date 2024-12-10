package com.example.fintech_nocountry

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fintech_nocountry.consumoApiRest.dto.UsuarioDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var etContrasenia: EditText
    lateinit var btnLogin: Button
    lateinit var tvRegistrarse: TextView
    lateinit var btnVolver: ImageButton
    lateinit var sharedPreferences: SharedPreferences
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etEmail = findViewById(R.id.login_email)
        etContrasenia = findViewById(R.id.login_contrasenia)
        btnLogin = findViewById(R.id.login_ingresar)
        btnVolver = findViewById(R.id.login_volver)
        tvRegistrarse = findViewById(R.id.login_registrarse)
        sharedPreferences = getSharedPreferences("datos_usuario", MODE_PRIVATE)
        progressBar = findViewById(R.id.login_progressbar)

        if(sharedPreferences.getString("correo", null) != null){
            startActivity(
                Intent(this, HomeActivity::class.java)
            )
            finish()
        }

        btnVolver.setOnClickListener{
            finish()
        }

        tvRegistrarse.setOnClickListener{
            startActivity(Intent(this, RegistroActivity::class.java))
            finish()
        }

        btnLogin.setOnClickListener{
            if(etEmail.text.toString().isNotEmpty() && etContrasenia.text.toString().isNotEmpty()){
                if(CorreoYUsuario.esEmailValido(etEmail.text.toString())) {
                    progressBar.visibility = View.VISIBLE
                    CoroutineScope(Dispatchers.IO).launch{
                        val usuario = CorreoYUsuario.verificarDatos(etEmail.text.toString(), etContrasenia.text.toString())

                        withContext(Dispatchers.Main){
                            if(usuario != null) {
                                cargarSharedPreferences(usuario)
                                startActivity(
                                    Intent(this@LoginActivity, HomeActivity::class.java)
                                )
                                finish()
                            }
                            else
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Datos incorrectos, vuelva a intentar",
                                    Toast.LENGTH_SHORT)
                                    .show()
                            progressBar.visibility = View.GONE
                        }
                    }
                }
                else
                {
                    Toast.makeText(this, "Formato de email inválido", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(this, "Se deben completar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun cargarSharedPreferences(usuario: UsuarioDTO){
        if(usuario.id != null)
            sharedPreferences.edit().putInt("id", usuario.id).apply()
        if(usuario.correo != null)
            sharedPreferences.edit().putString("correo", usuario.correo).apply()
        if(usuario.nombre != null)
            sharedPreferences.edit().putString("nombre", usuario.nombre).apply()
        if(usuario.imagenPerfil != null)
            sharedPreferences.edit().putString("imagen_perfil", usuario.imagenPerfil).apply()
    }
}