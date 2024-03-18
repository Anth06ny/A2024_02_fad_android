package com.amonteiro.a2024_02_fad_android.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.amonteiro.a2024_02_fad_android.R
import com.amonteiro.a2024_02_fad_android.model.PictureBean
import com.amonteiro.a2024_02_fad_android.model.pictureList
import com.amonteiro.a2024_02_fad_android.ui.Routes
import com.amonteiro.a2024_02_fad_android.ui.theme.A2024_02_fad_androidTheme
import com.amonteiro.a2024_02_fad_android.viewmodel.MainViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    A2024_02_fad_androidTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            //Jeu de donnée pour la Preview
            val mainViewModel : MainViewModel = viewModel()
            mainViewModel.myList.addAll(pictureList)
            mainViewModel.searchText.value = "BC"

            SearchScreen(mainViewModel = mainViewModel)
        }
    }
}

@Composable
fun SearchScreen(
    navHostController: NavHostController? = null,
    mainViewModel: MainViewModel) {


//    //Lancement au démarrage de l'écran uniquement
//    LaunchedEffect("") {
//        println("loadData")
    //mainViewModel.searchText.value = "Toulouse"
//        mainViewModel.loadData()
//    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {

        //SearchBar
        SearchBar(searchText = mainViewModel.searchText)


        Spacer(Modifier.size(4.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {

            //
            val filterList = mainViewModel.myList //.filter { it.title.contains(mainViewModel.searchText.value, ignoreCase = true) }

            items(filterList.size) {
                PictureRowItem(data = filterList[it],
                    onPictureClick = {
                        navHostController?.navigate(Routes.DetailScreen.withObject(filterList[it]))
                    })
            }
        }

        Row {
            Button(
                onClick = { mainViewModel .searchText.value = "" },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Clear filter")
            }

            Spacer(Modifier.size(ButtonDefaults.IconSpacing))

            Button(
                onClick = { mainViewModel.loadData() },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Load data")
            }
        }

        //Bouton
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, searchText: MutableState<String>) {

    TextField(
        value = searchText.value, //Valeur par défaut
        onValueChange = {
            searchText.value = it
        }, //Action
        singleLine = true,
        leadingIcon = { //Image d'icone
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        },
        label = { Text("Enter text") }, //Texte d'aide qui se déplace
        //Comment le composant doit se placer
        modifier = modifier
            .fillMaxWidth() // Prend toute la largeur
            .heightIn(min = 56.dp) //Hauteur minimum
    )
}


//@Composable
//fun HideKeyboard() {
//    val context = LocalContext.current
//    val focusManager = LocalFocusManager.current
//
//    focusManager.clearFocus()
//    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    inputMethodManager.hideSoftInputFromWindow(focusManager.currentFocus?.windowToken, 0)
//}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PictureRowItem(modifier: Modifier = Modifier, data: PictureBean, onPictureClick: () -> Unit) {

    var expended by remember { mutableStateOf(false) }

    Row(modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {

//Permission Internet nécessaire
        GlideImage(
            model = data.url,
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
                .clickable(onClick = onPictureClick)
                .clickable {
                    onPictureClick()
                }
        )


        Column(modifier = Modifier.padding(5.dp)) {
            Text(
                text = data.title,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = if (!expended) data.longText.take(20) + "..." else data.longText,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable {
                        expended = !expended
                    }
                    .animateContentSize()
            )
        }
    }

}