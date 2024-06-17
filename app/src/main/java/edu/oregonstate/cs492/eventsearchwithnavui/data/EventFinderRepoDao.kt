package edu.oregonstate.cs492.eventsearchwithnavui.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventFinderRepoDao {
    @Insert
    suspend fun insert(repo: EventFinderListJson)

    @Delete
    suspend fun delete(repo: EventFinderListJson)

    @Query("SELECT * FROM EventFinderListJson")
    fun getAllRepos() : Flow<List<EventFinderListJson>>

    @Query("SELECT * FROM EventFinderListJson WHERE name = :name LIMIT 1")
    fun getRepoByName(name: String) : Flow<EventFinderListJson?>
}