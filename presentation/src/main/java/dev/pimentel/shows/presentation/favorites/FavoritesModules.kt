package dev.pimentel.shows.presentation.favorites

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.pimentel.shows.presentation.favorites.data.FavoritesState
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object FavoritesViewModelModule {

    @Provides
    @ViewModelScoped
    @FavoritesStateQualifier
    fun provideInitialState(): FavoritesState = FavoritesState()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FavoritesStateQualifier
