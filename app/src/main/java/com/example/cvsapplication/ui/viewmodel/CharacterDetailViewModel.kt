package com.example.cvsapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvsapplication.data.api.RickMortyApiService
import com.example.cvsapplication.data.model.ApiCharacter
import com.example.cvsapplication.ui.state.CharacterDetailState
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
class CharacterDetailViewModel @Inject constructor(
    private val repository: RickMortyApiService
) : ViewModel() {

    /** Backing state for the UI. */
    private val _uiState = MutableStateFlow<CharacterDetailState>(
        CharacterDetailState.Loading
    )
    val uiState: StateFlow<CharacterDetailState> = _uiState.asStateFlow()

    /** Last requested character id (used for retry). */
    private var lastCharacterId: Int? = null

    /**
     * Public API called from CharacterDetailScreen.
     * Triggers loading of the given character.
     */
    fun loadCharacterDetail(characterId: Int) {
        // Remember last id for retry()
        lastCharacterId = characterId

        viewModelScope.launch {
            _uiState.value = CharacterDetailState.Loading

            try {
                // Repository returns a domain model CharacterDetail
                val detail: ApiCharacter = repository.getCharacterDetailsById(characterId)

                _uiState.value = CharacterDetailState.Success(
                    character = detail
                )
            } catch (e: Exception) {
                _uiState.value = CharacterDetailState.Error(
                    message = e.message ?: "Something went wrong while loading character details."
                )
            }
        }
    }

    /**
     * Retry last failed load, if an id is known.
     */
    fun retry() {
        val id = lastCharacterId ?: return
        loadCharacterDetail(id)
    }
}