package com.tr3sco.littlelemon.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tr3sco.littlelemon.R

@Composable
fun Home(navHostController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Header logo",
            modifier = Modifier
                .size(width = 200.dp, height = 80.dp)
                .align(Alignment.Center)
        )
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Header logo",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 10.dp)
                .size(50.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
                .clickable {
                    navHostController.navigate(Profile.route)
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    Home(rememberNavController())
}