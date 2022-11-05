package com.example.figutrader.ui.album

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.figutrader.R
import kotlin.math.log

//class FiguritasAdapter(private val myDataset: MutableList<Figurita>) :
//    RecyclerView.Adapter<FiguritasAdapter.MyViewHolder>() {
//
//    private val VIEWTYPE_COUNTRY: Int = 1
//    private val VIEWTYPE_FIGURITA: Int = 2
//
//    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)
//
//    override fun onCreateViewHolder(parent: ViewGroup,
//                                    viewType: Int): FiguritasAdapter.MyViewHolder {
//        val view : View = if(viewType == VIEWTYPE_COUNTRY)
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.country, parent, false)
//        else
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.figurita, parent, false)
//
//        return MyViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.view.findViewById<TextView>(R.id.figurita_nombre).text = myDataset[position].nombre
//        if(!myDataset[position].esPais) {
//            holder.view.findViewById<TextView>(R.id.figurita_cantidad).text =
//                myDataset[position].cantidad.toString()
//            if(myDataset[position].cantidad == 0) {
//                holder.view.findViewById<TextView>(R.id.figurita_nombre).setBackgroundResource(R.color.gris_figurita)
//            }
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return if(myDataset[position].esPais)
//            VIEWTYPE_COUNTRY
//        else
//            VIEWTYPE_FIGURITA
//    }
//
//    override fun getItemCount() = myDataset.size
//}