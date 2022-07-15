package com.example.searchaddress

data class Paging(
    val blockSize: Int,
    val current: Int,
    val rowsPerPage: Int,
    val totalCount: Int
)