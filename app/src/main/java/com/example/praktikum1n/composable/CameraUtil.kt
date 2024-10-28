package com.example.praktikum1n.composable

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import com.example.praktikum1n.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

object CameraUtil {
    fun takePicture(
        cameraController: CameraController,
        context: Context,
        executor: ExecutorService,
        onImageCaptured: (Uri) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ){
        val photoFile = createPhotoFile(context)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        cameraController.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Uri.fromFile(photoFile).let(onImageCaptured)
                }

                override fun onError(exception: ImageCaptureException) {
                    onError(exception)
                }
            }
        )
    }

    private fun createPhotoFile(context: Context): File {
        val outputDirectory = getOutputDirectory(context)
        return File(outputDirectory, photoFileName()).apply {
            parentFile?.mkdirs()
        }
    }

    private fun photoFileName() = SimpleDateFormat("yyyy-MM-dd-mm-ss-SSS", Locale.US).format(System.currentTimeMillis()) + ".jpg"

    private fun getOutputDirectory(context: Context): File {
        val mediaDir = context.getExternalFilesDir(null)?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
    }
}