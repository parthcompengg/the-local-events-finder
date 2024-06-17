package edu.oregonstate.cs492.eventsearchwithnavui.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import edu.oregonstate.cs492.eventsearchwithnavui.data.AppDatabase
import edu.oregonstate.cs492.eventsearchwithnavui.data.BookmarkedReposRepository
import edu.oregonstate.cs492.eventsearchwithnavui.data.EventFinderListJson
import edu.oregonstate.cs492.eventsearchwithnavui.data.EventFinderRepo
import kotlinx.coroutines.launch

class BookmarkedReposViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BookmarkedReposRepository(
        AppDatabase.getInstance(application).gitHubRepoDao()
    )

    val bookmarkedRepos = repository.getAllBookmarkedRepos().asLiveData()

    fun addBookmarkedRepo(repo: EventFinderListJson) {
        viewModelScope.launch {
            repository.insertBookmarkedRepo(repo)
        }
    }

    fun removeBookmarkedRepo(repo: EventFinderListJson) {
        viewModelScope.launch {
            repository.deleteBookmarkedRepo(repo)
        }
    }

    fun getBookmarkedRepoByName(name: String) =
        repository.getBookmarkedRepoByName(name).asLiveData()
}