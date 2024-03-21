package com.example.m8juego

import android.content.Intent
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

                    var adapter = JugadorsAdapter(jugadorArrayList)

                    // Ordenar la lista de jugadores por puntuación en orden descendente
                    jugadorArrayList.sortByDescending { it.Puntuacio.toIntOrNull() ?: 0 }
                    jugadorRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : JugadorsAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            Toast.makeText(this@RecyclerView, "Has clicat a .$position", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@RecyclerView, DetalleJugador::class.java)
                            intent.putExtra("Nom", jugadorArrayList[position].Nom)
                            intent.putExtra("Puntuacio", jugadorArrayList[position].Puntuacio)
                            intent.putExtra("Imatge", jugadorArrayList[position].Imatge)
                            intent.putExtra("Data", jugadorArrayList[position].Data)
                            intent.putExtra("Edat", jugadorArrayList[position].Edat)
                            intent.putExtra("Email", jugadorArrayList[position].Email)
                            intent.putExtra("Poblacio", jugadorArrayList[position].Poblacio)
                            startActivity(intent) // Iniciar la actividad

                        }

                    })
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