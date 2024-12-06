package com.example.fintech_nocountry.recyclerViews

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fintech_nocountry.DetalleActivity
import com.example.fintech_nocountry.R
import com.example.fintech_nocountry.consumoApiRest.dto.CrowdfundingDTO
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrowdfundingAdapter(
    private var lstCrowdfundings: MutableList<CrowdfundingDTO>,
    private var context: Context)
    : RecyclerView.Adapter<CrowdfundingViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrowdfundingViewholder {
        return CrowdfundingViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_crowdfunding, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return lstCrowdfundings.size
    }

    override fun onBindViewHolder(holder: CrowdfundingViewholder, position: Int) {
        val item = lstCrowdfundings[position]
        holder.titulo.text = item.titulo
        holder.recaudado.text = "US$ ${item.montoRecaudado}"
        holder.porcentaje.text = "${item.calcularPorcentajeActual()}%"
        holder.tiempoRestante.text = "${item.calcularDiasRestantes()}"
        holder.progressBar.progress = item.calcularPorcentajeActual()

        holder.layout.setOnClickListener{
            context.startActivity(
                Intent(context, DetalleActivity::class.java)
                    .putExtra("crowdfunding", item)
            )
        }
         try{
                Picasso
                    .get()
                    .load(item.imagenUrl)
                    .into(holder.imagen)
         } catch(e: Exception){
                Log.e("PicassoError", e.message!!)
            }
    }

}