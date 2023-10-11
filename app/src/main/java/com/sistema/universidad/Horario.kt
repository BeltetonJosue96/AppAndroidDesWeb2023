package com.sistema.universidad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide

class Horario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horario)
        //botón regresar
        val bRegresar=findViewById<Button>(R.id.regresar_escritorio3)
        bRegresar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Escritorio::class.java)
            startActivity(intent)
        })
        showGIF()
    }
    fun showGIF(){
        val imageView: ImageView = findViewById(R.id.horario2023)
        Glide.with(this).load(R.drawable.animacionhorario).into(imageView)
    }
}