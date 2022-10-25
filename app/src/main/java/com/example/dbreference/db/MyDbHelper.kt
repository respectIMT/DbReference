package com.example.dbreference.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dbreference.models.Buyurtma
import com.example.dbreference.models.Sotuvchi
import com.example.dbreference.models.Xaridor

class MyDbHelper(context: Context?)
    : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), MyDbInterface{

    companion object{
        const val DB_NAME = "Market_db"
        const val DB_VERSION = 1

        const val SOTUVCHI_TABLE = "Sotuvchi_table"
        const val SOTUVCHI_ID = "id"
        const val SOTUVCHI_NAME = "name"
        const val SOTUVCHI_NUMBER = "number"

        const val XARIDOR_TABLE = "Xaridor_table"
        const val XARIDOR_ID = "id"
        const val XARIDOR_NAME = "name"
        const val XARIDOR_NUMBER = "number"
        const val XARIDOR_ADDRESS = "address"

        const val BUYURTMA_TABLE = "orders"
        const val BUYURTMA_ID = "id"
        const val BUYURTMA_NAME = "name"
        const val BUYURTMA_PRICE ="price"
        const val BUYURTMA_SOTUVCHI_ID = "sotuvchi_id"
        const val BUYURTMA_XARIDOR_ID = "xaridor_id"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val querySotuvchi = "create table $SOTUVCHI_TABLE ($SOTUVCHI_ID integer not null primary key autoincrement unique, $SOTUVCHI_NAME text not null, $SOTUVCHI_NUMBER text not null )"
        val queryXaridor = "create table $XARIDOR_TABLE ($XARIDOR_ID integer not null primary key autoincrement unique, $XARIDOR_NAME text not null, $XARIDOR_NUMBER text not null, $XARIDOR_ADDRESS text not null )"
        val queryBuyurtma = "create table $BUYURTMA_TABLE ($BUYURTMA_ID integer not null primary key autoincrement unique, $BUYURTMA_NAME text not null, $BUYURTMA_PRICE integer not null, $BUYURTMA_SOTUVCHI_ID integer not null, $BUYURTMA_XARIDOR_ID integer not null, foreign key($BUYURTMA_SOTUVCHI_ID) references $SOTUVCHI_TABLE ($SOTUVCHI_ID), foreign key ($BUYURTMA_XARIDOR_ID) references $XARIDOR_TABLE($XARIDOR_ID) )"
        db?.execSQL(querySotuvchi)
        db?.execSQL(queryXaridor)
        db?.execSQL(queryBuyurtma)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addSalesmen(sotuvchi: Sotuvchi) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SOTUVCHI_NAME, sotuvchi.name)
        contentValues.put(SOTUVCHI_NUMBER, sotuvchi.number)
        database.insert(SOTUVCHI_TABLE, null,contentValues)
        database.close()
    }

    override fun addCustomer(xaridor: Xaridor) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(XARIDOR_NAME, xaridor.name)
        contentValues.put(XARIDOR_NUMBER, xaridor.number)
        contentValues.put(XARIDOR_ADDRESS, xaridor.address)
        database.insert(XARIDOR_TABLE, null,contentValues)
        database.close()
    }

    override fun addOrder(buyurtma: Buyurtma) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(BUYURTMA_NAME, buyurtma.name)
        contentValues.put(BUYURTMA_PRICE, buyurtma.price)
        contentValues.put(BUYURTMA_SOTUVCHI_ID, buyurtma.sotuvchi?.id)
        contentValues.put(BUYURTMA_XARIDOR_ID, buyurtma.xaridor?.id)
        database.insert(BUYURTMA_TABLE, null,contentValues)
        database.close()
    }

    override fun getAllSalesmen(): List<Sotuvchi> {
        val list = ArrayList<Sotuvchi>()
        val query = "select * from $SOTUVCHI_TABLE"
        val database = readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToNext()){
            do {
                list.add(
                    Sotuvchi(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                    )
                )
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllCustomer(): List<Xaridor> {
        val list = ArrayList<Xaridor>()
        val query = "select * from $XARIDOR_TABLE"
        val database = readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToNext()){
            do {
                list.add(
                    Xaridor(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                    )
                )
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllOrder(): List<Buyurtma> {
        val list = ArrayList<Buyurtma>()
        val database = readableDatabase
        val query = "select * from $BUYURTMA_TABLE"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                list.add(Buyurtma(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    getSalesmanByid(cursor.getInt(3)),
                    getCustomerByid(cursor.getInt(4))

                ))

            }while (cursor.moveToNext())
        }

        return list
    }

    override fun getSalesmanByid(id: Int): Sotuvchi {
        val database = readableDatabase
        val cursor = database.query(
            SOTUVCHI_TABLE,
            arrayOf(
                SOTUVCHI_ID,
                SOTUVCHI_NAME,
                SOTUVCHI_NUMBER
            ),
            "$SOTUVCHI_ID = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val sotuvchi = Sotuvchi(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2)
        )
        return sotuvchi
    }

    override fun getCustomerByid(id: Int): Xaridor {
        val database = readableDatabase
        val cursor = database.query(
            XARIDOR_TABLE,
            arrayOf(
                XARIDOR_ID,
                XARIDOR_NAME,
                XARIDOR_NUMBER,
                XARIDOR_ADDRESS
            ),
            "$XARIDOR_ID = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val xaridor = Xaridor(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3)
        )
        return xaridor
    }
}