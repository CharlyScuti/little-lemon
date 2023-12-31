package com.tr3sco.littlelemon.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.tr3sco.littlelemon.R
import com.tr3sco.littlelemon.ui.theme.LittleLemonTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navHostController: NavHostController) {

    val mContext = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header()
        Text(
            text = "Let's get to know you",
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(10.dp)
        )

        Text(
            text = "Personal information",
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            onClick = {
                if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                    Toast.makeText(mContext, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                mContext.saveUserData(
                    UserData(
                        firstName = firstName.trim(),
                        lastName = lastName.trim(),
                        email = email.trim()
                    )
                )
                navHostController.navigate(
                    route = Home.route,
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(Onboarding.route, inclusive = true)
                        .build()
                )
            }
        ) {
            Text(text = "Register")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingPreview() {
    val navController = rememberNavController()
    LittleLemonTheme {
        Onboarding(navController)
    }
}

@Composable
fun Header() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Header logo",
            modifier = Modifier.size(width = 200.dp, height = 80.dp)
        )
    }
}