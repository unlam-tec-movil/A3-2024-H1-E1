package ar.edu.unlam.mobile.scaffolding.ui.screens.cameraScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.widget.Toast
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CameraViewModel
    @Inject
    constructor() : ViewModel() {
        private val listPhotoBitmap =
            MutableStateFlow<List<Bitmap>>(
                emptyList(),
            )
        val listBitmaps = listPhotoBitmap.asStateFlow()

        fun addPhotoList(photoTake: Bitmap) {
            listPhotoBitmap.value += photoTake
        }

        fun takePhoto(
            cameraController: LifecycleCameraController,
            context: Context,
            onPhotoTake: (Bitmap) -> Unit,
        )  {
            cameraController.takePicture(
                ContextCompat.getMainExecutor(context),
                object : OnImageCapturedCallback() {
                    override fun onCaptureSuccess(image: ImageProxy) {
                        super.onCaptureSuccess(image)

                        val matrix =
                            Matrix().apply {
                                postRotate(image.imageInfo.rotationDegrees.toFloat())
                            }

                        val rotatedBitmap =
                            Bitmap.createBitmap(
                                image.toBitmap(),
                                0,
                                0,
                                image.width,
                                image.height,
                                matrix,
                                true,
                            )
                        Toast.makeText(context, "foto tomada", Toast.LENGTH_SHORT).show()

                        onPhotoTake(rotatedBitmap)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        super.onError(exception)
                        Toast.makeText(context, "no se pudo tomar la foto", Toast.LENGTH_SHORT).show()
                    }
                },
            )
        }
        // agregar la funcion para subir una foto a firebase
    }
