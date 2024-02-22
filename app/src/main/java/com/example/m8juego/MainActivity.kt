package com.example.m8juego

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.m8juego.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    //per a comprovar si la sessió esta inicialitzada
    lateinit var auth: FirebaseAuth
    var user: FirebaseUser? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser

        var LOGIN = findViewById<Button>(R.id.login);
        var REGISTRO = findViewById<Button>(R.id.registro);
        val tf = Typeface.createFromAsset(assets,"fonts/Pulang.ttf")
        REGISTRO.setTypeface(tf)
        LOGIN.setTypeface(tf)
        LOGIN.setOnClickListener(){
            val intent= Intent(this, Login::class.java)
            startActivity(intent)
        }
        REGISTRO.setOnClickListener(){
            //Toast.makeText(this, "click boton Registro",Toast.LENGTH_LONG).show();
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }
    // Aquest mètode s'executarà quan s'obri el menu
    override fun onStart() {
        usuariLogejat()
        super.onStart()
    }
    private fun usuariLogejat() {
        if (user != null) {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }
    }
}