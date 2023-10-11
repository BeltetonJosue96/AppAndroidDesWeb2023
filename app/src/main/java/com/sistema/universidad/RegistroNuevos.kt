package com.sistema.universidad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide

class RegistroNuevos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_nuevos)
        //botón regresar
        val bRegresar=findViewById<Button>(R.id.regresar_escritorio5)
        bRegresar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Escritorio::class.java)
            startActivity(intent)
        })
        //Botón para Registrar
        val bRegistar=findViewById<Button>(R.id.registrar)
        bRegistar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Registrar::class.java)
            startActivity(intent)
        })
        //Botón para Listar
        val bListar=findViewById<Button>(R.id.listar)
        bListar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Listar::class.java)
            startActivity(intent)
        })
        showGIF()
    }
    fun showGIF(){
        val imageView: ImageView = findViewById(R.id.imageSaludo2)
        Glide.with(this).load(R.drawable.contacto).into(imageView)
    }
}