package com.example.praktikum1n

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktikum1n.ui.theme.Praktikum1NTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListActivity : ComponentActivity() {

    var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Praktikum1NTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding).fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                        Title()
                        
                        Spacer(modifier = Modifier.padding(16.dp))
                        
                        getData()
                    }

                }
            }
        }
    }
}

@Composable
fun getData(){
    val matkuls = remember {
        mutableStateListOf<matkul>()
    }
    val matkulCollection = Firebase.firestore.collection("matkul")

    LaunchedEffect(Unit) {
        matkulCollection.get().addOnSuccessListener { result ->
            for(document in result){
                val matkul = document.toObject(matkul::class.java)
                matkuls.add(matkul)
            }
        }.addOnFailureListener { exception ->
            Log.w("error", "error", exception)
        }
    }
    display(matkuls = matkuls)
}

@Composable
fun display(matkuls: List<matkul>){
    LazyColumn {
        items(matkuls) { matkul ->
            displayCard(hari = matkul.hari, jam = matkul.jam, matkul = matkul.matkul, praktikum = matkul.praktikum.toString(), ruang = matkul.ruang)
        }
    }
}

@Composable
fun displayCard(hari: String, jam: String, matkul: String, praktikum: String, ruang: String){
    Card(Modifier.size(240.dp, 300.dp), colors = CardDefaults.cardColors(
        MaterialTheme.colorScheme.primary
    )){
        Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
            Row() {
                Text(text = "Hari:", Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                Text(text = hari, Modifier.padding(top = 16.dp, bottom = 16.dp))
            }
            Row() {
                Text(text = "Jam: ", Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                Text(text = jam, Modifier.padding(top = 16.dp, bottom = 16.dp))
            }
            Row() {
                Text(text = "Matkul: ", Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                Text(text = matkul, Modifier.padding(top = 16.dp, bottom = 16.dp))
            }
            Row() {
                Text(text = "Praktikum: ", Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                Text(text = praktikum.toString(), Modifier.padding(top = 16.dp, bottom = 16.dp))
            }
            Row() {
                Text(text = "Ruang: ", Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                Text(text = ruang, Modifier.padding(top = 16.dp, bottom = 16.dp))
            }
        }
    }
}