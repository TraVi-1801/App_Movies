package com.vic.project.app_movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vic.project.app_movies.utils.isOnline
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            enableEdgeToEdge()

            val snackbarHostState = remember { SnackbarHostState() }
            val isOffline = isOnline(this).map(Boolean::not).collectAsStateWithLifecycle(
                initialValue = false,
            )

            val notConnectedMessage = "⚠️ You aren’t connected to the internet"
            LaunchedEffect(isOffline.value) {
                if (isOffline.value){
                    snackbarHostState.showSnackbar(
                        message = notConnectedMessage,
                        duration = Indefinite,
                    )
                }
            }

            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
                content = { padding -> // We have to pass the scaffold inner padding to our content. That's why we use Box.
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                            .padding(PaddingValues(bottom = padding.calculateBottomPadding()))
                    ) {
                        App()
                    }
                },
            )

        }
    }
}