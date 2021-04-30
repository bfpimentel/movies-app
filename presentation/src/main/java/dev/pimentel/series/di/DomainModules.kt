package dev.pimentel.series.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.pimentel.series.domain.repository.ExampleRepository
import dev.pimentel.series.domain.usecase.GetExample

@Module
@InstallIn(ViewModelComponent::class)
object DomainUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetExample(exampleRepository: ExampleRepository): GetExample =
        GetExample(repository = exampleRepository)
}
