package com.sistema.universidad

import android.content.Context
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class Listar : AppCompatActivity() {
    data class Alumno(
        val id: Int,
        val Nombres: String,
        val PrimerApellido: String,
        val SegundoApellido: String,
        val CorreoElectronico: String,
        val Celular: String,
        val Direccion: String,
        val Foto: String
    )

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)

        listView = findViewById(R.id.listadatos)

        // Crea un objeto OkHttpClient
        val client = OkHttpClient()
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        // Comprobar si el token es nulo
        if (token == null) {
            runOnUiThread {
                Toast.makeText(this@Listar, "No se ha iniciado sesión", Toast.LENGTH_SHORT).show()
            }
            return
        }

        // Crea un objeto Request
        val request = Request.Builder()
            .url("http://104.198.132.229/api/consultar")
            .addHeader("Authorization", "Bearer $token")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Muestra un mensaje de error
                runOnUiThread {
                    Toast.makeText(this@Listar, "Lo sentimos existen fallos de comunicación, verifique su conexión a Internet", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    // Parsea la respuesta JSON
                    val gson = Gson()
                    val type = object : TypeToken<List<Alumno>>() {}.type
                    val alumnos: List<Alumno> = gson.fromJson(response.body?.string(), type)

                    runOnUiThread {
                        // Asigna el ArrayAdapter al ListView
                        val adapter = AlumnoAdapter(this@Listar, alumnos)
                        listView.adapter = adapter
                    }
                }
            }
        })
    }
}