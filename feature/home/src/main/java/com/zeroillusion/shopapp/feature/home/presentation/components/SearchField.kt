package com.zeroillusion.shopapp.feature.home.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    ifClickCloseButton: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = RoundedCornerShape(20.dp),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
    ),
    contentPadding: PaddingValues = TextFieldDefaults.textFieldWithoutLabelPadding(
        top = 0.dp, bottom = 0.dp
    )
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        textStyle = MaterialTheme.typography.bodyMedium
            .merge(TextStyle(color = MaterialTheme.colorScheme.onSurface)),
        modifier = modifier
            .height(25.dp)
            .width(250.dp),
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                leadingIcon = {},
                trailingIcon = @Composable {
                    if (value.isEmpty()) {
                        IconButton(
                            onClick = {
                                //Start searching
                            },
                        ) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(
                            onClick = ifClickCloseButton,
                        ) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = null
                            )
                        }
                    }
                },
                isError = isError,
                placeholder = {
                    Text(
                        text = "What are you looking for?",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                colors = colors,
                contentPadding = contentPadding,
                container = {
                    TextFieldDefaults.OutlinedBorderContainerBox(
                        enabled,
                        isError,
                        interactionSource,
                        colors,
                        shape
                    )
                }
            )
        }
    )
}

@Preview
@Composable
fun SearchFieldPreview() {
    var searchText by rememberSaveable { mutableStateOf("") }
    SearchField(
        value = searchText,
        onValueChange = { searchText = it },
        ifClickCloseButton = { searchText = "" }
    )
}