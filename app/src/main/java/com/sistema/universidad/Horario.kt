package com.sistema.universidad

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class Horario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horario)

        showGIF()
    }
    fun showGIF(){
        val imageView: ImageView = findViewById(R.id.horario2023)
        Glide.with(this).load(R.drawable.animacionhorario).into(imageView)
    }
}