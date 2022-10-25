package com.example.dbreference.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.dbreference.adapters.BuyurtmaAdapter
import com.example.dbreference.databinding.FragmentBuyurtmaBinding
import com.example.dbreference.databinding.ItemDialogOrdersBinding
import com.example.dbreference.db.MyDbHelper
import com.example.dbreference.models.Buyurtma

class BuyurtmaFragment : Fragment() {
    private lateinit var binding: FragmentBuyurtmaBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var list: ArrayList<Buyurtma>
    private lateinit var buyurtmaAdapter: BuyurtmaAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuyurtmaBinding.inflate(layoutInflater)

        myDbHelper = MyDbHelper(context)
        list = ArrayList()
        list.addAll(myDbHelper.getAllOrder())
        buyurtmaAdapter = BuyurtmaAdapter(list)
        binding.rvBuyurtma.adapter = buyurtmaAdapter
        addBuyurtma()

        return binding.root
    }

    private fun addBuyurtma() {
        binding.btnAdd.setOnClickListener{
            val dialog = AlertDialog.Builder(binding.root.context).create()
            val itemDialogOrdersBinding = ItemDialogOrdersBinding.inflate(layoutInflater)
            dialog.setView(itemDialogOrdersBinding.root)
            dialog.show()

            val listSotuvchi = myDbHelper.getAllSalesmen()
            val listSotuvchiName = ArrayList<String>()
            listSotuvchi.forEach{
                listSotuvchiName.add(it.name)
            }
            val sAdapter =ArrayAdapter<String>(binding.root.context, android.R.layout.simple_list_item_1, listSotuvchiName)
            itemDialogOrdersBinding.spinnerSotuvchi.adapter = sAdapter

            val listXaridor = myDbHelper.getAllCustomer()
            val listXaridorName = ArrayList<String>()
            listXaridor.forEach{
                listXaridorName.add(it.name)
            }
            val xAdapter =ArrayAdapter<String>(binding.root.context, android.R.layout.simple_list_item_1, listXaridorName)
            itemDialogOrdersBinding.spinnerXaridor.adapter = xAdapter

            itemDialogOrdersBinding.btnSave.setOnClickListener{

                var nomi = itemDialogOrdersBinding.edtName.text.toString()
                var narxi = itemDialogOrdersBinding.edtPrice.text.toString()
                var spinner_sotuvchi = itemDialogOrdersBinding.spinnerSotuvchi.toString()
                var spinner_xaridor = itemDialogOrdersBinding.spinnerXaridor.toString()

                if (nomi!= "" && narxi!= "" && spinner_sotuvchi!="" && spinner_xaridor!=""){

                    val buyurtma = Buyurtma(

                        itemDialogOrdersBinding.edtName.text.toString(),
                        itemDialogOrdersBinding.edtPrice.text.toString().toInt(),
                        listSotuvchi[itemDialogOrdersBinding.spinnerSotuvchi.selectedItemPosition],
                        listXaridor[itemDialogOrdersBinding.spinnerXaridor.selectedItemPosition]

                    )
                    myDbHelper.addOrder(buyurtma)
                    list.add(buyurtma)
                    buyurtmaAdapter.notifyItemInserted(list.size-1)
                    Toast.makeText(context, "Save", Toast.LENGTH_SHORT ).show()
                    dialog.cancel()

                }else{
                    Toast.makeText(requireContext(), "Malumotlarni to'ldiring", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}