package com.zeroillusion.shopapp.feature.profile.presentation.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zeroillusion.shopapp.core.domain.model.CurrentUser
import com.zeroillusion.shopapp.feature.profile.R
import com.zeroillusion.shopapp.feature.profile.presentation.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val currentUserState = viewModel.currentUserState.value
    val currentUser by viewModel.currentUser.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is ProfileViewModel.UIEvent.ShowSnackbar -> {
                                snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }

                            is ProfileViewModel.UIEvent.NavigateToSignIn -> {
                                navController.navigate("sign_in_screen") {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(300.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Profile",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        if (currentUserState.isLoading) {
                            Box(
                                modifier = Modifier
                                    .size(55.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            ) {
                                Image(
                                    bitmap = BitmapFactory.decodeResource(
                                        LocalContext.current.resources,
                                        com.zeroillusion.shopapp.core.presentation.R.drawable.default_image
                                    ).asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(55.dp)
                                        .clip(CircleShape)
                                        .border(1.dp, Color.Gray, CircleShape)
                                )
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
                                    .size(55.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.Gray, CircleShape)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    var imageUri by remember { mutableStateOf<Uri?>(null) }
                    val context = LocalContext.current
                    val launcher = rememberLauncherForActivityResult(
                        ActivityResultContracts.PickVisualMedia()
                    ) {
                        imageUri = it
                    }

                    Text(
                        text = "Change photo",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 8.sp
                        ),
                        modifier = Modifier
                            .clickable {
                                launcher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }
                            .align(Alignment.CenterHorizontally)
                    )

                    imageUri?.let {
                        val bitmapOriginal = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            ImageDecoder.decodeBitmap(
                                ImageDecoder.createSource(
                                    context.contentResolver,
                                    it
                                )
                            )
                        } else {
                            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                        }
                        val bitmap = if (bitmapOriginal.width >= bitmapOriginal.height) {
                            Bitmap.createBitmap(
                                bitmapOriginal,
                                bitmapOriginal.width / 2 - bitmapOriginal.height / 2,
                                0,
                                bitmapOriginal.height,
                                bitmapOriginal.height
                            )
                        } else {
                            Bitmap.createBitmap(
                                bitmapOriginal,
                                0,
                                bitmapOriginal.height / 2 - bitmapOriginal.width / 2,
                                bitmapOriginal.width,
                                bitmapOriginal.width
                            )
                        }
                        val stream = ByteArrayOutputStream()
                        Bitmap
                            .createScaledBitmap(bitmap, 50, 50, true)
                            .compress(Bitmap.CompressFormat.JPEG, 100, stream)
                        val bytes: ByteArray = stream.toByteArray()
                        currentUser?.let {
                            viewModel.updateCurrentUser(
                                CurrentUser(
                                    id = currentUser?.id!!,
                                    firstName = currentUser?.firstName!!,
                                    lastName = currentUser?.lastName!!,
                                    email = currentUser?.email!!,
                                    password = currentUser?.password!!,
                                    image = bytes
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = if (currentUser != null) "${currentUser?.firstName} ${currentUser?.lastName}"
                        else "Welcome",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .height(45.dp)
                            .width(280.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_upload),
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = "Upload item",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.width(30.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    ProfileItem(
                        text = "Trade store",
                        icon = R.drawable.ic_bank_card,
                        isArrow = true,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    ProfileItem(
                        text = "Payment method",
                        icon = R.drawable.ic_bank_card,
                        isArrow = true,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    ProfileItem(
                        text = "Balance",
                        icon = R.drawable.ic_bank_card,
                        trailingText = "$ 1593",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    ProfileItem(
                        text = "Trade history",
                        icon = R.drawable.ic_bank_card,
                        isArrow = true,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    ProfileItem(
                        text = "Restore Purchase",
                        icon = R.drawable.ic_restore,
                        isArrow = true,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    ProfileItem(
                        text = "Help",
                        icon = R.drawable.ic_help,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    ProfileItem(
                        text = "Log out",
                        icon = R.drawable.ic_log_out,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable { viewModel.logOut() }
                    )
                }
            }
        }
    )
}