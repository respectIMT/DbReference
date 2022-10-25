package com.example.dbreference.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dbreference.databinding.ItemSotuvchiRvBinding
import com.example.dbreference.models.Buyurtma

class BuyurtmaAdapter(val list:List<Buyurtma>) : RecyclerView.Adapter<BuyurtmaAdapter.Vh>() {
    inner class Vh(val itemRvBinding: ItemSotuvchiRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root){

        fun onBind(buyurtma: Buyurtma, position: Int){
            itemRvBinding.tvName.text = buyurtma.name
            itemRvBinding.tvNumber.text = buyurtma.price.toString()
            itemRvBinding.tvAddress.visibility = View.VISIBLE
            itemRvBinding.tvAddress.text = buyurtma.sotuvchi?.name
            itemRvBinding.tvXaridor.visibility = View.VISIBLE
            itemRvBinding.tvXaridor.text = buyurtma.xaridor?.name

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemSotuvchiRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)

    }
    override fun getItemCount(): Int {
        return list.size

    }
}