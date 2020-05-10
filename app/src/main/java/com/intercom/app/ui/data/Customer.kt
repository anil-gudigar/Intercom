package com.intercom.app.ui.data

data class Customer(
    val latitude: String,
    val longitude: String,
    val name: String,
    val user_id: Int
){
    override fun toString(): String {
        return "Customer(latitude='$latitude', longitude='$longitude', name='$name', user_id=$user_id)"
    }
}