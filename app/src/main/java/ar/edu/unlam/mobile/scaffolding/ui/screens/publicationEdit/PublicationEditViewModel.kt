package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationEdit

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.domain.services.FirestoreService
import ar.edu.unlam.mobile.scaffolding.domain.services.StorageService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetCurrentUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

// /problema con las imagenes , no las elimina
@HiltViewModel
class PublicationEditViewModel
    @Inject
    constructor(
        private val storageService: StorageService,
        private val getUser: GetCurrentUser,
        private val firestoreService: FirestoreService,
    ) : ViewModel() {
        private var currentUserId: String? = null

        @Suppress("ktlint:standard:backing-property-naming")
        private val _listImagesForUser = mutableStateOf<List<Bitmap>>(emptyList())

        val listImageUser: State<List<Bitmap>> = _listImagesForUser

        private val _listBitmapToCameraX = mutableStateOf<List<Bitmap>>(emptyList())
        val listBitmapToCameraX: State<List<Bitmap>> = _listBitmapToCameraX

        init {
            viewModelScope.launch {
                currentUserId = getCurrentUser()
            }
        }

        fun addBitmapToList(bitmap: Bitmap) {
            _listBitmapToCameraX.value += bitmap
        }

        fun resetListBitmapToCamareX() {
            _listBitmapToCameraX.value = emptyList()
        }

        fun takePhoto(
            cameraController: LifecycleCameraController,
            context: Context,
        ) {
            cameraController.takePicture(
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageCapturedCallback() {
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
                        Toast.makeText(context, "Foto tomada", Toast.LENGTH_SHORT).show()
                        addBitmapToList(rotatedBitmap)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        super.onError(exception)
                        Log.e("Camera", "Couldn't take photo: ", exception)
                    }
                },
            )
        }

        fun addImage(imageBitmap: Bitmap) {
            _listImagesForUser.value += imageBitmap
        }

        fun deleteImage(imageBitmap: Bitmap) {
            _listImagesForUser.value = _listImagesForUser.value.filterNot { it == imageBitmap }
        }

        private suspend fun getCurrentUser(): String? {
            return getUser.getCurrentUser()?.uid
        }

        fun hasRequirePermission(
            permisssion: Array<String>,
            context: Context,
        ): Boolean {
            return permisssion.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        }

        fun resetListOfImages() {
            _listImagesForUser.value = emptyList()
        }

        private val _publicationState = MutableStateFlow<Result<PostWithImages>?>(null)
        val publicationState: StateFlow<Result<PostWithImages>?> get() = _publicationState

        var isEditing = mutableStateOf(false)

        private val _id = mutableStateOf("")
        val id: State<String> = _id

        private val _type = mutableStateOf("")
        val type: State<String> = _type

        private val _title = mutableStateOf("")
        val title: State<String> = _title

        private val _description = mutableStateOf("")
        val description: State<String> = _description

        private val _dateLost = MutableLiveData<String>()
        val dateLost: LiveData<String> get() = _dateLost
        private val _species = mutableStateOf("")
        val species: State<String> = _species

        private val _sex = mutableStateOf("")
        val sex: State<String> = _sex

        private val _age = mutableStateOf("")
        val age: State<String> = _age

        private val _color = mutableStateOf("")
        val color: State<String> = _color

        private val _location = mutableStateOf("")
        val location: State<String> = _location

        private val _contact = mutableStateOf("")
        val contact: State<String> = _contact

        private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        fun setIsEditing() {
            isEditing.value = !isEditing.value
        }

        fun setId(value: String) {
            _id.value = value
        }

        fun setNewId() {
            _id.value = UUID.randomUUID().toString()
        }

        fun setType(value: String) {
            _type.value = value
        }

        fun setTitle(value: String) {
            _title.value = value
        }

        fun setDescription(value: String) {
            _description.value = value
        }

        fun setDateLost(value: String) {
            val formattedDate: String =
                try {
                    dateFormat.parse(value)?.let { dateFormat.format(it) } ?: dateFormat.format(Date())
                } catch (e: ParseException) {
                    dateFormat.format(Date()) // Si hay un error al analizar, devuelve la fecha actual como una cadena
                }
            Log.d("PublicationViewModel", "Setting date lost to: $formattedDate")
            _dateLost.value = formattedDate
        }

        fun setSpecies(value: String) {
            _species.value = value
        }

        fun setSex(value: String) {
            _sex.value = value
        }

        fun setAge(value: String) {
            _age.value = value
        }

        fun setColor(value: String) {
            _color.value = value
        }

        fun setLocation(value: String) {
            _location.value = value
        }

        fun setContact(value: String) {
            _contact.value = value
        }

        fun validateForm(): Boolean {
            return title.value.isNotEmpty() &&
                description.value.isNotEmpty() &&
                location.value.isNotEmpty() &&
                type.value.isNotEmpty() &&
                age.value.isNotEmpty() &&
                contact.value.isNotEmpty() &&
                (dateLost.value?.isNotEmpty() == true) // &&
            species.value.isNotEmpty() &&
                sex.value.isNotEmpty() &&
                color.value.isNotEmpty()
        }

