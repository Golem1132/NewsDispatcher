package com.example.newsdispatcher.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import com.example.newsdispatcher.R
import com.example.newsdispatcher.database.data.SearchHistory
import java.util.Date

@Composable
fun HistoryItem(modifier: Modifier = Modifier, searchHistory: SearchHistory, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .then(Modifier.clickable {
                onClick()
            }), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.history_24px),
            contentDescription = "Search icon"
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = searchHistory.query)
    }

}

@PreviewFontScale
@Composable
fun PreviewHistoryItem() {
    HistoryItem(searchHistory = SearchHistory(query = "bitcoin", time = Date().time), onClick = {})
}