package com.sistema.universidad

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class Principal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //botón de Iniciar Sesión
        val bEntrar=findViewById<Button>(R.id.entrar)
        bEntrar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        })
        showGIF()
    }
    fun showGIF(){
        val imageView:ImageView = findViewById(R.id.bienvenida)
        Glide.with(this).load(R.drawable.bienvenido).into(imageView)
    }
}