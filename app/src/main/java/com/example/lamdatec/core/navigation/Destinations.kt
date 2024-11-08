package com.example.lamdatec.core.navigation

sealed class Destinations (val route: String){
    data object Login: Destinations("login")
    //data object SESION: Destinations("SESION")
    data object Principal: Destinations("main")
    data object SensorMQ2: Destinations("mq2")
    data object SensorMQ7: Destinations("mq7")

}