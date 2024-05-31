package ar.edu.unlam.mobile.scaffolding.data.di

import ar.edu.unlam.mobile.scaffolding.data.network.FirestoreNetworkImpl
import ar.edu.unlam.mobile.scaffolding.data.network.FirestoreNetworkInterface
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {
    @Provides
    fun providesFirebaseFirestore() = Firebase.firestore

    @Provides
    fun FirestoreNetworkImpl(firestore: FirebaseFirestore): FirestoreNetworkInterface =
        FirestoreNetworkImpl(
            firebaseFirestore = firestore,
        )
}