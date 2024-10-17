package com.example.lamdatec.Interfaz.Nav

sealed class PantallasNav (val route: String){
    object LOGIN: PantallasNav("LOGIN")
    object SESION: PantallasNav("SESION")
    object PRINCIPAL: PantallasNav("PRINCIPAL")
    object S1: PantallasNav("S1")

}