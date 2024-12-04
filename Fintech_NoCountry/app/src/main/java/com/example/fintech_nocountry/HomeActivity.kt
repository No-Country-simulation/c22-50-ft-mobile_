package com.example.fintech_nocountry

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fintech_nocountry.consumoApiRest.ICrudATablas
import com.example.fintech_nocountry.consumoApiRest.RetrofitClient
import com.example.fintech_nocountry.consumoApiRest.dto.CrowdfundingDTO
import com.example.fintech_nocountry.consumoApiRest.dto.EtiquetaDTO
import com.example.fintech_nocountry.consumoApiRest.dto.MensajeDTO
import com.example.fintech_nocountry.recyclerViews.CrowdfundingAdapter
import com.example.fintech_nocountry.recyclerViews.EtiquetaAdapter
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class HomeActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var navigationBar: NavigationBarView
    lateinit var tvTitulo: TextView
    lateinit var rvCrowdfundings: RecyclerView
    lateinit var rvEtiquetas: RecyclerView
    private val api: ICrudATablas? = RetrofitClient.retrofit.create(ICrudATablas::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvEtiquetas = findViewById(R.id.rv_home_lstetiquetas)
        rvCrowdfundings = findViewById(R.id.rv_home_lstcrowdfundings)
        toolbar = findViewById(R.id.home_toolbar)
        navigationBar = findViewById(R.id.home_navbar)
        tvTitulo = findViewById(R.id.tv_home_titulo)

        val titulo = SpannableString( getString(R.string.home_titulo) )
        titulo.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.black)), 9, titulo.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE )
        titulo.setSpan(UnderlineSpan(), 9, titulo.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvTitulo.text = titulo

        rvEtiquetas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        cargarRecyclerView(rvEtiquetas, this, "etiqueta", EtiquetaDTO::class, EtiquetaAdapter::class)

        rvCrowdfundings.layoutManager = LinearLayoutManager(this)
        cargarRecyclerView(rvCrowdfundings, this,"crowdfunding", CrowdfundingDTO::class, CrowdfundingAdapter::class)

        navigationBar.setOnClickListener { item ->
            when (item.id) {
                R.id.navbar_home -> {
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.navbar_fav -> {
                    // Cambiar a la pantalla de Search
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.navbar_menu -> {
                    // Cambiar a la pantalla de Profile
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }
    }

    private fun <T:Any, Y:RecyclerView.Adapter<*>> cargarRecyclerView(
        recyclerView: RecyclerView, context: Context, tabla: String, claseDTO: KClass<T>,
        claseAdapter: KClass<Y>, cantidad: Int = 10, columnas: String = "*", condicion: String = ""
        )
    {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = api!!.getEnTabla(tabla, condicion, columnas)

                withContext(Dispatchers.Main){
                    if(response.isEmpty())
                        Log.e("ApiError", "0 arrows en la query 'SELECT $columnas FROM $tabla WHERE $condicion'")
                    else if(response[0] is MensajeDTO)
                        (response[0] as MensajeDTO).message?.let { Log.e("ApiError", it) }
                    else
                        recyclerView.adapter = claseAdapter.primaryConstructor?.call(
                            response.map { it as T },
                            context
                        )
                }
            }
            catch (e: Exception){
                Log.e("ApiError",  e.message!!)
            }
        }
    }
}