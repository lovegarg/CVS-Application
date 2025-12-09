package com.example.cvsapplication.ui.state

import com.example.cvsapplication.data.model.ApiCharacter

/**
 * author lgarg on 12/9/25.
 */
sealed class CharacterListState {

    object Idle : CharacterListState()

    object Loading : CharacterListState()

    data class Success(
        val characters: List<ApiCharacter>,
        val hasMorePages: Boolean = false
    ) : CharacterListState()

    data class Empty(
        val searchQuery: String
    ) : CharacterListState()

    data class Error(
        val message: String,
        val searchQuery: String? = null
    ) : CharacterListState()
}