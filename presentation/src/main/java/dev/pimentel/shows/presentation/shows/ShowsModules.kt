package dev.pimentel.shows.presentation.shows

import dev.pimentel.shows.presentation.shows.data.ShowsState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object ShowsModules {

    @Provides
    @ViewModelScoped
    @ShowsStateQualifier
    fun provideInitialState(): ShowsState = ShowsState()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ShowsStateQualifier
