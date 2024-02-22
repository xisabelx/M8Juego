package com.example.m8juego

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.m8juego.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var LOGIN = findViewById<Button>(R.id.login);
        var REGISTRO = findViewById<Button>(R.id.registro);
        LOGIN.setOnClickListener(){
            val intent= Intent(this, Login::class.java)
            startActivity(intent)
        }
        REGISTRO.setOnClickListener(){
            Toast.makeText(this, "click boton Registro",Toast.LENGTH_LONG).show();
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }
}