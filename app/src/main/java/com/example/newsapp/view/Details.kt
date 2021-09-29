package com.example.newsapp.view

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.example.newsapp.R
import com.example.newsapp.model.Articles
import com.example.newsapp.other.Constants
import com.example.newsapp.view_model.HomeViewModel
import kotlin.random.Random

@SuppressLint("UnrememberedGetBackStackEntry")
@ExperimentalCoilApi
@Composable
fun Details(
    index: Int,
    navHostController: NavHostController, homeViewModel: HomeViewModel
) {
    val articles = homeViewModel.allNews.value[index]
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(20.dp), color = Color(0xff0a0959), elevation = 40.dp
    ) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
            ) {

                articles.urlToImage?.let { SectionOne(it, navHostController, articles) }
            }
            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
            ) {
                articles.author?.let { SectionTow(it) }
            }
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
            ) {
                articles.title?.let { articles.description?.let { it1 -> SectionThree(it, it1) } }
            }
        }
    }
}

@Composable
fun SectionThree(title: String, desc: String) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = title, fontSize = MaterialTheme.typography.h6.fontSize,
            color = Color.Gray,
            maxLines = 2, textAlign = TextAlign.End, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = desc, fontSize = MaterialTheme.typography.h6.fontSize,
                    color = Color.White,
                    maxLines = 20, textAlign = TextAlign.End,
                )
            }
        }

    }

}

@Composable
fun SectionTow(author: String) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = author, fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            maxLines = 2, textAlign = TextAlign.End,

            )
        Box(
            modifier = Modifier
                .weight(0.1f), contentAlignment = Alignment.TopEnd
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp)
                    .width(5.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFff7a2e))
            )
        }

    }

}
fun borderColor(): Color {
    return Color.Gray
}

fun contentColor(): Color {
    return Color.Gray
}
@ExperimentalCoilApi
@Composable
fun SectionOne(url: String, navHostController: NavHostController, articles: Articles) {

    Box(modifier = Modifier.fillMaxSize()) {
        val painter = rememberImagePainter(data = url, builder = {
            transformations(RoundedCornersTransformation(20f))
            error(R.drawable.loading)
        })
        Image(
            painter = painter,
            contentDescription = "Just Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft, tint = contentColor(),
                    contentDescription = "",
                    modifier = Modifier
                        .border(BorderStroke(2.dp, borderColor()), CircleShape)
                        .padding(5.dp)
                        .clickable {
                            navHostController.popBackStack()
                        }
                )
                val context = LocalContext.current
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Default.Share,
                    tint = contentColor(),
                    contentDescription = "",
                    modifier = Modifier
                        .border(BorderStroke(2.dp, borderColor()), CircleShape)
                        .padding(5.dp)
                        .clickable {
                            articles.url?.let { Constants.shareData(it, context) }
                        })
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier
                        .border(BorderStroke(2.dp, borderColor()), CircleShape)
                        .padding(10.dp),
                    text = "${Random.nextInt(1, 10)} Hours ago",
                    fontWeight = FontWeight.Bold,
                    color = contentColor(),
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier
                    .border(BorderStroke(2.dp, borderColor()), CircleShape)
                    .padding(horizontal = 10.dp,vertical = 7.dp),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.spacedBy(1.dp)){
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = "",
                        tint = contentColor()
                    )
                    Text(
                        text = "  ${Random.nextInt(1, 1000)}",
                        fontWeight = FontWeight.Bold,
                        color = contentColor(),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

