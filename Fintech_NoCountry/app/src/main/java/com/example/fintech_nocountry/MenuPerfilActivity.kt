package com.example.fintech_nocountry

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class MenuPerfilActivity : AppCompatActivity() {

    lateinit var btnVolver: ImageButton
    lateinit var imgPerfil: CircleImageView
    lateinit var tvNombre: TextView
    lateinit var navMenu: NavigationView
    lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnVolver = findViewById(R.id.menu_perfil_volver)
        imgPerfil = findViewById(R.id.menu_perfil_imagen)
        tvNombre = findViewById(R.id.menu_perfil_nombre)
        navMenu = findViewById(R.id.perfil_navview)
        sharedPreferences = getSharedPreferences("datos_usuario", MODE_PRIVATE)

        val aux = navMenu.menu.findItem(R.id.titulo_campanias)
        val strTitulo = SpannableString(getString(R.string.menu_campanias))
        strTitulo.setSpan(StyleSpan(Typeface.BOLD), 0, getString(R.string.menu_campanias).length, 0)
        aux.title = strTitulo

        Picasso
            .get()
            .load(sharedPreferences.getString("imagen_perfil", "res/drawable/foto_perfil.xml"))
            .into(imgPerfil)

        tvNombre.text = sharedPreferences.getString("nombre", " ")

        btnVolver.setOnClickListener{
            finish()
        }

        navMenu.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.item_iniciar_campania -> {
                    val form = CampaniaBottomSheetFragment()
                    val bundle = Bundle()
                    bundle.putInt("id", sharedPreferences.getInt("id", 0))
                    form.arguments = bundle
                    form.show(supportFragmentManager, form.tag)
                    true
                }
                R.id.item_cerrar_sesion -> {
                    sharedPreferences.edit().clear().apply()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}