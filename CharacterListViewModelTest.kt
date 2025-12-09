package com.example.cvsapplication.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cvsapplication.MainCoroutineRule
import com.example.cvsapplication.data.api.RickMortyApiService
import com.example.cvsapplication.data.model.ApiCharacter
import com.example.cvsapplication.data.model.ApiCharacterListResponse
import com.example.cvsapplication.data.model.ApiLocation
import com.example.cvsapplication.data.model.ApiOrigin
import com.example.cvsapplication.data.model.ApiPageInfo
import com.example.cvsapplication.ui.state.CharacterListState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

/**
 * author lgarg on 12/9/25.
 */
@ExperimentalCoroutinesApi
class CharacterListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val repository: RickMortyApiService = mockk()

    @Test
    fun `search success emits Loading then Success`() = runTest(mainCoroutineRule.dispatcher) {
        // Arrange
        val characters = listOf(
            ApiCharacter(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                origin = ApiOrigin("Earth", "url"),
                location = ApiLocation("Earth", "url"),
                image = "url",
                episode = emptyList(),
                url = "url",
                created = "2017-11-04T18:48:46.250Z"
            )
        )
        coEvery {
            repository.searchCharacters(
                name = "rick",
                status = null,
                species = null
            )
        } returns ApiCharacterListResponse(info = ApiPageInfo(count = 1, pages = 1, next = null, prev = null), results= characters)

        val viewModel = CharacterListViewModel(repository)

        // Act
        viewModel.onSearchQueryChanged("rick")
        advanceUntilIdle() // let debounce + search complete

        // Assert
        val state = viewModel.uiState.value
        assertEquals(CharacterListState.Success(characters), state)
    }

    @Test
    fun `search error emits Error state`() = runTest(mainCoroutineRule.dispatcher) {
        // Arrange
        coEvery { repository.searchCharacters(name = "rick", status = null, species = null) } throws RuntimeException("Network error")

        val viewModel = CharacterListViewModel(repository)

        // Act
        viewModel.onSearchQueryChanged("rick")
        advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        require(state is CharacterListState.Error)
        assertEquals("Network error", state.message)
        assertEquals("rick", state.searchQuery)
    }
}