package com.example.dbreference.db

import com.example.dbreference.models.Buyurtma
import com.example.dbreference.models.Sotuvchi
import com.example.dbreference.models.Xaridor

interface MyDbInterface {

    fun addSalesmen(sotuvchi: Sotuvchi)
    fun addCustomer(xaridor: Xaridor)
    fun addOrder(buyurtma: Buyurtma)

    fun getAllSalesmen() :List<Sotuvchi>
    fun getAllCustomer() :List<Xaridor>
    fun getAllOrder() :List<Buyurtma>

    fun getSalesmanByid(id:Int):Sotuvchi
    fun getCustomerByid(id:Int):Xaridor

}