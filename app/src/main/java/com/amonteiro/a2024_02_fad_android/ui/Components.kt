package com.amonteiro.a2024_02_fad_android.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amonteiro.a2024_02_fad_android.ui.theme.A2024_02_fad_androidTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyErrorPreview() {
    A2024_02_fad_androidTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                Text(text = "-----Coucou---")
                MyError("Coucou")
                Text(text = "-----   ----")
                MyError("  ")
                Text(text = "----- null ---")
                MyError(null)
                Text(text = "-----")
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable //id du PictureBean à afficher
fun MyError(errorMessage: String?, modifier: Modifier = Modifier) {

    AnimatedVisibility(visible = !errorMessage.isNullOrBlank()) {
        Text(
            text = errorMessage ?: "",
            color = MaterialTheme.colorScheme.onError,
            modifier = modifier
                .background(MaterialTheme.colorScheme.error)
                .padding(4.dp)
                .fillMaxWidth()
        )
    }
}
