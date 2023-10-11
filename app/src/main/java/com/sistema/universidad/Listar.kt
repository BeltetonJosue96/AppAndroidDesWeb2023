package com.sistema.universidad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.ArrayAdapter

class Listar : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)

        listView = findViewById(R.id.listadatos)
        dbHelper = DBHelper(this)

        val dataList = getDataFromDatabase()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = dataList[position]
            val parts = selectedItem.split("\n")
            val idString = parts[0]
            val selectedId = idString.toLong() // Convertir el valor del ID a Long
            val intent = Intent(this, Editar::class.java)
            intent.putExtra("selectedItemID", selectedId)
            startActivity(intent)
        }

        //botón regresar
        val bRegresar=findViewById<Button>(R.id.regresar_escritorio7)
        bRegresar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,RegistroNuevos::class.java)
            startActivity(intent)
        })
    }

    private fun getDataFromDatabase(): List<String> {
        val dataList = mutableListOf<String>()
        val database = dbHelper.readableDatabase

        val projection = arrayOf(
            DBHelper.COLUMN_ID,
            DBHelper.COLUMN_NOMBRE,
            DBHelper.COLUMN_APELLIDOS,
            DBHelper.COLUMN_CORREO,
            DBHelper.COLUMN_TELEFONO
        )

        val cursor = database.query(
            DBHelper.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NOMBRE))
            val apellidos = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_APELLIDOS))
            val correo = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CORREO))
            val telefono = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TELEFONO))

            dataList.add("$id\n$nombre $apellidos\n$correo\n$telefono")
        }

        cursor.close()
        database.close()

        return dataList
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        val dataList = getDataFromDatabase()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList)
        listView.adapter = adapter
    }
}