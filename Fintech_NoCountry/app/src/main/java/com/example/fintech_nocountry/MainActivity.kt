package com.example.fintech_nocountry

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var btnRegistrarme: Button
    lateinit var btnIniciarSesion: Button
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnRegistrarme = findViewById(R.id.main_registrar)
        btnIniciarSesion = findViewById(R.id.main_login)
        sharedPreferences = getSharedPreferences("datos_usuario", MODE_PRIVATE)

        if(sharedPreferences.getString("correo", null) != null){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        btnRegistrarme.setOnClickListener{
            startActivity(Intent(this, RegistroActivity::class.java))

        }

        btnIniciarSesion.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}