package com.zeroillusion.shopapp.core.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
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
    Box(
        modifier = modifier
            .height(50.dp)
            .width(280.dp),
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyMedium
                .merge(TextStyle(color = MaterialTheme.colorScheme.onSurface)),
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            modifier = modifier.fillMaxSize(),
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.OutlinedTextFieldDecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    singleLine = singleLine,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource,
                    isError = isError,
                    placeholder = {
                        Text(
                            text = placeholder ?: "",
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    supportingText = {
                        Text(
                            text = if (isError) supportingText ?: "" else "",
                            color = MaterialTheme.colorScheme.error,
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
        Row(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null && trailingIcon == null) {
                leadingIcon()
            } else if (leadingIcon == null && trailingIcon != null) {
                Box {}
                trailingIcon()
            } else if (leadingIcon != null && trailingIcon != null) {
                leadingIcon()
                trailingIcon()
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview
@Composable
fun InputFieldPreview() {
    var inputText by rememberSaveable { mutableStateOf("") }
    InputField(
        value = inputText,
        onValueChange = { inputText = it },
        placeholder = "Email"
    )
}