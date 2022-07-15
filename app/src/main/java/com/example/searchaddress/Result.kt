package com.example.searchaddress

data class Result(
    val regionalTotalZipcodeSearchCount: Int,
    val regionalZipcodeStatusList: List<RegionalZipcodeStatus>,
    val totalZipcodeSearchCount: Int,
    val zipcodes: List<Zipcode>
)