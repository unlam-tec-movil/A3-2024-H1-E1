package ar.edu.unlam.mobile.scaffolding.domain.di

import ar.edu.unlam.mobile.scaffolding.data.repository.AuthRepository
import ar.edu.unlam.mobile.scaffolding.domain.services.AuthService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetCurrentUser
import ar.edu.unlam.mobile.scaffolding.domain.usecases.SignInWithGoogle
import ar.edu.unlam.mobile.scaffolding.domain.usecases.SignOut
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthProviderRepository {
    @Provides
    fun AuthProviderRep(authRepository: AuthRepository): AuthService {
        return AuthService(authRepository = authRepository)
    }

    @Provides
    fun provideGetCurrentUserUseCase(authService: AuthService): GetCurrentUser {
        return authService
    }

    @Provides
    fun provideSignInWithGoogleUseCase(authService: AuthService): SignInWithGoogle {
        return authService
    }

    @Provides
    fun provideSignOutUseCase(authService: AuthService): SignOut {
        return authService
    }
}
