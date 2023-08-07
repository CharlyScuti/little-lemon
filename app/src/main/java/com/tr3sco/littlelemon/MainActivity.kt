package com.tr3sco.littlelemon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.tr3sco.littlelemon.ui.MyNavigation
import com.tr3sco.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }
    private val database by lazy {
        Room.databaseBuilder(applicationContext, MenuDatabase::class.java, "menu.db").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            LittleLemonTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyNavigation(navHostController = navController)
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().getAllMenuItems().value?.isEmpty() != false) {
                val menuNetwork = getMenu()
                menuNetwork.menu.forEach {
                    database.menuItemDao().saveMenuItem(
                        MenuItem(
                            id = it.id,
                            title = it.title,
                            description = it.description,
                            price = it.price,
                            image = it.image,
                            category = it.category
                        )
                    )
                }
            }
        }
    }

    private suspend fun getMenu(): MenuNetwork {
        return httpClient
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body()
    }
}