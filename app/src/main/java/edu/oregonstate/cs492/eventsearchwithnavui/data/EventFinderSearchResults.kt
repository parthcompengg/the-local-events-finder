package edu.oregonstate.cs492.eventsearchwithnavui.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventFinderSearchResults(
    val items: List<EventFinderRepo>
)
