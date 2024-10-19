package com.example.praktikum1n

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.praktikum1n.ui.theme.Praktikum1NTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class GithubProfile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Praktikum1NTheme {
                Surface(Modifier.fillMaxSize()) {
                    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {

                        var data by remember {
                            mutableStateOf(UserItem())
                        } 
                        
                        val scope = rememberCoroutineScope()
                        
                        LaunchedEffect(key1 = true) {
                            scope.launch(Dispatchers.IO) {
                                val response = try {
                                    RetrofitInstance.getApiService().getDetailUser2()
                                }catch (e: HttpException) {
                                    Toast.makeText(this@GithubProfile, "Error GET", Toast.LENGTH_SHORT).show()
                                    return@launch
                                }catch (e: IOException) {
                                    Toast.makeText(this@GithubProfile, "Error APP", Toast.LENGTH_SHORT).show()
                                    return@launch
                                }
                                if(response.isSuccessful && response.body() != null){
                                    withContext(Dispatchers.Main){
                                        data = response.body()!!
                                    }
                                }else{

                                }
                            }
                        }
                        Display(data = data)
                    }
                }
            }
        }
    }
}

@Composable
fun GithubProfileComp() {
    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {

            var data by remember {
                mutableStateOf(UserItem())
            }

            val scope = rememberCoroutineScope()

            LaunchedEffect(key1 = true) {
                scope.launch(Dispatchers.IO) {
                    val response = try {
                        RetrofitInstance.getApiService().getDetailUser2()
                    }
                    catch (e: HttpException) {
//                        Toast.makeText(this@Column, "Error GET", Toast.LENGTH_SHORT).show()
                        return@launch
                    }catch (e: IOException) {
//                        Toast.makeText(this@GithubProfile, "Error APP", Toast.LENGTH_SHORT).show()
                        return@launch
                    }
                    if(response.isSuccessful && response.body() != null){
                        withContext(Dispatchers.Main){
                            data = response.body()!!
                        }
                    }else{

                    }
                }
            }
            Display(data = data)
        }
    }
}

@Composable
fun Display(data: UserItem){
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(model = data.avatarUrl, contentDescription = "Github" )
        }

        Spacer(modifier = Modifier.padding(16.dp))
        var bordersize = 220.dp
        Column(
            Modifier
                .fillMaxWidth()
                .height(200.dp), verticalArrangement =  Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = data.login,
                Modifier
                    .width(bordersize)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.Blue), textAlign = TextAlign.Center)
            Text(text = data.name,
                Modifier
                    .width(bordersize)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.Blue), textAlign = TextAlign.Center)

            Text(text = "Followers: " + data.followers.toString(),
                Modifier
                    .width(bordersize)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.Blue), textAlign = TextAlign.Center)

            Text(text = "Following: " + data.following.toString(),
                Modifier
                    .width(bordersize)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.Blue), textAlign = TextAlign.Center)
        }
    }
}

