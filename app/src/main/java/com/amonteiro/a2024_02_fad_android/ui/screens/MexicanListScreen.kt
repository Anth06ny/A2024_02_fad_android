package com.amonteiro.a2024_02_fad_android.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.amonteiro.a2024_02_fad_android.R
import com.amonteiro.a2024_02_fad_android.model.MexicanFoodBean
import com.amonteiro.a2024_02_fad_android.ui.MyError
import com.amonteiro.a2024_02_fad_android.ui.Routes
import com.amonteiro.a2024_02_fad_android.ui.theme.A2024_02_fad_androidTheme
import com.amonteiro.a2024_02_fad_android.viewmodel.MexicanViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MexicanListPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    A2024_02_fad_androidTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//            //Jeu de donnée pour la Preview
            val mexicanViewModel: MexicanViewModel = viewModel()
            mexicanViewModel.errorMessage.value = "Coucou"
            mexicanViewModel.loadFakeData()
//            mainViewModel.searchText.value = "BC"
//            mainViewModel.errorMessage.value = "Un message d'erreur"
//            mainViewModel.runInProgress.value = true

            MexicanListScreen(mexicanViewModel = mexicanViewModel) //mainViewModel = mainViewModel)
        }
    }
}

@Composable
fun MexicanListScreen(
    navHostController: NavHostController? = null,
    mexicanViewModel: MexicanViewModel
) {

    //Action à l'arrivée sur l'écran
    LaunchedEffect(key1 = "") {
        mexicanViewModel.loadData()
    }

    Column {

        Text(
            text = "Nourriture Mexicaine : ",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )

        //Uniquement pour rediriger sur le reste de lk'application
        Text(
            text = "(SearchScreen)",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navHostController?.navigate(Routes.SearchScreen.route)
                },
        )

        MyError(errorMessage = mexicanViewModel.errorMessage.value)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(mexicanViewModel.list.size) {
                FoodItem(mexicanFoodBean = mexicanViewModel.list[it])
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FoodItem(modifier: Modifier = Modifier, mexicanFoodBean: MexicanFoodBean) {

    Row(modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {

//Permission Internet nécessaire
        GlideImage(
            model = mexicanFoodBean.image,
            //Dans string.xml
            //contentDescription = getString(R.string.picture_of_cat),
            //En dur
            contentDescription = "une photo de chat",
            // Image d'attente. Permet également de voir l'emplacement de l'image dans la Preview
            loading = placeholder(R.mipmap.ic_launcher_round),
            // Image d'échec de chargement
            failure = placeholder(R.mipmap.ic_launcher),
            contentScale = ContentScale.Fit,
            //même autres champs qu'une Image classique
            modifier = Modifier
                .heightIn(max = 100.dp) //Sans hauteur il prendra tous l'écran
                .widthIn(max = 100.dp)
        )

        Column(modifier = Modifier.padding(5.dp)) {
            Text(
                text = mexicanFoodBean.title,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = "Difficulty : ${mexicanFoodBean.difficulty}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
            )
        }
    }

}
