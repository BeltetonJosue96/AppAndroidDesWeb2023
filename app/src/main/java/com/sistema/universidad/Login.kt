package com.sistema.universidad

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import java.io.IOException

class Login : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private var token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            if (validarCampos()) {
                enviarDatos()
            }
        }
        //Botón de Registrar que abre WebView
        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("http://crudyapibyjb.tech/register")
            startActivity(openURL)
        }
    }

    private fun validarCampos(): Boolean {
        val correo = email.text.toString()
        val contra = password.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(
                this,
                "Por favor, introduzca un correo electrónico válido.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        if (contra.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese su contraseña.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    private fun enviarDatos() {
        val json = JSONObject()
        json.put("email", email.text.toString())
        json.put("password", password.text.toString())
        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json.toString())
        val request = Request.Builder()
            .url("http://crudyapibyjb.tech/api/login")
            .post(requestBody)
            .build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@Login, "Lo sentimos existen fallos de comunicación, verifique su conexión a Internet", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val respuesta = JSONObject(response.body?.string())
                    val token = respuesta.getString("token")

                    // Almacenar el token en las Preferencias Compartidas
                    val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                    with (sharedPref.edit()) {
                        putString("token", token)
                        apply()
                    }

                    runOnUiThread {
                        Toast.makeText(this@Login, "Sesión iniciada con éxito", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Login, Escritorio::class.java)
                        startActivity(intent)
                    }
                }
                else {
                    runOnUiThread {
                        Toast.makeText(this@Login, "Lo sentimos, no se pudo iniciar sesión, verifique su correo y contraseña", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}