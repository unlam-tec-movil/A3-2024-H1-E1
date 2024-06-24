package ar.edu.unlam.mobile.scaffolding.domain.di.useCases

import ar.edu.unlam.mobile.scaffolding.data.repository.FirestoreRepositoryInterface
import ar.edu.unlam.mobile.scaffolding.domain.services.FirestoreService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetMarkersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetMarkersUseCase(firestoreRepository: FirestoreRepositoryInterface): GetMarkersUseCase {
        return FirestoreService(firestoreRepository)
    }
}
