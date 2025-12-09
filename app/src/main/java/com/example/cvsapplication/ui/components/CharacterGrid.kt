package com.example.cvsapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cvsapplication.data.model.ApiCharacter

/**
 * author lgarg on 12/9/25.
 */
@Composable
fun CharacterGrid(
    characters: List<ApiCharacter>,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        // Fixed 2 columns on all screen sizes
        columns = GridCells.Fixed(2),
        modifier = modifier,
        // 12dp horizontal spacing between cards
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        // 12dp vertical spacing between rows
        verticalArrangement = Arrangement.spacedBy(12.dp),
        // Content padding from screen edges
        contentPadding = PaddingValues(12.dp)
    ) {
        // Render card for each character
        items(
            items = characters,
            // Stable key prevents recomposition issues during scroll
            key = { it.id }
        ) { character ->
            CharacterCard(
                character = character,
                onClick = { characterId ->
                    // Notify parent of character selection
                    onCharacterClick(characterId)
                }
            )
        }
    }
}