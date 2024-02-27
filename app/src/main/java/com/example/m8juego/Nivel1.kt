package com.example.m8juego

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.*

class Nivel1 : AppCompatActivity() {

    private val COLUMNAS = 3
    private val DIMENSIONES = COLUMNAS * COLUMNAS

    private lateinit var graella: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nivel1)
        inicializar()
        mezclar()
        mostrar()
    }
    private fun mostrar() {
        val buttons = listOf(
            findViewById<Button>(R.id.button),
            findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4),
            findViewById<Button>(R.id.button5),
            findViewById<Button>(R.id.button6),
            findViewById<Button>(R.id.button7),
            findViewById<Button>(R.id.button8),
            findViewById<Button>(R.id.button9)
        )

        for (i in graella.indices) {
            val button = buttons[i]

            // Configuración del fondo del botón según el valor en graella[i]
            when (graella[i]) {
                "0" -> button.setBackgroundResource(R.drawable.gatouno)
                "1" -> button.setBackgroundResource(R.drawable.gatodos)
                "2" -> button.setBackgroundResource(R.drawable.gatotres)
                "3" -> button.setBackgroundResource(R.drawable.gatocuatro)
                "4" -> button.setBackgroundResource(R.drawable.gatocinco)
                "5" -> button.setBackgroundResource(R.drawable.gatoseis)
                "6" -> button.setBackgroundResource(R.drawable.gatosiete)
                "7" -> button.setBackgroundResource(R.drawable.gatoocho)
                "8" -> button.setBackgroundResource(R.drawable.gatonueve)
            }

        }

    }

    private fun mezclar() {
        val random = Random()
        var index: Int
        var temp: String
        for (i in graella.size - 1 downTo 0) {
            //hacemos un intercambio para mezclar las piezas, desordenando los números de la graella
            //como en el ejercicio de cifrado de M9
            index = random.nextInt(i + 1)
            temp = graella[index]
            graella[index] = graella[i]
            graella[i] = temp
        }
    }

    private fun inicializar() {
        //inicializamos la graella con numeros del 1 al 8 en este caso, en String
        graella = Array(DIMENSIONES) { i -> i.toString() }
    }

    //private boolean fun estaResuelto(){
      //  var resuelto = false
        //for (i in graella.indices) {
          //  if (graella[i] == "i") {
            //    resuelto = true
            //} else {
              //  resuelto = false
                //break
            //}
        //}

        //return resuelto;
    //}

}
