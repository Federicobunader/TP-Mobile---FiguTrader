package com.example.figutrader.ui.album

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.figutrader.R

interface FiguClickedListener {
    fun onFiguClicked(figurita: FiguritaDataView)
}

class FiguritasAdapter(var myDataset: List<FiguritaDataView>, val figuClickedListener: FiguClickedListener) :
    RecyclerView.Adapter<FiguritasAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.figurita, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.figurita_nombre).text =
            myDataset[position].descripcion

        holder.view.findViewById<TextView>(R.id.CantidadTextView).text =
            myDataset[position].cantidad.toString()

        if (myDataset[position].cantidad == 0) {
            holder.view.findViewById<TextView>(R.id.figurita_nombre)
                .setBackgroundResource(R.color.gris_figurita)
        }
        else {
            holder.view.findViewById<TextView>(R.id.figurita_nombre)
                .setBackgroundResource(R.color.verde_figurita)
        }

        holder.itemView.setOnClickListener{ figuClickedListener.onFiguClicked(myDataset[position])}
    }

    override fun getItemCount() = myDataset.size
}