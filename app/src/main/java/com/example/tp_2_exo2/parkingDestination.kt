package com.example.tp_2_exo2

sealed class ParkingDestination(val route:String) {
    object ParkingsList:ParkingDestination("parkingsList")
    object ParkingDetails:ParkingDestination("parkingDetails/{ParkingId}"){
        fun createRoute(id:Int) = "parkingDetails/$id"
    }
}