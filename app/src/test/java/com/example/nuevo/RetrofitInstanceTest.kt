package com.example.nuevo

import com.example.nuevo.modelo.remote.apiRetrofit.RetrofitClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstanceTest { //test unitarios

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp(){
        mockWebServer= MockWebServer()
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun testRetrofit(){
        // arrange
        val expectedBaseUrl = mockWebServer.url("https://my-json-server.typicode.com/Himuravidal/FakeAPIdata/").toString()
        val mockRetrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("https://my-json-server.typicode.com/Himuravidal/FakeAPIdata/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // act
        val retrofitInstance = RetrofitClient.retrofitInstance()


        // Assert
        Assert.assertEquals(expectedBaseUrl, RetrofitClient.retrofitInstance()).toString()


    }
}

// Arrange
//val expectedBaseUrl = "https://my-json-server.typicode.com/Himuravidal/FakeAPIdata/"

// Act
//val actualBaseUrl = RetrofitClient.retrofitInstance().baseUrl().toString()

// Assert
//Assert.assertEquals(expectedBaseUrl, actualBaseUrl)