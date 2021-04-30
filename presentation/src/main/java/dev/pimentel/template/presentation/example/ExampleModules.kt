package dev.pimentel.template.presentation.example

import dev.pimentel.template.presentation.example.data.ExampleState
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
    fun provideInitialState(): ExampleState = ExampleState()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WelcomeStateQualifier
