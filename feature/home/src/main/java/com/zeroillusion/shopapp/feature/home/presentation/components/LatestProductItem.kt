package com.zeroillusion.shopapp.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zeroillusion.shopapp.feature.home.R
import com.zeroillusion.shopapp.feature.home.domain.model.LatestProduct

@Composable
fun LatestProductItem(
    latestProductItem: LatestProduct,
    onItemClick: (LatestProduct) -> Unit
) {
    Box(
        modifier = Modifier
            .height(150.dp)
            .width(115.dp)
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(latestProductItem.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(75.dp)
                .padding(top = 108.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(0.dp, 10.dp, 0.dp, 0.dp)
                )
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.secondaryContainer,
                    RoundedCornerShape(0.dp, 10.dp, 0.dp, 0.dp)
                )
        ) {}
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(75.dp)
                .padding(top = 90.dp, start = 5.dp, bottom = 5.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val categoryColor = Color.LightGray.copy(alpha = 0.8f)
            Text(
                modifier = Modifier
                    .height(12.dp)
                    .width(35.dp)
                    .padding(bottom = 2.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = categoryColor,
                            cornerRadius = CornerRadius(50f)
                        )
                    }
                    .border(1.dp, Color.Gray, RoundedCornerShape(20.dp)),
                textAlign = TextAlign.Center,
                text = latestProductItem.category,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 6.sp
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    fontSize = 9.sp
                ),
                text = latestProductItem.name
            )
            Text(
                text = "$ ${latestProductItem.price}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 7.sp
                )
            )
        }
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(5.dp)
                .size(20.dp),
            onClick = { }
        ) {
            val buttonColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.8f)
            Icon(
                painter = painterResource(id = R.drawable.ic_add_to_cart_btn),
                contentDescription = "Add to cart",
                modifier = Modifier
                    .size(20.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = buttonColor,
                            cornerRadius = CornerRadius(50f)
                        )
                    }
                    .border(1.dp, MaterialTheme.colorScheme.tertiaryContainer, CircleShape)
            )
        }
    }
}

@Preview
@Composable
fun LatestProductItemPreview() {
    LatestProductItem(
        latestProductItem = LatestProduct(
            category = "Phone",
            name = "It is name of phone",
            price = 100,
            imageUrl = ""
        ),
        onItemClick = { }
    )
}