package com.example.newsapp.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.example.newsapp.R
import com.example.newsapp.model.remote.Articles
import com.example.newsapp.view_model.HomeViewModel
import com.example.newsapp.view_model.ParentViewModel
import kotlin.random.Random

@ExperimentalCoilApi
@Composable
fun Home(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val list by homeViewModel.allNews.observeAsState()
    list?.let { DrawUI(it, navHostController) } ?: Text(
        text = "Wait",
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

@ExperimentalCoilApi
@Composable
fun DrawUI(list: List<Articles>, navHostController: NavHostController) {
    Log.d("lol", "DrawUI: List Size =  ${list.size}")
    LazyColumn(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxSize(), text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xfff9f2ec),
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp, fontFamily = FontFamily.Serif
                    )
                ) {
                    append("Latest \n")
                    append("News")
                }
            })
        }
        items(list) { item ->
            DrawCard(item, navHostController)
        }
    }
}


@ExperimentalCoilApi
@Composable
fun DrawCard(
    item: Articles,
    navHostController: NavHostController,
    parentViewModel: ParentViewModel = hiltViewModel(LocalContext.current as ViewModelStoreOwner)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(15.dp)
            .clickable {
                parentViewModel.getArticlesObject(item = item) // Sending Data
                navHostController.navigate("Details")
            }, elevation = 40.dp, backgroundColor = Color(0xff0a0959)
    ) {
        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .weight(2f)
                    .padding(5.dp)
            ) {
                if (item.author != null) {
                    WriteText(item.author, item.title!!)
                } else {
                    if (item.title != null)
                        WriteText(title = item.title)
                    else
                        WriteText()
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f), contentAlignment = Alignment.Center
            ) {
                val painter = rememberImagePainter(data = item.urlToImage, builder = {
                    transformations(RoundedCornersTransformation(20f))
                    error(R.drawable.loading)
                })
                Image(painter = painter, contentDescription = "Just Image")
                val state = painter.state
                if ((state is ImagePainter.State.Loading) || (state is ImagePainter.State.Error)) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun WriteText(author: String = "No Data", title: String = "From Service Provider") {
    val map = mapOf(2 to Color.Blue, 1 to Color.Magenta)
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Surface(
                shape = CircleShape,
                color = map[Random.nextInt(1, 3)]!!,

                ) {
                Text(
                    text = author, color = Color.White, modifier = Modifier
                        .padding(horizontal = 15.dp), fontWeight = FontWeight.SemiBold
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(2f), contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = title,
                color = Color(0xfff9f2ec),
                textAlign = TextAlign.End,
                fontFamily = FontFamily.Default, maxLines = 3, overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
