package com.example.searchaddress

data class Zipcode(
    val addressByNumberOfLot: String,
    val addressByRoadName: String,
    val baseAddress: String,
    val isRoadNameAddress: Boolean,
    val massiveAddress: String,
    val roadZipcode: String,
    val zipcode: String
)