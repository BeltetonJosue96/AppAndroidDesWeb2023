package com.sistema.universidad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

class Principal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //botón de ingresar
        val bEntrar=findViewById<Button>(R.id.entrar)
        bEntrar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Escritorio::class.java)
            startActivity(intent)
        })
        showGIF()
    }
    fun showGIF(){
        val imageView:ImageView = findViewById(R.id.bienvenida)
        Glide.with(this).load(R.drawable.bienvenido).into(imageView)
    }
}