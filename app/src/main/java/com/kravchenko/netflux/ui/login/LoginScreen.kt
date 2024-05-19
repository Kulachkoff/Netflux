package com.kravchenko.netflux.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kravchenko.netflux.ui.navigation.Routes
import com.kravchenko.netflux.ui.theme.LightBlue
import com.kravchenko.netflux.ui.theme.LightGrey
import com.kravchenko.netflux.ui.theme.PrimaryBlack
import com.kravchenko.netflux.ui.theme.PrimaryGreen
import com.kravchenko.netflux.ui.theme.White
import com.kravchenko.netflux.ui.theme.inter


@Composable
fun LoginScreen(navController: NavController) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBlack)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = "Login",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = inter
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            label = {
                Text(text = "Email")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = ""
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryGreen,
                unfocusedBorderColor = LightGrey,
                focusedTextColor = PrimaryGreen,
                unfocusedTextColor = LightGrey,
                focusedLabelColor = PrimaryGreen,
                unfocusedLabelColor = LightGrey,
                focusedLeadingIconColor = PrimaryGreen,
                unfocusedLeadingIconColor = LightGrey
            )
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            label = {
                Text(text = "Password")
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = ""
                )
            },
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}) {
                    Icon(imageVector = image, description)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryGreen,
                unfocusedBorderColor = LightGrey,
                focusedTextColor = PrimaryGreen,
                unfocusedTextColor = LightGrey,
                focusedLabelColor = PrimaryGreen,
                unfocusedLabelColor = LightGrey,
                focusedLeadingIconColor = PrimaryGreen,
                unfocusedLeadingIconColor = LightGrey,
                focusedTrailingIconColor = PrimaryGreen,
                unfocusedTrailingIconColor = LightGrey
            )
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            onClick = { navController.navigate(Routes.Main.route) {
                popUpTo(Routes.Auth.route) { inclusive = true }
            } },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(PrimaryGreen)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = "Login",
                color = Color.Black,
                fontFamily = inter,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                color = White,
                fontSize = 14.sp,
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                text = "Haven't made an account?"
            )
            TextButton(
                onClick = { navController.navigate(Routes.Signup.route) },
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Sign Up",
                    color = LightBlue,
                    fontFamily = inter,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController)
}