package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationEdit

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.PetColors
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.domain.models.Sex
import ar.edu.unlam.mobile.scaffolding.domain.models.Species
import ar.edu.unlam.mobile.scaffolding.domain.models.UserInfoGoogle
import ar.edu.unlam.mobile.scaffolding.domain.services.FirestoreService
import ar.edu.unlam.mobile.scaffolding.domain.services.StorageService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetCurrentUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PublicationEditViewModel
    @Inject
    constructor(
        private val storageService: StorageService,
        private val getUser: GetCurrentUser,
        private val firestoreService: FirestoreService,
        context: Context,
    ) : ViewModel() {
        private var currentUserId: UserInfoGoogle? = null

        @Suppress("ktlint:standard:backing-property-naming")
        private val _listImagesForUser = mutableStateOf<List<Bitmap>>(emptyList())

        val listImageForPublication: State<List<Bitmap>> = _listImagesForUser

        init {
            viewModelScope.launch {
                currentUserId = getCurrentUser()
            }
        }

        fun addImage(imageBitmap: Bitmap) {
            _listImagesForUser.value += imageBitmap
        }

        fun deleteImage(imageBitmap: Bitmap) {
            _listImagesForUser.value = _listImagesForUser.value.filterNot { it == imageBitmap }
        }

        private suspend fun getCurrentUser(): UserInfoGoogle? {
            val firebaseUser = getUser.getCurrentUser()
            return if (firebaseUser != null) {
                UserInfoGoogle(
                    userId = firebaseUser.uid,
                    displayName = firebaseUser.displayName,
                    email = firebaseUser.email,
                    photoUrl = firebaseUser.photoUrl.toString(),
                )
            } else {
                null
            }
        }

        fun hasRequirePermission(
            permisssion: Array<String>,
            context: Context,
        ): Boolean {
            return permisssion.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        }

        fun createFile(context: Context): String {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
            return file.absolutePath
        }

        fun captureImage(
            context: Context,
            cameraLauncher: ActivityResultLauncher<Intent>,
            file: File,
        ) {
            val photoUri = FileProvider.getUriForFile(context, "ar.edu.unlam.mobile.scaffolding.fileprovider", file)
            val cameraIntent =
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                    putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                }
            cameraLauncher.launch(cameraIntent)
        }

        fun resetListOfImages() {
            _listImagesForUser.value = emptyList()
        }

        private val _publicationState = MutableStateFlow<Result<PostWithImages>?>(null)
        val publicationState: StateFlow<Result<PostWithImages>?> get() = _publicationState

        private var isEditing = mutableStateOf(false)

        // Mantiene el resultado de la publicación que estás editando
        private val _setterPublication = MutableStateFlow<Result<PostWithImages>?>(null)
        val setterPublication: StateFlow<Result<PostWithImages>?> get() = _setterPublication

        private val _id = mutableStateOf("")
        val id: State<String> = _id

        private val _type = mutableStateOf("")
        val type: State<String> = _type

        private val _title = mutableStateOf("")
        val title: State<String> = _title

        private val _description = mutableStateOf("")
        val description: State<String> = _description

        private val _dateLost = mutableStateOf("")
        val dateLost: State<String> = _dateLost

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

        private fun setId(value: String) {
            _id.value = value
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

        fun validateForm() {
            // Comprueba si todos los campos obligatorios están llenos
            if (title.value.isNotEmpty() &&
                description.value.isNotEmpty() &&
                location.value.isNotEmpty() &&
                type.value.isNotEmpty() &&
                age.value.isNotEmpty() &&
                contact.value.isNotEmpty()
            ) {
                // Establece un ID único para la publicación si no existe
                if (id.value.isEmpty()) {
                    setId(UUID.randomUUID().toString())
                }

                // Establece valores predeterminados para ciertos campos si están vacíos
                if (dateLost.value.isEmpty()) {
                    setDateLost(dateFormat.format(Date())) // Devuelve la fecha actual como una cadena
                }
                if (species.value.isEmpty()) {
                    setSpecies(Species.LORO.toString())
                }
                if (sex.value.isEmpty()) {
                    setSex(Sex.MACHO.toString())
                }
                if (color.value.isEmpty()) {
                    setColor(PetColors.MARRON.toString())
                }

                // Comprueba si se está editando o agregando una publicación
                if (isEditing.value) {
                    // editPublication()
                } else {
                    currentUserId?.let { addPublication(it.userId) }
                }
            } else {
                // Realizar componente para avisar de posibles faltantes datos
            }
        }

        private fun addPublication(idUser: String) {
            viewModelScope.launch {
                try {
                    val bitmapList: List<Bitmap> = listImageForPublication.value
                    val imageUrls =
                        bitmapList.map { bitmap ->
                            async { storageService.uploadImage(bitmap, idUser, id.value) }
                        }
                    val urls = imageUrls.awaitAll() // Asegura que esperemos a todas las subidas de imágenes
                    val postWithImages =
                        PostWithImages(
                            id = id.value,
                            type = type.value,
                            title = title.value,
                            description = description.value,
                            dateLost = dateLost.value,
                            species = species.value,
                            sex = sex.value,
                            age = age.value.toInt(),
                            color = color.value,
                            location = location.value,
                            contact = contact.value.toInt(),
                            images = urls, // Lista de URLs
                        )
                    firestoreService.addPublicationToPublicationCollection(postWithImages).collect { result ->
                        firestoreService.addPublication(idUser, postWithImages).collect { result ->
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

                setId(result.id)
                setTitle(result.title)
                setColor(result.color)
                setContact(result.contact.toString())
                setAge(result.age.toString())
                setDescription(result.description)
                setDateLost(result.dateLost)
                setLocation(result.location)
                setSex(result.sex)
                setSpecies(result.species)
                // /nos traemos las imagenes del storage y la seteamos
                storageService.getAllImagesForPublication(currentUserId!!.userId, idPublication).collect { result ->
                    _listImagesForUser.value = result
                }
                // esto no es necesario
                _setterPublication.value = Result.success(result)
                isEditing.value = true // Actualiza la variable isEditing
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
                dateLost = dateLost.value,
                species = species.value,
                sex = sex.value,
                age = age.value.toInt(),
                color = color.value,
                location = location.value,
                contact = contact.value.toInt(),
                images = urls, // Lista de URLs
            )
        }

        private suspend fun addEditPublicationToFirestore() {
            viewModelScope.launch {
                try {
                    // tenemos que eliminar primero las imagenes que esta
                    storageService.deletePublicationImages(currentUserId!!.userId, id.value)
                    val urls = uploadImagesToStorage(listImageForPublication.value, currentUserId!!.userId, id.value)
                    val newPostWithImages = createPostWithImage(urls)
                    firestoreService.addPublicationToPublicationCollection(newPostWithImages).collect { result ->
                        firestoreService.addPublication(currentUserId!!.userId, newPostWithImages).collect { result ->
                            _publicationState.value = Result.success(result)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Edit Publication", "Failed upload to Firestore edit Publication")
                }
            }
        }
    }
