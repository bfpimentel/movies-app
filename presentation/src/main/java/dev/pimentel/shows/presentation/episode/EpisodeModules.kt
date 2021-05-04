package dev.pimentel.shows.presentation.episode

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.pimentel.shows.presentation.episode.data.EpisodeState
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object EpisodeViewModelModule {

    @Provides
    @ViewModelScoped
    @EpisodeStateQualifier
    fun provideInitialState(): EpisodeState = EpisodeState()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EpisodeStateQualifier
