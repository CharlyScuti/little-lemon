package com.tr3sco.littlelemon.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navHostController: NavHostController) {

    val mContext = LocalContext.current
    val userData = mContext.userData
    var firstName by remember { mutableStateOf(userData?.firstName ?: "") }
    var lastName by remember { mutableStateOf(userData?.lastName ?: "") }
    var email by remember { mutableStateOf(userData?.email ?: "") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header()
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
                    Toast.makeText(mContext, "Please enter all data.", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                mContext.saveUserData(
                    UserData(
                        firstName = firstName.trim(),
                        lastName = lastName.trim(),
                        email = email.trim()
                    )
                )
                Toast.makeText(mContext, "Ok", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(text = "Update")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            onClick = {
                mContext.clearUserData()
                navHostController.navigate(
                    route = Onboarding.route,
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(Home.route, inclusive = true)
                        .build()
                )
            }
        ) {
            Text(text = "Logout")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePreview() {
    Profile(rememberNavController())
}