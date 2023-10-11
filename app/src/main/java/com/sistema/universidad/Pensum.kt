package com.sistema.universidad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.webkit.WebView
import android.webkit.WebViewClient

class Pensum : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pensum)
        val webView: WebView = findViewById(R.id.pensumweb)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        val pdfUrl = "https://apps2.umg.edu.gt/pensum/0909/2014.pdf"
        val googleDocsUrl = "https://docs.google.com/gview?embedded=true&url=$pdfUrl"
        webView.loadUrl(googleDocsUrl)

        //botón regresar
        val bRegresar=findViewById<Button>(R.id.regresar_escritorio2)
        bRegresar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Escritorio::class.java)
            startActivity(intent)
        })
        showGIF()
    }
    fun showGIF(){
        val imageView: ImageView = findViewById(R.id.pensum_2023)
        Glide.with(this).load(R.drawable.animacionpensum).into(imageView)
    }
}