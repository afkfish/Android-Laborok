package hu.bme.aut.android.network.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hu.bme.aut.android.network.R
import hu.bme.aut.android.network.ui.model.UnsplashPhotoUiModel
import hu.bme.aut.android.network.ui.theme.UnsplashTheme


@Composable
fun PhotoItem(
    photo: UnsplashPhotoUiModel,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { },
) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .padding(5.dp)
            .clip(CardDefaults.shape)
            .clickable(enabled = true) { onClick(photo.id) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(photo.photoUrl)
                .crossfade(enable = true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder),
            modifier = Modifier
                .height(150.dp)
        )
        Box(Modifier.fillMaxWidth()) {
            LikesTracker(likes = photo.likes, modifier = Modifier.align(Alignment.CenterStart))
            UserData(
                username = photo.username,
                userProfileImageUrl = photo.userProfileImageUrl,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
private fun LikesTracker(
    likes: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(5.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ThumbUp,
            contentDescription = null,
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 5.dp)
        )
        Text(
            text = "$likes",
            modifier = Modifier.padding(5.dp),
        )
    }
}

@Preview
@Composable
fun LikesTracker_Preview() {
    UnsplashTheme() {
        LikesTracker(likes = 5)
    }
}

@Composable
private fun UserData(
    username: String,
    userProfileImageUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(5.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(userProfileImageUrl)
                .crossfade(enable = true)
                .build()
            ,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp)
                .size(30.dp)
                .clip(CircleShape)
        )
        Text(
            text = username,
            modifier = Modifier.padding(5.dp)
        )
    }
}