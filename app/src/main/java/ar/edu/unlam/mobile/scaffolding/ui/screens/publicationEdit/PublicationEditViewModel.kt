package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationEdit

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PublicationEditViewModel
    @Inject
    constructor(context: Context) : ViewModel() {
        private val signInClient = Identity.getSignInClient(context)

        fun hasRequirePermission(
            camerXPermisssion: Array<String>,
            context: Context,
        ): Boolean {
            return camerXPermisssion.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        }
    }
