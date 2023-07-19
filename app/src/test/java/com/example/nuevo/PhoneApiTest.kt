package com.example.nuevo

import com.example.nuevo.modelo.remote.apiRetrofit.PhoneApi
import com.example.nuevo.modelo.remote.frominternet.DetailsPhoneApiClass
import com.example.nuevo.modelo.remote.frominternet.PhoneApiClass
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhoneApiTest {
/*    private lateinit var mockWebServer: MockWebServer
    private lateinit var phoneApi: PhoneApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        phoneApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhoneApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    suspend fun testFetchPhoneList() {
        // Mock response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("[{\"id\":\"1\",\"name\":\"Phone 1\",\"price\":\"$100\",\"image\":\"image_url\"}]")
        mockWebServer.enqueue(mockResponse)

        // Make the API call
        val response = phoneApi.fetchPhoneList().execute()

        // Verify the response
        assert(response.isSuccessful)
        val phoneList = response.body()
        assert(phoneList != null && phoneList.isNotEmpty())
        assert(phoneList?.get(0)?.id == "1")
        assert(phoneList?.get(0)?.name == "Phone 1")
        assert(phoneList?.get(0)?.price == "$100")
        assert(phoneList?.get(0)?.image == "image_url")
    }

    @Test
    suspend fun testFetchPhoneDetail() {
        // Mock response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{\"id\":\"1\",\"name\":\"Phone 1\",\"price\":\"$100\",\"image\":\"image_url\",\"description\":\"Description\",\"lastPrice\":\"$90\",\"credit\":true}")
        mockWebServer.enqueue(mockResponse)

        // Make the API call
        val response = phoneApi.fechPhoneDetail("1").execute()
        //fetchPhoneDetail("1").execute()

        // Verify the response
        assert(response.isSuccessful)
        val phoneDetail = response.body()
        assert(phoneDetail != null)
        assert(phoneDetail?.id == "1")
        assert(phoneDetail?.name == "Phone 1")
        assert(phoneDetail?.price == "$100")
        assert(phoneDetail?.image == "image_url")
        assert(phoneDetail?.description == "Description")
        assert(phoneDetail?.lastPrice == "$90")
        assert(phoneDetail?.credit == true)
    }*/
}
