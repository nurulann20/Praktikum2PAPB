package com.example.praktikum1n

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktikum1n.ui.theme.Praktikum1NTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginScreen : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

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

                        var passwordToggle: Boolean by remember {
                            mutableStateOf(false)
                        }

//                        var printName by remember { mutableStateOf("") }
//                        var printNim by remember { mutableStateOf("") }

                        val isFormFilled =
                            name.isNotBlank() && nim.length == 15 && nim.all { it.isDigit() }

                        Column {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                modifier = Modifier.padding(bottom = 12.dp),
                                label = { Text("Masukkan Email") },
                            )

                            OutlinedTextField(
                                value = nim,
                                visualTransformation = if(passwordToggle) VisualTransformation.None else PasswordVisualTransformation(),
                                leadingIcon = {
                                    IconButton(onClick = {
                                        passwordToggle = !passwordToggle
                                    }) {
                                        Icon(imageVector = Icons.Filled.Face, contentDescription = "Visibility")
                                    }
                                },
                                onValueChange = { nim = it },
                                modifier = Modifier.padding(bottom = 12.dp),
                                label = { Text("Masukkan Password") },
                            )

                            Button(
                                onClick = {
                                    auth.signInWithEmailAndPassword(name, nim).addOnCompleteListener { task ->
                                        if(task.isSuccessful){
                                            Intent(applicationContext, MainActivity::class.java).also {
                                                startActivity(it)
                                            }
                                        }else {
                                            Log.w("Error", "SignInFailed", task.exception)
                                            Toast.makeText(
                                                baseContext,
                                                "Email atau Password anda Salah",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text("Login")
                            }

                            Spacer(modifier = Modifier.padding(bottom = 24.dp))
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
        Text("Praktikum 7", style = MaterialTheme.typography.displayLarge, modifier = Modifier.padding(bottom = 24.dp))
        Text("Nurul Annisa Murnastiti", style = MaterialTheme.typography.titleLarge)
        Text("235150209111008")
    }
}