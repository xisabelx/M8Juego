package com.example.m8juego

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.media3.common.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat.getDateInstance
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.UUID

class Registro : AppCompatActivity() {
    //Definim les variables que farem servir
    //lateinit ens permet no inicialitzar-les encara
    lateinit var correo: EditText
    lateinit var pass: EditText
    lateinit var nombre: EditText
    lateinit var fecha: TextView
    lateinit var Registrar: Button
    lateinit var auth: FirebaseAuth //FIREBASE AUTENTIFICACIO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        correo = findViewById<EditText>(R.id.correo)
        pass = findViewById<EditText>(R.id.pass)
        nombre = findViewById<EditText>(R.id.nombre)
        fecha = findViewById<TextView>(R.id.fecha)
        Registrar = findViewById<Button>(R.id.Registrar)

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance() //or use
        getDateInstance()
        val formatedDate = formatter.format(date)
        //ara la mostrem al TextView
        fecha.text = formatedDate

        //Instanciem el firebaseAuth
        auth = FirebaseAuth.getInstance()

        Registrar.setOnClickListener {
            //Abans de fer el registre validem les dades
            val email: String = correo.text.toString()
            val passw: String = pass.text.toString()
            // validació del correu
            // si no es de tipus correu
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                correo.setError("Invalid Mail")
            } else if (passw.length < 6) {
                pass.setError("Password less than 6 chars")
            } else {
                RegistrarJugador(email, passw)
            }


        }

    }
    fun RegistrarJugador(email: String, passw: String) {
        auth.createUserWithEmailAndPassword(email, passw)  // Corregir aquí
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "createUserWithEmail:success", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }
    fun updateUI(user: FirebaseUser?){
        //hi ha un interrogant perquè podria ser null
        if (user!=null)
        {
            var puntuacio: Int = 0
            var uidString: String = user.uid
            var correoString: String = correo.getText().toString()
            var passString: String = pass.getText().toString()
            var nombreString: String = nombre.getText().toString()
            var fechaString: String= fecha.getText().toString()
            var dadesJugador : HashMap<String,String> = HashMap<String, String>()
            dadesJugador.put ("Uid",uidString)
            dadesJugador.put ("Email",correoString)
            dadesJugador.put ("Password",passString)
            dadesJugador.put ("Nom",nombreString)
            dadesJugador.put ("Data",fechaString)
            dadesJugador.put ("Puntuacio", puntuacio.toString())
            // Creem un punter a la base de dades i li donem un nom
            var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://m8juego-e9538-default-rtdb.europe-west1.firebasedatabase.app/")
            var reference: DatabaseReference = database.getReference("DATA BASE JUGADORS")
            if(reference!=null) {
                //crea un fill amb els valors de dadesJugador
                //Log.i ("MYTAG", reference.toString())
                //Log.i ("MYTAG", uidString)
                //Log.i ("MYTAG", dadesJugador.toString())
                reference.child(uidString).setValue(dadesJugador)
                Toast.makeText(this, "USUARI BEN REGISTRAT", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Menu::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "ERROR BD", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
        else
        {
            Toast.makeText( this,"ERROR CREATE USER",Toast.LENGTH_SHORT).show()
        }
    }

}