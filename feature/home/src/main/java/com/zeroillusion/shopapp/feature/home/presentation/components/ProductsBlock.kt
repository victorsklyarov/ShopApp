package com.zeroillusion.shopapp.feature.home.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zeroillusion.shopapp.feature.home.domain.model.LatestProduct

@Composable
fun ProductsBlock(
    title: String,
    size: Int,
    content: @Composable (Int) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(start = 10.dp, bottom = 4.dp),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "View all",
                modifier = Modifier.padding(end = 10.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(size) {
                content(it)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProductsBlockPreview() {
    val latestProductItems: List<LatestProduct> = listOf(
        LatestProduct(
            category = "Category",
            name = "Phone",
            price = 1500,
            imageUrl = ""
        ),
        LatestProduct(
            category = "Category",
            name = "Phone",
            price = 1500,
            imageUrl = ""
        )
    )
    ProductsBlock(
        title = "Latest",
        size = latestProductItems.size,
        content = { i ->
            LatestProductItem(
                latestProductItem = latestProductItems[i],
                onItemClick = { }
            )
        }
    )
}