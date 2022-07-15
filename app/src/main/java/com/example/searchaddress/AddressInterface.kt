package com.example.searchaddress

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AddressInterface {
    @GET("zipcodeV2/extendedSearch?")
    fun getAddress(
        @Query("searchKeyword") searchKeyword: String
    ): retrofit2.Call<AddressDetail>
}