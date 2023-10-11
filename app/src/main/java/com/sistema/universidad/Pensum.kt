package com.sistema.universidad

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

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

        showGIF()
    }
    fun showGIF(){
        val imageView: ImageView = findViewById(R.id.pensum_2023)
        Glide.with(this).load(R.drawable.animacionpensum).into(imageView)
    }
}