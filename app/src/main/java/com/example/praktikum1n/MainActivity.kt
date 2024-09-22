package com.example.praktikum1n

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Title()

                        var name by remember { mutableStateOf("") }
                        var nim by remember { mutableStateOf("") }

                        var printName by remember { mutableStateOf("") }
                        var printNim by remember { mutableStateOf("") }

                        val isFormFilled = name.isNotBlank() && nim.length == 15 && nim.all { it.isDigit() }

                        Column {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                modifier = Modifier.padding(bottom = 12.dp),
                                label = { Text("Masukkan Nama") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )

                            OutlinedTextField(
                                value = nim,
                                onValueChange = { nim = it },
                                modifier = Modifier.padding(bottom = 12.dp),
                                label = { Text("Masukkan NIM") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )

                            Button(
                                onClick = {
                                    if (isFormFilled) {
                                        printName = name
                                        printNim = nim
                                    }
                                },
                                enabled = isFormFilled,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isFormFilled) MaterialTheme.colorScheme.primary else Color.Gray
                                )
                            ) {
                                Text("Submit")
                            }

                            Spacer(modifier = Modifier.padding(bottom = 24.dp))

                            if (printName.isNotBlank() && printNim.isNotBlank()) {
                                Text("Nama: $printName", style = MaterialTheme.typography.titleLarge)
                                Text("NIM: $printNim", style = MaterialTheme.typography.titleLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Title() {
    Column {
        Text("Praktikum 4", style = MaterialTheme.typography.displayLarge, modifier = Modifier.padding(bottom = 24.dp))
        Text("Nurul Annisa Murnastiti", style = MaterialTheme.typography.titleLarge)
        Text("235150209111008")
    }
}
