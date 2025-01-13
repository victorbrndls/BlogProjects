package com.victorbrandalise

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.extensions.ExtensionMode
import androidx.camera.extensions.ExtensionsManager
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
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
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val resolutionSelector = remember {
        ResolutionSelector.Builder()
            .setAspectRatioStrategy(AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY)
            .build()
    }

    // Preview use case
    val previewView = remember { PreviewView(context) }
    val preview = remember {
        CameraPreview.Builder()
            .setResolutionSelector(resolutionSelector)
            .build()
    }

    // Image capture use case
    val imageCapture = remember {
        ImageCapture.Builder()
            .setResolutionSelector(resolutionSelector)
            .setPostviewEnabled(true)
            .setPostviewResolutionSelector(resolutionSelector)
            .build()
    }

    LaunchedEffect(Unit) {
        bindCamera(context, lifecycleOwner, preview, imageCapture, previewView)
    }

    CameraContainer(previewView, imageCapture)
}

@Composable
private fun CameraContainer(
    previewView: PreviewView,
    imageCapture: ImageCapture,
) {
    var captureProgress by remember { mutableIntStateOf(0) }
    var previewBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var finalBitmap by remember { mutableStateOf<Bitmap?>(null) }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        previewBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Preview",
                modifier = Modifier.fillMaxSize()
            )
        } ?: AndroidView(
            { previewView },
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            if (captureProgress > 0) {
                Text(
                    "Don't move your camera ($captureProgress%)",
                    color = Color.White,
                    modifier = Modifier
                        .padding(2.dp)
                        .background(Color.Black)
                )
            } else {
                Button(
                    onClick = {
                        imageCapture.takePicture(
                            Dispatchers.Default.asExecutor(),
                            object : ImageCapture.OnImageCapturedCallback() {
                                override fun onCaptureSuccess(image: ImageProxy) {
                                    captureProgress = 0
                                    previewBitmap = null

                                    finalBitmap = image.toBitmap().rotate(90f)
                                    image.close()
                                }

                                override fun onPostviewBitmapAvailable(bitmap: Bitmap) {
                                    previewBitmap = bitmap
                                }

                                override fun onCaptureProcessProgressed(progress: Int) {
                                    captureProgress = progress
                                }

                                override fun onError(exception: ImageCaptureException) {
                                    Log.e("CameraPreview", "Error capturing image", exception)
                                    captureProgress = 0
                                    previewBitmap = null
                                }
                            }
                        )
                    }
                ) {
                    Text("Take picture")
                }
            }
        }

        finalBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Final",
                modifier = Modifier.fillMaxSize()
            )
            Button(
                onClick = { finalBitmap = null }
            ) {
                Text("Take another picture")
            }
        }
    }
}

private fun bindCamera(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    preview: CameraPreview,
    imageCapture: ImageCapture,
    previewView: PreviewView
) {
    val cameraProvider = ProcessCameraProvider.getInstance(context).get()
    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    val extensionsManager = ExtensionsManager.getInstanceAsync(context, cameraProvider).get()

    cameraProvider.unbindAll()

    try {
        if (extensionsManager.isExtensionAvailable(cameraSelector, ExtensionMode.NIGHT)) {
            val nightCameraSelector = extensionsManager.getExtensionEnabledCameraSelector(
                cameraSelector, ExtensionMode.NIGHT
            )

            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                nightCameraSelector,
                preview,
                imageCapture
            )
        } else {
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        }

        preview.surfaceProvider = previewView.surfaceProvider
    } catch (e: Exception) {
        Log.e("CameraPreview", "Error binding camera", e)
    }
}

@Composable
fun NoCameraPermission() {
    Text("You need to give camera permission")
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    App()
}