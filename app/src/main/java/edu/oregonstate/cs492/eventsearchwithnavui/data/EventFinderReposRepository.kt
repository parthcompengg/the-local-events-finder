package edu.oregonstate.cs492.eventsearchwithnavui.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventFinderReposRepository(
    private val service: EventFinderService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadRepositoriesSearch(query: String, postalCode: String,classificationName: String): Result<List<EventFinderListJson>> =
        withContext(ioDispatcher) {
            try {
                val response = service.searchRepositories(query, postalCode, classificationName)

                if (response.isSuccessful) {
                    Result.success(response.body()?.embedded?.events?: listOf())
                } else {
                    Result.failure(Exception(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun loadeventRepositoriesSearch(postalCode: String, classificationName: String?): Result<List<EventFinderListJson>> =
        withContext(ioDispatcher) {
            try {
                val response = service.searchEventsbyPostcodeCategory(postalCode, classificationName!!)

                if (response.isSuccessful) {
                    Result.success(response.body()?.embedded?.events?: listOf())
                } else {
                    Result.failure(Exception(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}