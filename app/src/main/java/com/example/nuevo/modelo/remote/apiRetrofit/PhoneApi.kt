package com.example.nuevo.modelo.remote.apiRetrofit

import com.example.nuevo.modelo.remote.frominternet.DetailsPhoneApiClass
import com.example.nuevo.modelo.remote.frominternet.PhoneApiClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PhoneApi {

    // es el que te agrega los links  products y el otro que dice details

    //listado de teléfonos
    @GET("products")
    suspend fun fetchPhoneList(): Response<List<PhoneApiClass>>

    //seleccionar uno con detalle
    @GET("details/{id}")
    suspend fun fechPhoneDetail(@Path("id") id: String):Response<DetailsPhoneApiClass>

}