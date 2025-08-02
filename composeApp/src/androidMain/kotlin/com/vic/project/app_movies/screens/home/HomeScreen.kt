package com.vic.project.app_movies.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vic.project.app_movies.R
import com.vic.project.app_movies.design_system.ImageLoad
import com.vic.project.app_movies.design_system.InputText
import com.vic.project.app_movies.domain.model.Movie
import com.vic.project.app_movies.presentation.viewmodel.HomeViewModel
import com.vic.project.app_movies.utils.ModifierExtension.clickOutSideToHideKeyBoard
import com.vic.project.app_movies.utils.ModifierExtension.clickableSingle
import com.vic.project.app_movies.utils.StringUtils.orNullWithHolder
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.GlobalContext.get

@Composable
fun HomeScreen(
    navigateToDetails: (Int) -> Unit
) {
    val homeViewModel: HomeViewModel = koinViewModel()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickOutSideToHideKeyBoard()
            .padding(horizontal = 16.dp),
    ) {
        Column (
            modifier = Modifier
                .background(Color.Black)
                .statusBarsPadding()
        ){
            Spacer(Modifier.height(16.dp))
            InputText(
                string = uiState.search,
                textHint = "Search movie",
                imgTrailing = R.drawable.ic_search,
                imeAction = ImeAction.Search,
                onSearch = {
                    if (uiState.search.isNotBlank().not()) {
                        homeViewModel.retry()
                    }
                },
                onValueChange = {
                    homeViewModel.updateInputSearch(it)
                }
            )
        }


        Text(
            text = if (uiState.search.isNotBlank()) "Search results" else "Trending movies",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.W600,
            color = Color.White,
            modifier = Modifier.padding(vertical = 14.dp)
        )

        AnimatedContent(uiState.listData.isNotEmpty()) { objectsAvailable ->
            if (objectsAvailable) {
                ObjectGrid(
                    objects = uiState.listData,
                    onObjectClick = navigateToDetails,
                )
            } else {
                when {
                    uiState.isError -> ErrorPage { homeViewModel.retry() }
                    else -> SearchNewsNotFound(
                        isLoading = uiState.isLoading,
                        isNotFound = uiState.search.isNotBlank()
                    )
                }
            }
        }
    }
}

@Composable
private fun ObjectGrid(
    objects: List<Movie>,
    onObjectClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(objects, key = { it.id }) { obj ->
            ObjectFrame(
                obj = obj,
                onClick = { onObjectClick(obj.id) },
            )
        }
    }
}

@Composable
private fun ObjectFrame(
    obj: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .height(240.dp)
            .clickableSingle { onClick() }
            .border(1.dp, Color.White.copy(0.8f), RoundedCornerShape(12.dp))
    ) {
        ImageLoad(
            url = obj.posterUrl.orEmpty(),
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(0.8f),RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                .padding(10.dp)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                obj.title.orNullWithHolder(),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text("(${obj.rating.orNullWithHolder()}/${obj.vote.orNullWithHolder()})",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                )
                Text(obj.releaseDate.orNullWithHolder(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                )
            }
        }

        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(0.5f),CircleShape)
                .align(Alignment.Center)
                .padding(6.dp)
        )
    }
}