package com.sistema.universidad

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri

class Registrar : AppCompatActivity() {
    private lateinit var editNombre: EditText
    private lateinit var editApellidos: EditText
    private lateinit var editSecApellido: EditText
    private lateinit var editEmail: EditText
    private lateinit var editTelefono: EditText
    private lateinit var editDireccion: EditText

    private lateinit var botonguardar: Button

    fun validarCampos(): Boolean {
        val nombre = editNombre.text.toString()
        val apellidos = editApellidos.text.toString()
        val correo = editEmail.text.toString()
        val telefono = editTelefono.text.toString()

        if (nombre.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return false
        }

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!correo.matches(emailPattern.toRegex())) {
            Toast.makeText(this, "El correo electrónico no es válido", Toast.LENGTH_SHORT).show()
            return false
        }

        if (telefono.length != 8 || !telefono.matches("\\d+".toRegex())) {
            Toast.makeText(this, "El número de teléfono no es válido", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
        val dbHelper = DBHelper(this)
        val database = dbHelper.writableDatabase

        //Declaración de variables para encontrar datos en campos de texto.
        editNombre=findViewById(R.id.editNombre)
        editApellidos=findViewById(R.id.editApellidos)
        editEmail=findViewById(R.id.editEmail)
        editTelefono=findViewById(R.id.editTelefono)
        botonguardar = findViewById(R.id.botonguardar)

        botonguardar.setOnClickListener {
            if (validarCampos()) {
                val nombre = editNombre.text.toString()
                val apellidos = editApellidos.text.toString()
                val correo = editEmail.text.toString()
                val telefono = editTelefono.text.toString()

                val values = ContentValues().apply {
                    put(DBHelper.COLUMN_NOMBRE, nombre)
                    put(DBHelper.COLUMN_APELLIDOS, apellidos)
                    put(DBHelper.COLUMN_CORREO, correo)
                    put(DBHelper.COLUMN_TELEFONO, telefono)
                }

                val newRowId = database.insert(DBHelper.TABLE_NAME, null, values)
                database.close()
                if (newRowId != -1L) {
                    Toast.makeText(this, "El interesado ha sido registrado correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                    // Iniciar una nueva instancia de la misma actividad para ingresar un nuevo registro
                    val intent = Intent(this, Registrar::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Error al registrar el interesado", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}