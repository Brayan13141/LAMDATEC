package com.example.lamdatec.core.navigation

sealed class PantallasNav (val route: String){
    object LOGIN: PantallasNav("LOGIN")
    object SESION: PantallasNav("SESION")
    object PRINCIPAL: PantallasNav("PRINCIPAL")
    object SENSOR_MQ2: PantallasNav("SENSOR MQ2")
    object SENSOR_MQ7: PantallasNav("SENSOR MQ7")

}