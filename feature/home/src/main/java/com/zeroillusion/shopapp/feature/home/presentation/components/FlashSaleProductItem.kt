package com.zeroillusion.shopapp.feature.home.presentation.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zeroillusion.shopapp.feature.home.R
import com.zeroillusion.shopapp.feature.home.domain.model.FlashSaleProduct

@Composable
fun FlashSaleProductItem(
    flashSaleProductItem: FlashSaleProduct,
    onItemClick: (FlashSaleProduct) -> Unit
) {
    Box(
        modifier = Modifier
            .height(220.dp)
            .width(175.dp)
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(flashSaleProductItem.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                bitmap = BitmapFactory.decodeResource(
                    LocalContext.current.resources,
                    com.zeroillusion.shopapp.core.presentation.R.drawable.default_image
                ).asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape)
            )
            Text(
                modifier = Modifier
                    .height(17.dp)
                    .width(50.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = Color.Red,
                            cornerRadius = CornerRadius(50f)
                        )
                    },
                textAlign = TextAlign.Center,
                text = "${flashSaleProductItem.discount}% off",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(80.dp)
                .padding(top = 143.dp)
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
                .width(80.dp)
                .padding(top = 120.dp, start = 10.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                val categoryColor = Color.LightGray.copy(alpha = 0.8f)
                Text(
                    modifier = Modifier
                        .height(17.dp)
                        .width(50.dp)
                        .padding(bottom = 2.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = categoryColor,
                                cornerRadius = CornerRadius(50f)
                            )
                        }
                        .border(1.dp, Color.Gray, RoundedCornerShape(20.dp)),
                    textAlign = TextAlign.Center,
                    text = flashSaleProductItem.category,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = flashSaleProductItem.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    )
                )
            }
            Text(
                text = "$ ${flashSaleProductItem.price}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val buttonColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.8f)
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(30.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_to_favorite_btn),
                    contentDescription = "Add to favorite",
                    modifier = Modifier
                        .size(30.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = buttonColor,
                                cornerRadius = CornerRadius(50f)
                            )
                        }
                        .border(1.dp, MaterialTheme.colorScheme.tertiaryContainer, CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(35.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_to_cart_btn),
                    contentDescription = "Add to cart",
                    modifier = Modifier
                        .size(35.dp)
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
}

@Preview
@Composable
fun FlashSaleProductItemPreview() {
    FlashSaleProductItem(
        flashSaleProductItem = FlashSaleProduct(
            category = "Phone",
            name = "It is name of phone",
            price = 100.0,
            imageUrl = "",
            discount = 30
        ),
        onItemClick = { }
    )
}