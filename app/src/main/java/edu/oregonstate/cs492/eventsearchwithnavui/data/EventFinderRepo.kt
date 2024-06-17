package edu.oregonstate.cs492.eventsearchwithnavui.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity
@JsonClass(generateAdapter = true)
data class EventFinderRepo(
    @PrimaryKey
    @Json(name = "name")
    val name: String,

    val description: String?,
    @Json(name = "url") val url: String,
    @Json(name = "id") val stars: String
) : Serializable