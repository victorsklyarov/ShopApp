package com.zeroillusion.shopapp.feature.home.presentation.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zeroillusion.shopapp.feature.home.R
import com.zeroillusion.shopapp.feature.home.domain.model.Category
import com.zeroillusion.shopapp.feature.home.presentation.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state = viewModel.state.value
    val currentUserState = viewModel.currentUserState.value
    val currentUser by viewModel.currentUser.collectAsState()
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    var searchText by rememberSaveable { mutableStateOf("") }
    viewModel.setCategories(
        listOf(
            Category(stringResource(id = R.string.category_phones), R.drawable.ic_phone),
            Category(stringResource(id = R.string.category_headphones), R.drawable.ic_headphone),
            Category(stringResource(id = R.string.category_games), R.drawable.ic_game),
            Category(stringResource(id = R.string.category_cars), R.drawable.ic_car),
            Category(stringResource(id = R.string.category_furniture), R.drawable.ic_furniture),
            Category(stringResource(id = R.string.category_kids), R.drawable.ic_kids)
        )
    )
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->
            LaunchedEffect(key1 = true) {
                viewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is HomeViewModel.UIEvent.ShowSnackbar -> {
                            snackbarHostState.showSnackbar(
                                message = event.message
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { focusManager.clearFocus() }
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .widthIn(max = 375.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.Center)
                        ) {
                            Text(
                                text = "Trade by ",
                                style = MaterialTheme.typography.displayLarge.copy(
                                    fontSize = 20.sp
                                )
                            )
                            Text(
                                text = "Seller #1",
                                style = MaterialTheme.typography.displayLarge.copy(
                                    fontSize = 20.sp,
                                    color = Color.Blue
                                )
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .align(Alignment.CenterEnd),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Box {
                                if (currentUserState.isLoading) {
                                    Box(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(CircleShape)
                                    ) {
                                        Image(
                                            bitmap = BitmapFactory.decodeResource(
                                                LocalContext.current.resources,
                                                com.zeroillusion.shopapp.core.presentation.R.drawable.default_image
                                            ).asImageBitmap(),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(30.dp)
                                                .clip(CircleShape)
                                                .border(1.dp, Color.Gray, CircleShape)
                                        )
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(
                                                Alignment.Center
                                            )
                                        )
                                    }
                                } else {
                                    Image(
                                        bitmap = currentUser?.image?.size?.let {
                                            BitmapFactory.decodeByteArray(
                                                currentUser?.image, 0, it
                                            ).asImageBitmap()
                                        } ?: BitmapFactory.decodeResource(
                                            LocalContext.current.resources,
                                            com.zeroillusion.shopapp.core.presentation.R.drawable.default_image
                                        ).asImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(CircleShape)
                                            .border(1.dp, Color.Gray, CircleShape)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                Text(
                                    text = "Location",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Icon(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .size(15.dp),
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    SearchField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        ifClickCloseButton = { searchText = "" },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyRow(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        items(viewModel.categories.value?.size ?: 0) { i ->
                            Column(
                                modifier = Modifier
                                    .padding(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                            shape = CircleShape
                                        )
                                        .size(40.dp)
                                        .align(Alignment.CenterHorizontally)
                                ) {
                                    Icon(
                                        painter = painterResource(viewModel.categories.value?.get(i)?.icon!!),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(20.dp)
                                            .align(Alignment.Center)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = viewModel.categories.value?.get(i)?.category!!,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally),
                                    style = TextStyle(
                                        fontSize = 8.sp
                                    )
                                )
                            }
                        }
                    }
                    ProductsBlock(
                        title = "Latest",
                        size = state.latestProductItems.size
                    ) { i ->
                        LatestProductItem(
                            latestProductItem = state.latestProductItems[i],
                            onItemClick = { }
                        )
                    }
                    ProductsBlock(
                        title = "Flash Sale",
                        size = state.flashSaleProductItems.size
                    ) { i ->
                        FlashSaleProductItem(
                            flashSaleProductItem = state.flashSaleProductItems[i],
                            onItemClick = { }
                        )
                    }
                    ProductsBlock(
                        title = "Brands",
                        size = state.latestProductItems.size
                    ) { i ->
                        LatestProductItem(
                            latestProductItem = state.latestProductItems[i],
                            onItemClick = { }
                        )
                    }
                }
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    )
}