package com.example.praktikum1n

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCbrt
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.praktikum1n.composable.CameraPreviewScreen
import com.example.praktikum1n.composable.CameraUtil.takePicture
import com.example.praktikum1n.ui.theme.Praktikum1NTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Room : ComponentActivity() {
    private val db by lazy { MatkulDatabase.getDatabase(this).matkulsDao() }

    private val viewmodel by viewModels<MatkulsViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MatkulsViewModel(dao = db) as T
                }
            }
        }
    )

    private val _isCameraPermissionGranted = MutableStateFlow(false)
    val isCameraPermissionGranted: StateFlow<Boolean> = _isCameraPermissionGranted

    private val cameraPermissionRequestLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if(isGranted) {
                _isCameraPermissionGranted.value = true
            } else {
                Toast.makeText(
                    this,
                    "Camera Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    fun handleCameraPermission(){
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                _isCameraPermissionGranted.value = true
            }

            else -> {
                cameraPermissionRequestLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Praktikum1NTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val datalist = viewmodel.getAll().collectAsState(initial = emptyList())
                    var matkul by remember {
                        mutableStateOf("")
                    }
                    var tugas by remember {
                        mutableStateOf("")
                    }
                    var isdone by remember {
                        mutableStateOf(false)
                    }

                    val permissionGranted = isCameraPermissionGranted.collectAsState().value

                    var image by remember {
                        mutableStateOf<Uri?>(null)
                    }

                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)){
                        OutlinedTextField(value = matkul, onValueChange = { matkul = it }, label = { Text(text = "Masukkan Matkul")})
                        OutlinedTextField(value = tugas, onValueChange = { tugas = it }, label = { Text(text = "Masukkan Tugas")})
                        Spacer(modifier = Modifier.padding(16.dp))
                        Button(onClick = { viewmodel.AddData(matkul,tugas,false) }) {
                            Text(text = "Add Matkul")
                        }

                        Spacer(modifier = Modifier.padding(all = 20.dp))
//                        if(image != null){
//                            Image(painter = rememberAsyncImagePainter(model = image), contentDescription = null)
//                        }
//
//                        Spacer(modifier = Modifier.padding(all = 20.dp))
//
//                        if(permissionGranted){
//                            image = CameraScreen()
//                        }
//                        Button(onClick = {
//                            handleCameraPermission()
//                        },
//                            modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                            Text(text = "Start Capture")
//                        }
//
//                        Spacer(modifier = Modifier.padding(all = 20.dp))

                        LazyColumn(
                            Modifier
                                .fillMaxSize()
                                .padding(all = 16.dp)) {
                            items(datalist.value){item ->
                                Row {
                                    Column {
                                        Text(text = item.namaMatkul)
                                        Text(text = item.tugas)
                                    }
                                    Spacer(modifier = Modifier.padding(all = 20.dp))
                                    Button(onClick = { viewmodel.AddData(item.namaMatkul, item.tugas, true)}) {
                                        Icon(if (item.isDone) Icons.Filled.Done else Icons.Filled.Clear, contentDescription = "isDone")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CameraScreen() : Uri? {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val executor = remember {
        Executors.newSingleThreadExecutor()
    }
    val capturedImageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            bindToLifecycle(lifecycleOwner)
        }
    }

    Box {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    scaleType = PreviewView.ScaleType.FILL_START
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    controller = cameraController
                }
            },
            Modifier.fillMaxSize(),
            onRelease = {
                cameraController.unbind();
            }
        )

        Button(onClick = {
            takePicture(cameraController, context, executor, {uri: Uri ->
                capturedImageUri.value = uri
            }, { exception ->

            })
        },
            modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(text = "Take A Picture")
        }
    }

   return capturedImageUri.value
}


@Composable
fun DatabaseListView(vm: MatkulsViewModel, image: Uri){
    val datalist = vm.getAll().collectAsState(initial = emptyList())
    var matkul by remember {
        mutableStateOf("")
    }
    var tugas by remember {
        mutableStateOf("")
    }
    var isdone by remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 50.dp)){
        OutlinedTextField(value = matkul, onValueChange = { matkul = it }, label = { Text(text = "Masukkan Matkul")})
        OutlinedTextField(value = tugas, onValueChange = { tugas = it }, label = { Text(text = "Masukkan Tugas")})
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = { vm.AddData(matkul,tugas,false) }) {
            Text(text = "Add Matkul")
        }

        Spacer(modifier = Modifier.padding(all = 20.dp))

        Button(onClick = {

        }) {

        }

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(all = 16.dp)) {
            items(datalist.value){item ->
                Row {
                    Column {
                        Text(text = item.namaMatkul)
                        Text(text = item.tugas)
                    }
                    Spacer(modifier = Modifier.padding(all = 20.dp))
                    Button(onClick = { vm.AddData(item.namaMatkul, item.tugas, true)}) {
                        Icon(if (item.isDone) Icons.Filled.Done else Icons.Filled.Clear, contentDescription = "isDone")
                    }
                }
            }
        }
    }
}
