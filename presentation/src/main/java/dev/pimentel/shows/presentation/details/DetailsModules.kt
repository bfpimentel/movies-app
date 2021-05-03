package dev.pimentel.shows.presentation.details

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.pimentel.shows.presentation.details.data.DetailsState
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object DetailsModules {

    @Provides
    @ViewModelScoped
    @DetailsStateQualifier
    fun provideInitialDetailsState(): DetailsState = DetailsState()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DetailsStateQualifier
