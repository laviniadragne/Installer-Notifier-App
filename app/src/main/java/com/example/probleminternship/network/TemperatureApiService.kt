package com.example.probleminternship.network

import com.example.probleminternship.utils.GET_POINT
import com.example.probleminternship.utils.URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = URL
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface TemperatureApiService {
    @GET(GET_POINT)
    suspend fun getTemperature(): Map<String, Any>
}

// Call to create() function on a Retrofit object is expensive
// and the app needs only one instance of Retrofit API service
object TemperatureApi {
    val retrofitService : TemperatureApiService by lazy {
        retrofit.create(TemperatureApiService::class.java)
    }
}