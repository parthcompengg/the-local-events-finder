package edu.oregonstate.cs492.eventsearchwithnavui.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable


@JsonClass(generateAdapter = true)
data class EventsRepo(
    @Json(name = "_embedded")
    val embedded: EmbeddedEvents
) : Serializable

@JsonClass(generateAdapter = true)
data class EmbeddedEvents(
    @Json(name = "events")
    val events: List<EventFinderListJson>
)

@Entity
@JsonClass(generateAdapter = true)
@TypeConverters(EmbeddedTypeConverter::class, DatesTypeConverter::class, ImageTypeConverter::class)
data class EventFinderListJson(
    @PrimaryKey
    val id: String,
    val name: String,
    val url: String,
    val type: String,
    @Json(name = "_embedded")
    val embedded: Embedded,
    val dates: Dates,
    val images: List<Image>
) : Serializable

@JsonClass(generateAdapter = true)
data class Embedded(
    @Json(name = "venues")
    val venues: List<Venues>
) : Serializable

@JsonClass(generateAdapter = true)
data class Venues(
    @PrimaryKey
    val id: String,
    val name: String,
    @Json(name = "city")
    val city: City,
    @Json(name = "state")
    val state: State,
    @Json(name = "address")
    val address: Address
) : Serializable

@JsonClass(generateAdapter = true)
data class City(
    val name: String
) : Serializable

@JsonClass(generateAdapter = true)
data class State(
    val name: String
) : Serializable

@JsonClass(generateAdapter = true)
data class Address(
    val line1: String
) : Serializable

@JsonClass(generateAdapter = true)
data class Dates(
    val start: Start
    //val timezone: String,
    //val status: Status
) : Serializable

@JsonClass(generateAdapter = true)
data class Start(
    val localDate: String,
    val dateTBD: Boolean,
    val dateTBA: Boolean,
    val timeTBA: Boolean,
    val noSpecificTime: Boolean
) : Serializable

@JsonClass(generateAdapter = true)
data class Status(
    val code: String
) : Serializable

@JsonClass(generateAdapter = true)
data class Image(
    val ratio: String,
    val url: String,
    val width: Int,
    val height: Int,
    val fallback: Boolean
) : Serializable


class EmbeddedTypeConverter {
    @TypeConverter
    fun fromJson(json: String): Embedded? {
        val type = object : TypeToken<Embedded>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(embedded: Embedded): String {
        return Gson().toJson(embedded)
    }
}

class DatesTypeConverter {
    @TypeConverter
    fun fromJson(json: String): Dates? {
        val type = object : TypeToken<Dates>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(dates: Dates): String {
        return Gson().toJson(dates)
    }
}

class ImageTypeConverter {
    @TypeConverter
    fun fromJson(json: String): List<Image>? {
        val type = object : TypeToken<List<Image>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(images: List<Image>): String {
        return Gson().toJson(images)
    }
}