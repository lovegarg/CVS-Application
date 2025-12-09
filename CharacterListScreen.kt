package com.example.cvsapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cvsapplication.ui.components.CharacterGrid
import com.example.cvsapplication.ui.components.EmptyStateView
import com.example.cvsapplication.ui.components.ErrorStateView
import com.example.cvsapplication.ui.components.LoadingIndicator
import com.example.cvsapplication.ui.components.SearchBar
import com.example.cvsapplication.ui.state.CharacterListState
import com.example.cvsapplication.ui.viewmodel.CharacterListViewModel

/**
 * author lgarg on 12/9/25.
 */
@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel,
    onCharacterClick: (Int) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState().value
    val searchQuery = viewModel.searchQuery.collectAsState().value

    Scaffold(
        // TopAppBar with title and SearchBar
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    // Blue background for top bar
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(top = 35.dp, start = 12.dp, end = 12.dp, bottom = 12.dp)
            ) {
                // Title text
                Text(
                    text = "Rick & Morty Characters",
                    style = MaterialTheme.typography.headlineSmall,
                    // White text on blue background
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // SearchBar component
                // - Accepts text input
                // - Shows clear button when text present
                // - Calls onQueryChanged when user types
                SearchBar(
                    query = searchQuery,
                    onQueryChanged = { newQuery -> viewModel.onSearchQueryChanged(newQuery) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        // Content area inside Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize()
                // Apply Scaffold padding (respects TopAppBar)
                .padding(paddingValues)
                // Additional internal padding
                .padding(12.dp)
        ) {
            when (uiState) {
                // STATE 1: IDLE
                // User hasn't searched yet, show welcome message
                is CharacterListState.Idle -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        // Center content vertically
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Search for a character",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = "Type a name like 'rick' or 'morty'",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }

                // STATE 2: LOADING
                // Show loading indicator while fetching data
                is CharacterListState.Loading -> {
                    LoadingIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 64.dp)
                    )
                }

                // STATE 3: SUCCESS
                // Got results, render character grid
                is CharacterListState.Success -> {
                    CharacterGrid(
                        characters = uiState.characters,
                        onCharacterClick = onCharacterClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // STATE 4: EMPTY
                // Search succeeded but no results found
                is CharacterListState.Empty -> {
                    EmptyStateView(
                        message = "No characters found for \"${uiState.searchQuery}\".\nTry a different search.",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // STATE 5: ERROR
                // Network error or API error occurred
                is CharacterListState.Error -> {
                    ErrorStateView(
                        errorMessage = uiState.message,
                        onRetry = {
                            viewModel.retrySearch()
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}