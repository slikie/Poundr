package com.github.poundr.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.poundr.R
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@Composable
fun MessageRow(
    modifier: Modifier = Modifier,
    imageId: String?,
    name: String?,
    lastMessage: String?,
    lastMessageTime: Long,
    onClick: () -> Unit
) {
    var elapsedTimeString by remember(lastMessageTime) {
        mutableStateOf(getElapsedTimeString(lastMessageTime))
    }

    LaunchedEffect(lastMessageTime) {
        while (true) {
            val nextUpdateDelay = 1.minutes.inWholeMilliseconds - (System.currentTimeMillis() - lastMessageTime) % 1.minutes.inWholeMilliseconds
            delay(nextUpdateDelay)
            elapsedTimeString = getElapsedTimeString(lastMessageTime)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (imageId == null) {
            Image(
                painter = painterResource(R.drawable.profile_pic_placeholder),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = "https://cdns.grindr.com/images/thumb/320x320/$imageId",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f),
                placeholder = painterResource(R.drawable.profile_pic_placeholder),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = name ?: "",
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = lastMessage ?: "",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text(
            text = elapsedTimeString,
        )
    }
}

private fun getElapsedTimeString(startTime: Long): String {
    val elapsedTime = System.currentTimeMillis() - startTime
    
    return if (elapsedTime < 1.minutes.inWholeMilliseconds) {
        "Just now"
    } else if (elapsedTime < 1.hours.inWholeMilliseconds) {
        "${elapsedTime / 1.minutes.inWholeMilliseconds}m ago"
    } else if (elapsedTime < 1.days.inWholeMilliseconds) {
        "${elapsedTime / 1.hours.inWholeMilliseconds}h ago"
    } else {
        "${elapsedTime / 1.days.inWholeMilliseconds}d ago"
    }
}

@Preview
@Composable
private fun MessageRowPreview() {
    Surface {
        MessageRow(
            imageId = null,
            name = "John Doe",
            lastMessage = "Hello, world!",
            lastMessageTime = System.currentTimeMillis() - 43.minutes.inWholeMilliseconds,
            onClick = {}
        )
    }
}