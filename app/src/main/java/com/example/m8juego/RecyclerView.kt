package com.example.m8juego

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class RecyclerView : AppCompatActivity() {

    /*val jugadors = listOf<Jugador>(
        Jugador("Pepe","12","https://www.kasandbox.org/programming-images/avatars/piceratops-tree.png"),
        Jugador("Juan","20","https://www.kasandbox.org/programming-images/avatars/leafers-seed.png"),
        Jugador("Luis","15","https://www.kasandbox.org/programming-images/avatars/leaf-yellow.png"),
        Jugador("Jorge","32","https://www.kasandbox.org/programming-images/avatars/leaf-blue.png"),
    )*/
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
                    for (jugadorSnapshot in snapshot.children) {
                        val nom = jugadorSnapshot.child("Nom").getValue(String::class.java)
                        val puntuacio =
                            jugadorSnapshot.child("Puntuacio").getValue(String::class.java)
                        val imatgeUrl = jugadorSnapshot.child("Imatge")
                            .getValue(String::class.java) // URL de la imagen en Firebase Storage


                        if (!imatgeUrl.isNullOrEmpty()) {
                            // Crear un objeto Jugador con los datos recuperados
                            val jugador = Jugador(nom ?: "", puntuacio ?: "", imatgeUrl ?: "")
                            jugadorArrayList.add(jugador)

                            // Notificar al adaptador que los datos han cambiado
                            jugadorRecyclerView.adapter?.notifyDataSetChanged()

                            // Descargar la imagen de Firebase Storage y actualizar el ImageView cuando la descarga sea exitosa
                            val storageRef = FirebaseStorage.getInstance("gs://m8juego-e9538.appspot.com").reference
                            val imageRef = storageRef.child(imatgeUrl ?: "")
                            val ONE_MEGABYTE: Long = 1024 * 1024
                            imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { imageData ->
                                // Convertir los datos de la imagen en un Bitmap y establecerlo en el ImageView
                                val bitmap =
                                    BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                                // Actualizar la imagen en el RecyclerView
                                jugador.imatgeBitmap = bitmap
                                jugadorRecyclerView.adapter?.notifyDataSetChanged()
                            }.addOnFailureListener {
                                // Manejar cualquier error de descarga de imagen
                            }
                        }
                    }
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