/*
        fun validateLocation(): Boolean {
            _isErrorLocation.value = location.value.isEmpty()
            return isErrorLocation.value
        }
*/
        fun addNewPublication() {
            viewModelScope.launch {
                try {
                    val imageUrls =
                        uploadImagesToStorage(
                            listImageUser.value,
                            currentUserId!!,
                            id.value,
                        )

                    val postWithImages = createPostWithImage(imageUrls)
                    firestoreService.addPublicationToPublicationCollection(postWithImages).collect { result ->
                        firestoreService.addPublication(currentUserId!!, postWithImages)
                            .collect { result ->
                                _publicationState.value = Result.success(result)
                            }
                    }
                } catch (e: Exception) {
                    Log.e("PublicationEditViewModel", "Failed to add publication", e)
                    _publicationState.value = Result.failure(e)
                }
            }
        }

        // manejar en caso de que traiga un null o que no pueda
        suspend fun setPublication(idPublication: String) {
            firestoreService.getPublicationById(idPublication).collect { result ->
                isEditing.value = true
                setId(result.id)
                setType(result.type)
                setTitle(result.title)
                setDescription(result.description)
                val dateLostString =
                    when (val dateLost = result.dateLost) {
                        is String -> {
                            // Parsear la cadena de texto a un objeto Date
                            val date =
                                try {
                                    dateFormat.parse(dateLost)
                                } catch (e: ParseException) {
                                    Date() // Si hay un error al analizar, devuelve la fecha actual
                                }
                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date) // Formatear Date como una cadena de texto
                        }
                        else -> dateFormat.format(Date()) // Valor predeterminado si no es una cadena de texto
                    }
                setDateLost(dateLostString)
                setDateLost(dateLostString)
                setSpecies(result.species)
                setSex(result.sex)
                setAge((result.age).toString())
                setColor(result.color)
                setLocation(result.location)
                setContact(result.contact.toString())
                // urlImages
                viewModelScope.launch {
                    storageService.getAllImagesForPublication(currentUserId!!, idPublication)
                        .collect { result ->
                            if (result.isEmpty()) {
                                Log.e("Storage", "la lista esta vacia ")
                            } else {
                                _listImagesForUser.value = result
                            }
                        }
                }
            }
        }

        private suspend fun uploadImagesToStorage(
            images: List<Bitmap>,
            idUser: String,
            idPublication: String,
        ): List<String> {
            return images.map { imageBitmap ->
                storageService.uploadImage(image = imageBitmap, userId = idUser, publicationId = idPublication)
            }
        }

        private fun createPostWithImage(urls: List<String>): PostWithImages {
            return PostWithImages(
                id = id.value,
                type = type.value,
                title = title.value,
                description = description.value,
                dateLost = dateLost.value ?: "",
                species = species.value,
                sex = sex.value,
                age = age.value.toInt(),
                color = color.value,
                location = location.value,
                contact = contact.value.toInt(),
                images = urls, // Lista de URLs
            )
        }

        // /aca lo debemos eliminar la publicacion xq no se pisa , y luego añadir
        fun addEditPublicationToFirestore() {
            viewModelScope.launch {
                try {
                    storageService.deletePublicationImages(currentUserId!!, id.value)
                    val urls =
                        uploadImagesToStorage(
                            listImageUser.value,
                            currentUserId!!,
                            id.value,
                        )
                    resetListOfImages() // reseteamos devuelta las lista de imagenes para que luego
                    val newPostWithImages = createPostWithImage(urls)
                    firestoreService.editPublicationInAllPublications(id.value, newPostWithImages).collect {
                            result ->
                        firestoreService.editPublicationForUser(
                            currentUserId!!,
                            id.value,
                            newPostWithImages,
                        ).collect {
                                result ->
                            _publicationState.value = Result.success(result)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Edit Publication", "Failed upload to Firestore edit Publication")
                }
            }
        }
    }
// /usar el componente de loading
// /manejar bien las excepciones por si no podemos subir , traer o editar publicaciones para que en la ui se muestre algun msj
