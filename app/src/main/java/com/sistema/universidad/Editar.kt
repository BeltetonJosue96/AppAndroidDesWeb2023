package com.sistema.universidad

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException

class Editar : AppCompatActivity() {
    private lateinit var editNombre: EditText
    private lateinit var editPriApellido: EditText
    private lateinit var editSecApellido: EditText
    private lateinit var editEmail: EditText
    private lateinit var editTelefono: EditText
    private lateinit var editDireccion: EditText
    private lateinit var botonAdjuntarImagen: Button
    private lateinit var botonguardar: Button
    private var imagenBase64: String? = null
    private val CODIGO_GALERIA = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        // Inicializar vistas
        editNombre = findViewById(R.id.editNombre)
        editPriApellido = findViewById(R.id.editPriApellido)
        editSecApellido = findViewById(R.id.editSecApellido)
        editEmail = findViewById(R.id.editEmail)
        editTelefono = findViewById(R.id.editTelefono)
        editDireccion = findViewById(R.id.editDireccion)
        botonAdjuntarImagen = findViewById(R.id.botonAdjuntarImagen)
        botonguardar = findViewById(R.id.botonguardar)

        botonAdjuntarImagen.setOnClickListener {
            abrirGaleria()
        }
        botonguardar.setOnClickListener {
            if (validarCampos()) {
                enviarDatos()
            }
        }
    }

    private fun abrirGaleria() {
        val elegirDeGaleriaIntent = Intent(Intent.ACTION_GET_CONTENT)
        elegirDeGaleriaIntent.type = "image/*"
        startActivityForResult(elegirDeGaleriaIntent, CODIGO_GALERIA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODIGO_GALERIA && resultCode == Activity.RESULT_OK) {
            val imagenUri = data?.data
            val imagenBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imagenUri)
            val byteArrayOutputStream = ByteArrayOutputStream()
            imagenBitmap.compress(Bitmap.CompressFormat.JPEG, 1, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            var imagenBase64 = Base64.encodeToString(byteArray, Base64.NO_WRAP)
            imagenBase64 = "data:image/jpeg;base64," + imagenBase64
            this.imagenBase64 = imagenBase64
        }
    }

    private fun validarCampos(): Boolean {
        val nombre = editNombre.text.toString()
        val priApellido = editPriApellido.text.toString()
        val secApellido = editSecApellido.text.toString()
        val email = editEmail.text.toString()
        val telefono = editTelefono.text.toString()
        val direccion = editDireccion.text.toString()

        if (nombre.isEmpty() || priApellido.isEmpty() || secApellido.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(this, "Por favor, rellene todos los campos.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(
                this,
                "Por favor, introduzca un correo electrónico válido.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (telefono.length != 8) {
            Toast.makeText(this, "El número de teléfono debe tener 8 dígitos.", Toast.LENGTH_SHORT)
                .show()
            return false
        }

        if (imagenBase64 == null) {
            Toast.makeText(this, "Por favor, adjunte una imagen.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun enviarDatos() {
        val json = JSONObject()
        json.put("Nombres", editNombre.text.toString())
        json.put("PrimerApellido", editPriApellido.text.toString())
        json.put("SegundoApellido", editSecApellido.text.toString())
        json.put("CorreoElectronico", editEmail.text.toString())
        json.put("Celular", editTelefono.text.toString())
        json.put("Direccion", editDireccion.text.toString())
        json.put("Foto", imagenBase64)

        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json.toString())

        val request = Request.Builder()
            .url("http://104.198.132.229/api/agregar")
            .post(requestBody)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@Editar, "Lo sentimos, no se pudo completar la petición", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val respuesta = JSONObject(response.body?.string())
                    val mensaje = respuesta.getString("mensaje")
                    runOnUiThread {
                        Toast.makeText(this@Editar, mensaje, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@Editar, "Lo sentimos, no se pudo completar la petición", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
