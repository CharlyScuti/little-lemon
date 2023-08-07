package com.tr3sco.littlelemon.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.tr3sco.littlelemon.MenuDatabase
import com.tr3sco.littlelemon.MenuItem
import com.tr3sco.littlelemon.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navHostController: NavHostController) {
    val mContext = LocalContext.current
    val database by lazy {
        Room.databaseBuilder(mContext, MenuDatabase::class.java, "menu.db").build()
    }
    val databaseMenuItems by database.menuItemDao().getAllMenuItems().observeAsState(emptyList())
    val categories = databaseMenuItems.map { it.category }.toSet().let {
        val list = it.toMutableList()
        list.add(0, "all")
        list.toList()
    }
    var selectedCategory by remember { mutableStateOf(categories.firstOrNull() ?: "all") }
    var searchPhrase by remember { mutableStateOf("") }
    val menuItems = databaseMenuItems.filter {
        val bySearch = if (searchPhrase.isBlank()) true else it.title.contains(searchPhrase.trim(), true)
        val byCategory = if (selectedCategory == "all") true else it.category.equals(selectedCategory, true)
        bySearch && byCategory
    }

    Column {
        TopAppBar(navHostController)
        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.title),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(
                        text = stringResource(id = R.string.location),
                        fontSize = 24.sp,
                    )
                    Text(
                        text = stringResource(id = R.string.description),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(top = 20.dp, end = 20.dp)
                            .fillMaxWidth(0.6f)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = "Upper Panel Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            OutlinedTextField(
                value = searchPhrase,
                label = { Text(text = "Enter search phrase") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                onValueChange = {
                    searchPhrase = it
                }
            )
        }
        Text(
            text = stringResource(id = R.string.order_for_delivery).uppercase(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp, start = 12.dp, end = 12.dp)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 6.dp, end = 6.dp)
        ) {
            items(
                items = categories,
                itemContent = { category ->
                    Card(
                        border = if (selectedCategory == category) BorderStroke(2.dp, Color.Blue) else null,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .clickable {
                                selectedCategory = category
                            }
                    ) {
                        Text(
                            text = category.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            )
        }
        Divider(
            modifier = Modifier.padding(top = 20.dp, start = 12.dp, end = 12.dp),
            thickness = 1.dp,
        )
        MenuItemsList(items = menuItems)
    }
}

@Composable
fun TopAppBar(navHostController: NavHostController) {
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

@Composable
private fun MenuItemsList(items: List<MenuItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        items(
            items = items,
            itemContent = { menuItem ->
                MenuRowItem(menuItem = menuItem)
            }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuRowItem(menuItem: MenuItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            Text(
                text = menuItem.title,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = menuItem.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
                    .fillMaxWidth(.75f)
            )
            Text(
                text = "$${menuItem.price}",
                style = MaterialTheme.typography.titleMedium,
            )
        }
        GlideImage(
            model = menuItem.image,
            contentDescription = "Image for ${menuItem.title}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(10.dp))
        )
    }
    Divider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        thickness = 1.dp,
    )
}


@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    Home(rememberNavController())
}