package com.github.poundr.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.poundr.R
import com.github.poundr.network.model.PartialProfileItemData
import com.github.poundr.network.model.ProfileItemData

@Composable
fun Profile(
    data: ProfileItemData
) {
    Profile(
        photoMediaHashes = data.photoMediaHashes ?: emptyList(),
        displayName = data.displayName ?: "",
        age = data.age ?: 0,
        distance = data.distanceMeters ?: 0.0,
        aboutMe = data.aboutMe ?: ""
    )
}

@Composable
fun Profile(
    data: PartialProfileItemData
) {
    Profile(
        photoMediaHashes = data.photoMediaHashes ?: emptyList(),
        displayName = data.displayName ?: "",
        age = 0,
        distance = data.distanceMeters ?: 0.0,
        aboutMe = ""
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    photoMediaHashes: List<String>,
    displayName: String,
    age: Int,
    distance: Double,
    aboutMe: String,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty */ },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                         Icon(
                             imageVector = Icons.AutoMirrored.Default.ArrowBack,
                             contentDescription = "Back"
                         )
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextField(
                    value = "",
                    onValueChange = {  },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Say something...") },
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
//                .verticalScroll(rememberScrollState())
        ) {
            if (photoMediaHashes.isEmpty()) {
                Image(
                    painter = painterResource(R.drawable.profile_pic_placeholder),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Display 10 items
                val pagerState = rememberPagerState(
                    pageCount = { photoMediaHashes.size }
                )
                VerticalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                ) { page ->
                    AsyncImage(
                        model = "https://cdns.grindr.com/images/profile/2048x2048/${photoMediaHashes[page]}",
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        placeholder = painterResource(R.drawable.profile_pic_placeholder),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = displayName,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$age",
                    modifier = Modifier.weight(1f)
                )
            }

            Text(text = "Distance: $distance")

            Text(text = aboutMe)
        }
    }
}