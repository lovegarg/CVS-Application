package com.example.cvsapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvsapplication.data.api.RickMortyApiService
import com.example.cvsapplication.data.model.SearchDebouncer
import com.example.cvsapplication.data.model.SearchFilters
import com.example.cvsapplication.ui.state.CharacterListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * author lgarg on 12/9/25.
 */
@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: RickMortyApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterListState>(CharacterListState.Idle)
    val uiState: StateFlow<CharacterListState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filters = MutableStateFlow(SearchFilters())
    val filters: StateFlow<SearchFilters> = _filters.asStateFlow()

    private val searchDebouncer = SearchDebouncer(delayMs = 500L)// avoid firing API on every keystroke

    init {
        resetSearch()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query

        if (query.isBlank()) {
            _uiState.value = CharacterListState.Idle
            return
        }

        searchDebouncer.debounce(viewModelScope) {
            performSearch(query, _filters.value)
        }
    }

    fun updateFilters(newFilters: SearchFilters) {
        _filters.value = newFilters
        val currentQuery = _searchQuery.value
        if (currentQuery.isNotBlank()) {
            performSearch(currentQuery, newFilters)
        }
    }

    fun retrySearch() {
        val currentQuery = _searchQuery.value
        if (currentQuery.isNotBlank()) {
            performSearch(currentQuery, _filters.value)
        }
    }

    /**
     * Clears search query and resets to initial state.
     */
    fun resetSearch() {
        searchDebouncer.clear()
        _uiState.value = CharacterListState.Idle
    }

    /**
     * Core search logic - called after debouncing or filter changes.
     */
    private fun performSearch(query: String?, filters: SearchFilters) {
        if (query?.isEmpty() == true) {
            return
        }
        val nonEmptyQuery = query ?: ""
        viewModelScope.launch {
            // Show loading immediately
            _uiState.value = CharacterListState.Loading

            try {
                // Call repository (handles API, error mapping, etc.)
                val characters = repository.searchCharacters(
                    name = nonEmptyQuery
                )

                // Update state based on results
                if (characters.info.count > 0 && characters.results.isEmpty()) {
                    _uiState.value = CharacterListState.Empty(nonEmptyQuery)
                } else {
                    _uiState.value = CharacterListState.Success(characters.results)
                }
            } catch (e: Exception) {
                // Map exception to user-friendly error
                _uiState.value = CharacterListState.Error(
                    message = e.message ?: "Unknown error occurred",
                    searchQuery = query
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchDebouncer.clear()
    }
}