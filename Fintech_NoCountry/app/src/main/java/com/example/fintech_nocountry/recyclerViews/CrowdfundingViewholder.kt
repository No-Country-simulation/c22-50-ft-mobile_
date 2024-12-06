package com.example.fintech_nocountry.recyclerViews

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fintech_nocountry.R

class CrowdfundingViewholder(view: View): RecyclerView.ViewHolder(view) {
    var imagen: ImageView = view.findViewById(R.id.item_crowd_imagen)
    var titulo: TextView = view.findViewById(R.id.item_crowd_titulo)
    var progressBar: ProgressBar = view.findViewById(R.id.item_crowd_progressbar)
    var recaudado: TextView = view.findViewById(R.id.item_crowd_recaudado)
    var porcentaje: TextView = view.findViewById(R.id.item_crowd_porcentaje)
    var tiempoRestante: TextView = view.findViewById(R.id.item_crowd_tiemporestante)
    var layout: RelativeLayout = view.findViewById(R.id.item_crowd_layout)
}