package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationEdit

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.location.Address
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.domain.services.FirestoreService
import ar.edu.unlam.mobile.scaffolding.domain.services.StorageService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetCurrentUser
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

sealed interface PublicationUiState {
    object Success : PublicationUiState

    object Loading : PublicationUiState

    object Error : PublicationUiState
}

@HiltViewModel
class PublicationEditViewModel
    @Inject
    constructor(
        private val storageService: StorageService,
        private val getUser: GetCurrentUser,
        private val firestoreService: FirestoreService,
        private val context: Context,
    ) : ViewModel() {
        private var currentUserId: String? = null

        @Suppress("ktlint:standard:backing-property-naming")
        private val _listImagesForUser = mutableStateOf<List<Bitmap>>(emptyList())

        val listImageUser: State<List<Bitmap>> = _listImagesForUser

        private val _listBitmapToCameraX = mutableStateOf<List<Bitmap>>(emptyList())
        val listBitmapToCameraX: State<List<Bitmap>> = _listBitmapToCameraX

        private val _publicationUiState = mutableStateOf<PublicationUiState>(PublicationUiState.Success)
        val publicationUiState: State<PublicationUiState> = _publicationUiState

        private val _snackbar = mutableStateOf(false)
        val snackbar: State<Boolean> = _snackbar

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

        private suspend fun getCurrentUser(): String? = getUser.getCurrentUser()?.uid

        fun hasRequirePermission(
            permisssion: Array<String>,
            context: Context,
        ): Boolean =
            permisssion.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }

        fun resetListOfImages() {
            _listImagesForUser.value = emptyList()
        }

        private val _isEditing = mutableStateOf(false)
        var isEditing: State<Boolean> = _isEditing

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

        private val _isErrorTitle = mutableStateOf(false)
        val isErrorTitle: State<Boolean> get() = _isErrorTitle

        private val _isErrorDescription = mutableStateOf(false)
        val isErrorDescription: State<Boolean> get() = _isErrorDescription

        private val _isErrorAge = mutableStateOf(false)
        val isErrorAge: State<Boolean> get() = _isErrorAge

        private val _isErrorSex = mutableStateOf(false)
        val isErrorSex: State<Boolean> get() = _isErrorSex

        private val _isErrorType = mutableStateOf(false)
        val isErrorType: State<Boolean> get() = _isErrorType

        private val _isErrorColor = mutableStateOf(false)
        val isErrorColor: State<Boolean> get() = _isErrorColor

        private val _isErrorSpecies = mutableStateOf(false)
        val isErrorSpecies: State<Boolean> get() = _isErrorSpecies

        private val _isErrorDateLost = mutableStateOf(false)
        val isErrorDateLost: State<Boolean> get() = _isErrorDateLost

        private val _isErrorContact = mutableStateOf(false)
        val isErrorContact: State<Boolean> get() = _isErrorContact

        private val _isErrorLocation = mutableStateOf(false)
        val isErrorLocation: State<Boolean> get() = _isErrorLocation

        private val _address = MutableStateFlow("")
        val address = _address.asStateFlow()

        private val _geocodedLocation = MutableStateFlow<LatLng?>(null)
        val geocodedLocation = _geocodedLocation.asStateFlow()

        private var _cameraCenterLocation = MutableStateFlow<LatLng?>(null)
        val cameraCenterLocation = _cameraCenterLocation.asStateFlow()

        fun setIsEditing(value: Boolean) {
            _isEditing.value = value
        }

        fun setType(value: String) {
            _type.value = value
            validateType()
            // clearErrorType()
        }

        private fun clearErrorType() {
            _isErrorType.value = false
        }

        fun setId(value: String) {
            _id.value = value
        }

        fun setNewId() {
            _id.value = UUID.randomUUID().toString()
        }

        fun setTitle(value: String) {
            _title.value = value
            validateTitle()
        }

        fun setDescription(value: String) {
            _description.value = value
            validateDescription()
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
            // clearErrorDateLost()
            validateDateLost()
        }

        private fun clearErrorDateLost() {
            _isErrorDateLost.value = false
        }

        fun setSpecies(value: String) {
            _species.value = value
            validateSpecies()
        }

        fun setSex(value: String) {
            _sex.value = value
            validateSex()
        }

        fun setAge(value: String) {
            _age.value = value
            validateAge()
        }

        fun setColor(value: String) {
            _color.value = value
            validateColor()
        }

        fun onAddressChange(value: String) {
            _address.value = value
//            geocodeAddress(value)
            validateLocation()
        }

        fun setContact(value: String) {
            _contact.value = value
            validateContact()
        }

        fun validateTitle(): Boolean {
            _isErrorTitle.value = title.value.isEmpty()
            return isErrorTitle.value
        }

        fun setSnackbar(value: Boolean) {
            _snackbar.value = value
        }

        fun validateDescription(): Boolean {
            _isErrorDescription.value = description.value.isEmpty()
            return isErrorDescription.value
        }

        fun validateType(): Boolean {
            _isErrorType.value = type.value.isEmpty()
            return isErrorType.value
        }

        var messageErrorAge = ""

        fun validateAge(): Boolean {
            _isErrorAge.value =
                when {
                    age.value.isEmpty() -> {
                        messageErrorAge = "Campo requerido"
                        true
                    }

                    !age.value.isDigitsOnly() -> {
                        messageErrorAge = "Ingresar caracteres númericos"
                        true
                    }

                    else -> false
                }
            return isErrorAge.value
        }

        var messageErrorContact = ""

        fun validateContact(): Boolean {
            _isErrorContact.value =
                when {
                    contact.value.isEmpty() -> {
                        messageErrorContact = "Campo requerido"
                        true
                    }

                    !contact.value.isDigitsOnly() -> {
                        messageErrorContact = "Ingresar caracteres númericos"
                        true
                    }

                    else -> false
                }
            return isErrorContact.value
        }

        fun validateLocation(): Boolean {
            _isErrorLocation.value = geocodedLocation.value == null
            return isErrorLocation.value
        }

        fun validateDateLost(): Boolean {
            _isErrorDateLost.value = dateLost.value.isNullOrBlank()
            return isErrorDateLost.value
        }

        fun validateSpecies(): Boolean {
            _isErrorSpecies.value = species.value.isEmpty()
            return isErrorSpecies.value
        }

        fun validateSex(): Boolean {
            _isErrorSex.value = sex.value.isEmpty()
            return isErrorSex.value
        }

        fun validateColor(): Boolean {
            _isErrorColor.value = color.value.isEmpty()
            return isErrorColor.value
        }

        fun validateForm(): Boolean =
            validateTitle() ||
                validateDescription() ||
                validateType() ||
                validateSpecies() ||
                validateDateLost() ||
                validateSex() ||
                validateLocation() ||
                validateContact() ||
                validateColor() ||
                validateAge() ||
                validateLocation()

        suspend fun addNewPublication() {
            _publicationUiState.value = PublicationUiState.Loading
            try {
                val imageUrls = uploadImagesToStorage(listImageUser.value, currentUserId!!, id.value)
                val postWithImages = createPostWithImage(imageUrls)
                firestoreService
                    .addPublicationToPublicationCollection(postWithImages)
                    .collect { result ->
                        firestoreService.addPublication(currentUserId!!, postWithImages).collect { result ->
                            Log.d("Add Publication", "Publication added successfully")
                        }
                    }
                _publicationUiState.value = PublicationUiState.Success
            } catch (e: Exception) {
                Log.e("PublicationEditViewModel", "Failed to add publication", e)
                _publicationUiState.value = PublicationUiState.Error
            }
        }

        fun setPublication(idPublication: String) {
            _publicationUiState.value = PublicationUiState.Loading
            viewModelScope.launch {
                try {
                    fetchPublicationData(idPublication = idPublication)
                    _publicationUiState.value = PublicationUiState.Success
                } catch (e: Exception) {
                    Log.e("SetPublication", "no se pudo setear la publicacion")
                    _publicationUiState.value = PublicationUiState.Error
                }
            }
        }

        private fun fetchPublicationData(idPublication: String) {
            viewModelScope.launch {
                firestoreService.getPublicationById(idPublication).collect { result ->
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
                                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
                            }

                            else -> dateFormat.format(Date()) // Valor predeterminado si no es una cadena de texto
                        }
                    setDateLost(dateLostString)
                    setSpecies(result.species)
                    setSex(result.sex)
                    setAge((result.age).toString())
                    setColor(result.color)
                    onAddressChange(result.location)
                    setContact(result.contact.toString())
                    storageService.getAllImagesFromUrl(result.images).collect { result ->
                        if (result.isEmpty()) {
                            Log.e("Storage", "get imges from url faile")
                        } else {
                            _listImagesForUser.value = result
                        }
                    }
                }
            }
        }

        suspend fun geocodeAddress(address: String) {
            Log.d("PublicationEditViewModel", "Geocoding address: $address")
            withContext(Dispatchers.IO) {
                try {
                    val geocoder = Geocoder(context)
                    val addresses: MutableList<Address>? = geocoder.getFromLocationName(address, 1)
                    if (addresses != null && addresses.isNotEmpty()) {
                        val location = addresses[0]
                        _geocodedLocation.value = LatLng(location.latitude, location.longitude)
                        Log.d(
                            "PublicationEditViewModel",
                            "Geocoded location: ${_geocodedLocation.value}",
                        )
                    } else {
                        _geocodedLocation.value = null
                        Log.d("PublicationEditViewModel", "Geocoded location is null")
                    }
// / For later implementation this would be the way to do it
//                    geocoder.getFromLocationName(address, 1) { addresses ->
//                        if (addresses.isNotEmpty()) {
//                            val location = addresses[0]
//                            _geocodedLocation.value = LatLng(location.latitude, location.longitude)
//                            Log.d(
//                                "PublicationEditViewModel",
//                                "Geocoded location: ${_geocodedLocation.value}",
//                            )
//                        } else {
//                            _geocodedLocation.value = null
//                            Log.d("PublicationEditViewModel", "Geocoded location is null")
//                        }
//                    }
                } catch (e: Exception) {
                    Log.e("PublicationEditViewModel", "Failed to geocode address", e)
                }
            }
        }

        // con este cambio esperariamos a que termine de subir las imagenes para luego mostrarlas en la pantalla
        private suspend fun uploadImagesToStorage(
            images: List<Bitmap>,
            idUser: String,
            idPublication: String,
        ): List<String> {
            _publicationUiState.value = PublicationUiState.Loading
            var imageUrls = mutableListOf<String>()
            try {
                for (imageBitmap in images) {
                    val url =
                        storageService.uploadImage(
                            image = imageBitmap,
                            userId = idUser,
                            publicationId = idPublication,
                        )
                    imageUrls.add(url)
                }
            } catch (e: Exception) {
                Log.e("UploadImages", "Failed upload to Storage")
                _publicationUiState.value = PublicationUiState.Error
            }

            return imageUrls
        }

        private fun createPostWithImage(urls: List<String>): PostWithImages =
            PostWithImages(
                id = id.value,
                type = type.value,
                title = title.value,
                description = description.value,
                dateLost = dateLost.value ?: "",
                species = species.value,
                sex = sex.value,
                age = age.value.toInt(),
                color = color.value,
                location = address.value,
                contact = contact.value.toInt(),
                images = urls,
                locationLat = geocodedLocation.value?.latitude ?: 0.0,
                locationLng = geocodedLocation.value?.longitude ?: 0.0,
            )

        suspend fun addEditPublicationToFirestore() {
            _publicationUiState.value = PublicationUiState.Loading

            try {
                storageService.deletePublicationImages(currentUserId!!, id.value)

                val urls = uploadImagesToStorage(listImageUser.value, currentUserId!!, id.value)
                Log.d("UploadImages", "Uploaded image URL: $urls")
                val newPostWithImages = createPostWithImage(urls)
                firestoreService.editPublicationInAllPublications(id.value, newPostWithImages).collect { result ->
                    firestoreService
                        .editPublicationForUser(
                            currentUserId!!,
                            id.value,
                            newPostWithImages,
                        ).collect { result ->
                            Log.d("Edit Publication", "Publication edited successfully")
                        }
                }
                _publicationUiState.value = PublicationUiState.Success
            } catch (e: Exception) {
                Log.e("Edit Publication", "Failed upload to Firestore edit Publication")
                _publicationUiState.value = PublicationUiState.Error
            }
        }

        fun getAllImagesFromUrl(list: List<String>) {
            viewModelScope.launch {
                storageService.getAllImagesFromUrl(list).collect { list ->
                    _listImagesForUser.value = list
                }
            }
        }
    }
