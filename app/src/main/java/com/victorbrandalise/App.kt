package com.victorbrandalise

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import androidx.camera.core.Preview as CameraPreview

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

            val requestPermissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { _ -> } // We can ignore this because [cameraPermissionState] updates

            LaunchedEffect(cameraPermissionState) {
                if (
                    !cameraPermissionState.status.isGranted &&
                    cameraPermissionState.status.shouldShowRationale
                ) {
                    // Show rationale if needed
                } else {
                    requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                }
            }

            if (cameraPermissionState.status.isGranted) {
                CameraPreview()
            } else {
                NoCameraPermission()
            }
        }
    }
}

@Composable
fun CameraPreview() {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    // Preview use case
    val preview = CameraPreview.Builder().build()
    val previewView = remember { PreviewView(context) }

    // Image capture use case
    val imageCapture = remember { ImageCapture.Builder().build() }

    LaunchedEffect(lensFacing) {
        val cameraProvider = ProcessCameraProvider.getInstance(context).get()
        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
        preview.surfaceProvider = previewView.surfaceProvider
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            { previewView },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun NoCameraPermission() {
    Text("You need to give camera permission")
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    App()
}