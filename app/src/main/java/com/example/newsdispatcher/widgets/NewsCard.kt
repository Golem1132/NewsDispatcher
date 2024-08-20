package com.example.newsdispatcher.widgets

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier,
        onClick = {
            onClick()
        }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            SubcomposeAsyncImage(model = "", contentDescription = "") {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f)
                            .composed {
                                var size by remember {
                                    mutableStateOf(IntSize.Zero)
                                }
                                val transition = rememberInfiniteTransition(label = "")
                                val startOffsetX by transition.animateFloat(
                                    initialValue = -2 * size.width.toFloat(),
                                    targetValue = 2 * size.width.toFloat(),
                                    animationSpec = infiniteRepeatable(tween(1500)),
                                    label = ""
                                )
                                background(
                                    brush = Brush.linearGradient(
                                        listOf(
                                            Color.LightGray,
                                            Color.Gray,
                                            Color.LightGray,
                                        ),
                                        start = Offset(startOffsetX, 0f),
                                        end = Offset(
                                            startOffsetX + size.width,
                                            size.height.toFloat()
                                        )
                                    )
                                ).onGloballyPositioned {
                                    size = it.size
                                }
                            }
                    )

                    is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()

                    else -> Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = Modifier.padding(vertical = 10.dp),
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "Error while loading image"
                            )
                            Text(text = "Could not load an image")

                        }

                    }

                }
            }
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = "Title title title title. Title title title title title. Title titletitle",
                style = MaterialTheme.typography.headlineMedium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End)
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Default.Favorite, contentDescription = "Save"
                )
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Save"
                )
            }
        }
    }
}