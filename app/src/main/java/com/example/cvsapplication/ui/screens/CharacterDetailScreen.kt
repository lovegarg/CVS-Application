package com.example.cvsapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cvsapplication.ui.components.DetailRow
import com.example.cvsapplication.ui.components.ErrorStateView
import com.example.cvsapplication.ui.components.LoadingIndicator
import com.example.cvsapplication.ui.components.StatusBadge
import com.example.cvsapplication.ui.state.CharacterDetailState
import com.example.cvsapplication.ui.viewmodel.CharacterDetailViewModel

/**
 * author lgarg on 12/9/25.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    viewModel: CharacterDetailViewModel,
    characterId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(characterId) {
        // Load character from API
        // ViewModel will update uiState based on result
        viewModel.loadCharacterDetail(characterId)
    }

    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        // Top bar with title and back button
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Character Details")
                },
                navigationIcon = {
                    // Back button
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to list"
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        when (uiState) {
            // STATE 1: LOADING
            // Waiting for API response
            is CharacterDetailState.Loading -> {
                LoadingIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            // STATE 2: SUCCESS
            // Have character data, display it
            is CharacterDetailState.Success -> {
                // Extract character data from state
                val character = uiState.character

                // Scrollable column for character details
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        // Enable vertical scrolling
                        // In case content is taller than screen
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    AsyncImage(
                        model = character.image,
                        contentDescription = character.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        // Crop image to fit dimensions
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    StatusBadge(status = character.status)

                    Spacer(modifier = Modifier.height(24.dp))
                    DetailRow(label = "Species", value = character.species)
                    DetailRow(
                        label = "Type",
                        value = character.type ?: "Unknown"
                    )
                    DetailRow(label = "Origin", value = character.origin.name)
                    DetailRow(label = "Location", value = character.location.name)
                    DetailRow(
                        label = "Episodes",
                        value = character.episode.toString()
                    )
                    DetailRow(label = "Created", value = character.created)
                }
            }

            // STATE 3: ERROR
            // Failed to fetch character
            is CharacterDetailState.Error -> {
                ErrorStateView(
                    errorMessage = uiState.message,
                    onRetry = {
                        // Retry button callback
                        // ViewModel will re-fetch character
                        viewModel.retry()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
        }
    }
}