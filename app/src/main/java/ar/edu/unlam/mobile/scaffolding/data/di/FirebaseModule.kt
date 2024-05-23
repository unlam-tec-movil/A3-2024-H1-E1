package ar.edu.unlam.mobile.scaffolding.data.di

import ar.edu.unlam.mobile.scaffolding.data.network.AuthNetworkImpl
import ar.edu.unlam.mobile.scaffolding.data.network.AuthNetworkInterface
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun providesFirebaseAuth() = Firebase.auth

    @Provides
    fun providerAuthNetworkImpl(auth: FirebaseAuth): AuthNetworkInterface =
        AuthNetworkImpl(
            firebaseAuth = auth,
        )
}
