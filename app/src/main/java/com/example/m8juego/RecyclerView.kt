package com.example.m8juego

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m8juego.adapter.JugadorsAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RecyclerView : AppCompatActivity() {

    private lateinit var dbref: DatabaseReference
    private lateinit var jugadorArrayList: ArrayList<Jugador>
    private lateinit var jugadorRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initRecyclerView()
    }

    fun initRecyclerView(){
        jugadorRecyclerView = findViewById(R.id.RecyclerOne)
        jugadorRecyclerView.layoutManager= LinearLayoutManager(this)
        jugadorArrayList = arrayListOf<Jugador>()
        getJugadorData()
    }

    private fun getJugadorData() {
        dbref = FirebaseDatabase.getInstance("https://m8juego-e9538-default-rtdb.europe-west1.firebasedatabase.app/").getReference("DATA BASE JUGADORS")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    jugadorArrayList.clear() // Limpiar la lista antes de agregar los datos
                    for (jugadorSnapshot in snapshot.children) {
                        val jugador = jugadorSnapshot.getValue(Jugador::class.java)
                        jugador?.let {
                            jugadorArrayList.add(it) // Agregar el jugador a la lista
                        }
                    }
                    // Ordenar la lista de jugadores por puntuación en orden descendente
                    jugadorArrayList.sortByDescending { it.Puntuacio.toIntOrNull() ?: 0 }
                    jugadorRecyclerView.adapter = JugadorsAdapter(jugadorArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar error de cancelación
                TODO("Not yet implemented")
            }
        })
    }


    fun onItemSelected(jugador: Jugador){
        Toast.makeText(this, jugador.Nom, Toast.LENGTH_SHORT).show()
    }
}