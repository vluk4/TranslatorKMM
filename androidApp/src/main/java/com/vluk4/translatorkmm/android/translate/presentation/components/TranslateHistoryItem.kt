package com.vluk4.translatorkmm.android.translate.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vluk4.translatorkmm.R
import com.vluk4.translatorkmm.android.R.*
import com.vluk4.translatorkmm.android.TranslatorTheme
import com.vluk4.translatorkmm.android.core.theme.LightBlue
import com.vluk4.translatorkmm.core.domain.language.Language
import com.vluk4.translatorkmm.core.presentation.UiLanguage
import com.vluk4.translatorkmm.translate.presentation.UiHistoryItem

@Composable
fun TranslateHistoryItem(
    item: UiHistoryItem,
    onItemClick: () -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()
            .clickable(onClick = onItemClick)
    ) {
        IconButton(
            onClick = onDeleteClicked,
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = string.delete),
                tint = Color.Red
            )
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallLanguageIcon(language = item.fromLanguage)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.fromText,
                    color = LightBlue,
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallLanguageIcon(language = item.toLanguage)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.toText,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview
@Composable
fun TranslateHistoryItemPreview() {
    TranslatorTheme {
        TranslateHistoryItem(
            item = UiHistoryItem(
                id = 0,
                fromText = "Kitchen in the jungle",
                toText = "Kitchen in the jungle",
                fromLanguage = UiLanguage(
                    drawableRes = R.drawable.chinese,
                    language = Language.CHINESE
                ),
                toLanguage = UiLanguage(
                    drawableRes = R.drawable.english,
                    language = Language.ENGLISH
                )
            ),
            onItemClick = { },
            onDeleteClicked = { }
        )
    }
}