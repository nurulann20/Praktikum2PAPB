package com.example.praktikum1n

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktikum1n.ui.theme.Praktikum1NTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Praktikum1NTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Title()

                        var text by remember { mutableStateOf("")}
                        var print by remember { mutableStateOf("")}

                        Column (){
                            OutlinedTextField(
                                value = text,
                                onValueChange = {text = it},
                                modifier = Modifier.padding(bottom = 12.dp),
                                label = { Text("Masukkan kata")},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )

                            Button(onClick = {
                                print = text
                            }) {
                                Text("Submit")
                            }

                            Spacer(modifier = Modifier.padding(bottom = 24.dp))
                            
                            if(print.isNotBlank()){
                                Text(print, style = MaterialTheme.typography.titleLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Title(){
    Column() {
        Text("Praktikum 2", style = MaterialTheme.typography.displayLarge, modifier = Modifier.padding(bottom = 24.dp))
        Text("Nurul Annisa Murnastiti", style = MaterialTheme.typography.titleLarge)
        Text("235150209111008")
    }
}