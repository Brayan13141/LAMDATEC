package com.example.lamdatec.core.navigation

sealed class PantallasNav (val route: String){
    object LOGIN: PantallasNav("LOGIN")
    object SESION: PantallasNav("SESION")
    object PRINCIPAL: PantallasNav("PRINCIPAL")
    object S1: PantallasNav("S1")

}