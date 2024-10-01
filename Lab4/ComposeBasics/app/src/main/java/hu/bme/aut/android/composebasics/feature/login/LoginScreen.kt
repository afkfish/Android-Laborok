package hu.bme.aut.android.composebasics.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.composebasics.R
import hu.bme.aut.android.composebasics.ui.common.FullWideButton
import hu.bme.aut.android.composebasics.ui.common.NormalTextField
import hu.bme.aut.android.composebasics.ui.common.PasswordTextField

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: (String) -> Unit,
    onRegisterClick: () -> Unit,
) {
    var usernameValue by remember { mutableStateOf("") }
    var isUsernameError by remember { mutableStateOf(false) }

    var passwordValue by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            NormalTextField(
                value = usernameValue,
                label = stringResource(id = R.string.textfield_label_username),
                onValueChange = { newValue ->
                    usernameValue = newValue
                    isUsernameError = false
                },
                isError = isUsernameError,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                trailingIcon = { },
                onDone = { }
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordTextField(
                value = passwordValue,
                label = stringResource(id = R.string.textfield_label_password),
                onValueChange = { newValue ->
                    passwordValue = newValue
                    isPasswordError = false
                },
                isError = isPasswordError,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Key,
                        contentDescription = null
                    )
                },
                isVisible = isPasswordVisible,
                onVisibilityChanged = { isPasswordVisible = !isPasswordVisible },
                onDone = { }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    if (usernameValue.isEmpty()) {
                        isUsernameError = true
                    } else if (passwordValue.isEmpty()) {
                        isPasswordError = true
                    } else {
                        onLoginClick(usernameValue)
                    }
                },
                modifier = Modifier.width(TextFieldDefaults.MinWidth)
            ) {
                Text(text = stringResource(id = R.string.button_label_login))
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            FullWideButton(
                text = stringResource(id = R.string.button_label_register),
                onClick = onRegisterClick
            )
        }
    }
}
// JOYAXJ

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun LoginScreen_Preview() {
    LoginScreen(
        onLoginClick = { },
        onRegisterClick = { }
    )
}