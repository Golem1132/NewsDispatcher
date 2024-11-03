package com.example.newsdispatcher.appbar

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsdispatcher.navigation.FavouriteScreenRoutes

@Composable
fun NewsTopAppBar(
    navController: NavController,
    isActive: Boolean,
    onActiveChange: (Boolean) -> Unit = {},
    query: String,
    onQueryChange: (String) -> Unit = {},
    onKeyboardAction: (KeyboardActionScope) -> Unit = {},
    onTrailingIconClick: () -> Unit = {},
    hasAction: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = if (isActive) 0.dp else 16.dp)
            .height(64.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .animateContentSize(
                    animationSpec = tween(5000, easing = LinearOutSlowInEasing)
                )
                .onFocusChanged {
                    onActiveChange(it.isFocused)
                },
            placeholder = {
                Text(text = "What are you looking for?")
            },
            shape = if (isActive) RectangleShape else CircleShape,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = ""
                )
            },
            trailingIcon = {
                if (isActive)
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier.clickable(onClick = onTrailingIconClick)
                    )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = onKeyboardAction),
            value = query, onValueChange = onQueryChange
        )
        Crossfade(
            targetState = isActive,
            label = "Change icons on search bar active"
        ) { isActive ->
            if (hasAction) {
                if (!isActive) {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f)
                            .clickable {
                                navController.navigate(FavouriteScreenRoutes.FAVOURITE_SCREEN)
                            },
                        imageVector = Icons.Default.Star,
                        contentDescription = "Placeholder for no photo or error"
                    )
                }
            }
        }

    }
}