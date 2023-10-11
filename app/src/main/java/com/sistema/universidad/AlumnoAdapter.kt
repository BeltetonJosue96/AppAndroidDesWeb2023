package com.sistema.universidad

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class AlumnoAdapter(context: Context, private val alumnos: List<Listar.Alumno>) : ArrayAdapter<Listar.Alumno>(context, R.layout.list_item, alumnos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val alumno = alumnos[position]

        // Eliminar "data:image/jpeg;base64," para obtener solo los datos de la imagen
        val pureBase64Encoded = alumno.Foto.substring(alumno.Foto.indexOf(",") + 1)

        // Convierte la cadena base64 a un bitmap
        val bytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

        // Muestra la foto y los nombres en el layout
        val foto = view.findViewById<ImageView>(R.id.foto)
        val id = view.findViewById<TextView>(R.id.id)
        val nombres = view.findViewById<TextView>(R.id.nombres)
        val priApellido = view.findViewById<TextView>(R.id.priapellido)
        val secApellido = view.findViewById<TextView>(R.id.secapellido)
        val correo = view.findViewById<TextView>(R.id.correo)
        val celular = view.findViewById<TextView>(R.id.celular)
        val direccion = view.findViewById<TextView>(R.id.direccion)

        foto.setImageBitmap(bitmap)
        id.text = "ID: " + alumno.id.toString()
        nombres.text = "Nombres: " + alumno.Nombres
        priApellido.text = "Primer Apellido: " + alumno.PrimerApellido.toString()
        secApellido.text = "Segundo Apellido: " + alumno.SegundoApellido.toString()
        correo.text = "Correo Electrónico: " + alumno.CorreoElectronico.toString()
        celular.text = "Número de Celular: " + alumno.Celular.toString()
        direccion.text = "Dirección: " + alumno.Direccion.toString()

        return view
    }
}
