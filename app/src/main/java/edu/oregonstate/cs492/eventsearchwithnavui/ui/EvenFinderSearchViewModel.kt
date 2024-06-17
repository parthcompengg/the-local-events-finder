package edu.oregonstate.cs492.eventsearchwithnavui.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.oregonstate.cs492.eventsearchwithnavui.data.EventFinderListJson
import edu.oregonstate.cs492.eventsearchwithnavui.data.EventFinderReposRepository
import edu.oregonstate.cs492.eventsearchwithnavui.data.EventFinderRepo
import edu.oregonstate.cs492.eventsearchwithnavui.data.EventFinderService
import edu.oregonstate.cs492.eventsearchwithnavui.util.LoadingStatus
import kotlinx.coroutines.launch

class EvenFinderSearchViewModel : ViewModel() {
    private val repository = EventFinderReposRepository(EventFinderService.create())

    private val _searchResults = MutableLiveData<List<EventFinderListJson>?>(null)
    val searchResults: LiveData<List<EventFinderListJson>?> = _searchResults

    private val _loadingStatus = MutableLiveData<LoadingStatus>(LoadingStatus.SUCCESS)
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun loadSearchResults(query: String, postalCode: String, classificationName: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            val result = repository.loadRepositoriesSearch(query, postalCode,classificationName)
            _searchResults.value = result.getOrNull()
            _error.value = result.exceptionOrNull()?.message
            _loadingStatus.value = when (result.isSuccess) {
                true -> LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
        }
    }

    fun loadeventSearchResults(postalCode: String, classificationName: String?) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            val result = repository.loadeventRepositoriesSearch(postalCode, classificationName)
            _searchResults.value = result.getOrNull()
            _error.value = result.exceptionOrNull()?.message
            _loadingStatus.value = when (result.isSuccess) {
                true -> LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
        }
    }
}