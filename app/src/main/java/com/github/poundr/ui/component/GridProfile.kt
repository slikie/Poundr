package com.github.poundr.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.poundr.R

@Composable
fun GridProfile(
    modifier: Modifier = Modifier,
    imageId: String?,
    name: String?,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.aspectRatio(1f).clickable(onClick = onClick)
    ) {
        if (imageId == null) {
            Image(
                painter = painterResource(R.drawable.profile_pic_placeholder),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = "https://cdns.grindr.com/images/thumb/320x320/$imageId",
                contentDescription = null,
                modifier = Modifier.fillMaxSize().align(Alignment.Center),
                placeholder = painterResource(R.drawable.profile_pic_placeholder),
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = name ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                    )
                )
                .padding(
                    start = 8.dp,
                    top = 4.dp,
                    end = 8.dp,
                    bottom = 4.dp
                )
                .align(Alignment.BottomStart),
            color = Color.White,
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun GridProfilePreview() {
    Surface {
        GridProfile(
            imageId = null,
            name = "Test name",
            onClick = {}
        )
    }
}