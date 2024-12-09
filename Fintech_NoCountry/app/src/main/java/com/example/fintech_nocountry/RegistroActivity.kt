package com.example.fintech_nocountry

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroActivity : AppCompatActivity() {

    lateinit var etNombre: EditText
    lateinit var etCorreo: EditText
    lateinit var etContrasenia: EditText
    lateinit var etRepetirContra: EditText
    lateinit var btnRegistrar: Button
    lateinit var btnVolver: ImageButton
    lateinit var tvLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etNombre = findViewById(R.id.register_nombre)
        etCorreo = findViewById(R.id.register_email)
        etContrasenia = findViewById(R.id.register_contrasenia)
        etRepetirContra = findViewById(R.id.register_confirmar_contrasenia)
        btnRegistrar = findViewById(R.id.register_registar)
        btnVolver = findViewById(R.id.register_volver)
        tvLogin = findViewById(R.id.register_login)

        btnVolver.setOnClickListener{
            finish()
        }

        btnRegistrar.setOnClickListener{
            if(etNombre.text.toString().isEmpty()
                || etContrasenia.text.toString().isEmpty()
                || etRepetirContra.text.toString().isEmpty()
                || etCorreo.text.toString().isEmpty()
            )
                Toast.makeText(this, "Deben completarse todos los campos", Toast.LENGTH_SHORT).show()
            else if(!Correo.esEmailValido(etCorreo.text.toString()))
                Toast.makeText(this, "Formato de correo inválido", Toast.LENGTH_SHORT).show()
            else if(etContrasenia.text.toString() != etRepetirContra.text.toString())
                Toast.makeText(this, "La contraseñas no coinciden. Vuelva a ingresar", Toast.LENGTH_SHORT).show()
            else{
                CoroutineScope(Dispatchers.IO).launch {
                    if(Correo.datosRepetidos(etCorreo.text.toString()))
                        Log.e("DatosRepetidos", "Ya existe un usuario con ese correo")
                    else {
                        Correo.insertarUsuario(
                            etCorreo.text.toString(),
                            etContrasenia.text.toString(),
                            etNombre.text.toString()
                        )

                        withContext(Dispatchers.Main){
                            Toast.makeText(
                                this@RegistroActivity,
                                "¡Usuario creado exitosamente!",
                                Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(this@RegistroActivity, LoginActivity::class.java))
                            finish()
                        }
                    }
                }
            }
        }

        tvLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


}