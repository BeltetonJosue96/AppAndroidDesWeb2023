package com.sistema.universidad

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class Notas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        showGIF()
    }
    fun showGIF(){
        val imageView: ImageView = findViewById(R.id.buenas_notas)
        Glide.with(this).load(R.drawable.buenasnotas).into(imageView)
    }
}