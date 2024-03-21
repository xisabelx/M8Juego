package com.example.m8juego

import java.io.Serializable

data class Jugador(
    var Nom: String = "",
    var Puntuacio: String = "",
    var Imatge: String = "",
    var Data: String = "",
    var Edat: String = "",
    var Email: String = "",
    var Poblacio: String = ""
): Serializable {
}