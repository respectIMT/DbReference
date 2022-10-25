package com.example.dbreference.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dbreference.databinding.ItemSotuvchiRvBinding
import com.example.dbreference.models.Sotuvchi
import com.example.dbreference.models.Xaridor
import java.lang.Exception

class SotuvchiRvAdapter<T>(val list:List<T>) : RecyclerView.Adapter<SotuvchiRvAdapter<T>.Vh>() {
    inner class Vh(val itemRvBinding: ItemSotuvchiRvBinding ) : RecyclerView.ViewHolder(itemRvBinding.root){
        fun onBindSotuvchi(sotuvchi: Sotuvchi, position: Int){
            itemRvBinding.tvName.text = sotuvchi.name
            itemRvBinding.tvNumber.text = sotuvchi.number
        }
        fun onBindXaridor(xaridor: Xaridor, position: Int){
            itemRvBinding.tvName.text = xaridor.name
            itemRvBinding.tvNumber.text = xaridor.number
            itemRvBinding.tvAddress.visibility = View.VISIBLE
            itemRvBinding.tvAddress.text = xaridor.address

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemSotuvchiRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        try {
            val sotuvchi : Sotuvchi = list[position] as Sotuvchi
            holder.onBindSotuvchi(sotuvchi, position)
        }catch (e:Exception){

        }
        try {
            val xaridor : Xaridor = list[position] as Xaridor
            holder.onBindXaridor(xaridor, position)

        }catch (e:Exception){

        }

    }

    override fun getItemCount(): Int {
        return list.size

    }
}