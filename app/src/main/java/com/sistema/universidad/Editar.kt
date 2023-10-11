package com.sistema.universidad

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Editar : AppCompatActivity() {
    private lateinit var editNombre: EditText
    private lateinit var editApellidos: EditText
    private lateinit var editEmail: EditText
    private lateinit var editTelefono: EditText
    private lateinit var btnGuardarEdicion: Button

    private lateinit var dbHelper: DBHelper
    private var selectedItemID: Long = -1

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)

        editNombre = findViewById(R.id.editNombre2)
        editApellidos = findViewById(R.id.editApellidos2)
        editEmail = findViewById(R.id.editEmail2)
        editTelefono = findViewById(R.id.editTelefono2)
        btnGuardarEdicion = findViewById(R.id.botonguardar2)

        dbHelper = DBHelper(this)

        // Obtener el ID del item seleccionado de la actividad anterior
        selectedItemID = intent.getLongExtra("selectedItemID", -1)

        // Obtener los datos del objeto seleccionado de la base de datos y completar los campos
        val database = dbHelper.readableDatabase
        val projection = arrayOf(
            DBHelper.COLUMN_NOMBRE,
            DBHelper.COLUMN_APELLIDOS,
            DBHelper.COLUMN_CORREO,
            DBHelper.COLUMN_TELEFONO
        )
        val selection = "${DBHelper.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(selectedItemID.toString())

        val cursor = database.query(
            DBHelper.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            editNombre.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NOMBRE)))
            editApellidos.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_APELLIDOS)))
            editEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CORREO)))
            editTelefono.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TELEFONO)))
        }

        cursor.close()
        database.close()

        btnGuardarEdicion.setOnClickListener {
            if (validarCampos()) {
                // Actualizar los datos en la base de datos
                val database = dbHelper.writableDatabase
                val values = ContentValues().apply {
                    put(DBHelper.COLUMN_NOMBRE, editNombre.text.toString())
                    put(DBHelper.COLUMN_APELLIDOS, editApellidos.text.toString())
                    put(DBHelper.COLUMN_CORREO, editEmail.text.toString())
                    put(DBHelper.COLUMN_TELEFONO, editTelefono.text.toString())
                }

                val selection = "${DBHelper.COLUMN_ID} = ?"
                val selectionArgs = arrayOf(selectedItemID.toString())

                val updatedRows = database.update(DBHelper.TABLE_NAME, values, selection, selectionArgs)
                database.close()

                if (updatedRows > 0) {
                    Toast.makeText(
                        this,
                        "Los datos han sido actualizados correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this, "No se pudieron actualizar los datos", Toast.LENGTH_SHORT)
                        .show()
                }
                finish() // Volver a la actividad anterior
            }
        }

        // Después de inicializar tu botón de eliminar
        val botonEliminar = findViewById<Button>(R.id.botonEliminar)

        botonEliminar.setOnClickListener {
            // Eliminar el registro de la base de datos
            val database = dbHelper.writableDatabase
            val selection = "${DBHelper.COLUMN_ID} = ?"
            val selectionArgs = arrayOf(selectedItemID.toString())

            val deletedRows = database.delete(DBHelper.TABLE_NAME, selection, selectionArgs)
            database.close()

            if (deletedRows > 0) {
                Toast.makeText(this, "El registro ha sido eliminado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se pudo eliminar el registro", Toast.LENGTH_SHORT).show()
            }

            finish() // Volver a la actividad anterior
        }

        //botón regresar
        val bRegresar=findViewById<Button>(R.id.regresar_escritorio8)
        bRegresar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Listar::class.java)
            startActivity(intent)
        })
    }
}