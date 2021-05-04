package dev.pimentel.shows.presentation.information

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.pimentel.shows.presentation.information.data.InformationState
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object InformationViewModelModule {

    @Provides
    @ViewModelScoped
    @InformationStateQualifier
    fun provideInitialState(): InformationState = InformationState()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InformationStateQualifier
