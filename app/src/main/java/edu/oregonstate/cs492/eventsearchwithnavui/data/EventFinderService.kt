package edu.oregonstate.cs492.eventsearchwithnavui.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface EventFinderService {
    @GET("events")
    suspend fun searchRepositories(
        @Query("keyword") query: String,
        @Query("classificationName") classificationName: String,
        @Query("postalCode") postCode: String,
        @Query("apikey") apikey: String? = "DU8u0wIzrAqF1q0sEPV0oQGmopbNqTSU"

    ): Response<EventsRepo> //EventRepo
    @GET("events")
    suspend fun searchEventsbyPostcodeCategory(
        @Query("postalCode") postCode: String,
        @Query("classificationName") classificationName: String,
        @Query("apikey") apikey: String? = "DU8u0wIzrAqF1q0sEPV0oQGmopbNqTSU"
    ): Response<EventsRepo> //EventRepo
    companion object {
        private const val BASE_URL = "https://app.ticketmaster.com/discovery/v2/"

        fun create(): EventFinderService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(EventFinderService::class.java)
        }
    }
}