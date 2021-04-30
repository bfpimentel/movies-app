package dev.pimentel.series.presentation.series

import dev.pimentel.series.presentation.series.data.SeriesState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object WelcomeViewModelModule {

    @Provides
    @ViewModelScoped
    @WelcomeStateQualifier
    fun provideInitialState(): SeriesState = SeriesState()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WelcomeStateQualifier
