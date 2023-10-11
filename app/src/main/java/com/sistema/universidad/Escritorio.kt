package com.sistema.universidad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide

class Escritorio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escritorio)
        //Botón para regresar
        val bSalir=findViewById<Button>(R.id.logout)
        bSalir.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Principal::class.java)
            startActivity(intent)
        })
        //Botón para notas
        val bNotas=findViewById<Button>(R.id.notas)
        bNotas.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Notas::class.java)
            startActivity(intent)
        })
        //Botón para pensum
        val bPensum=findViewById<Button>(R.id.pensum)
        bPensum.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Pensum::class.java)
            startActivity(intent)
        })
        //Botón para horarios
        val bHorario=findViewById<Button>(R.id.horarios)
        bHorario.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Horario::class.java)
            startActivity(intent)
        })
        //Botón para estado de cuenta, ahora sera interesados
        val bCuenta=findViewById<Button>(R.id.registros)
        bCuenta.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,RegistroNuevos::class.java)
            startActivity(intent)
        })

        showGIF()
    }
    fun showGIF(){
        val imageView: ImageView = findViewById(R.id.imageSaludo)
        Glide.with(this).load(R.drawable.saludo).into(imageView)
    }
}