package com.example.m8juego

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Menu : AppCompatActivity() {
    //creem unes variables per comprovar ususari i authentificació
    lateinit var auth: FirebaseAuth
    var user: FirebaseUser? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        auth= FirebaseAuth.getInstance()
        user =auth.currentUser

    }
    override fun onStart() {
        usuariLogejat()
        super.onStart()
    }
    private fun usuariLogejat()
    {
        if (user !=null)
        {
            Toast.makeText(this,"Jugador logejat",
                Toast.LENGTH_SHORT).show()
        }
        else
        {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}