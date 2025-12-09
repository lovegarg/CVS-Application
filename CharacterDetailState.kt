package com.example.cvsapplication.ui.state

import com.example.cvsapplication.data.model.ApiCharacter

/**
 * author lgarg on 12/9/25.
 */
sealed class CharacterDetailState {

    /** First state, before anything is loaded. */
    object Loading : CharacterDetailState()

    /** Successfully loaded full character detail. */
    data class Success(
        val character: ApiCharacter
    ) : CharacterDetailState()

    /** Error while loading detail (network, server, parsing, etc.). */
    data class Error(
        val message: String
    ) : CharacterDetailState()
}