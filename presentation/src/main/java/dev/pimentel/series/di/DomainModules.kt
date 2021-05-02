package dev.pimentel.series.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.pimentel.series.domain.repository.ShowsRepository
import dev.pimentel.series.domain.usecase.GetMoreShows
import dev.pimentel.series.domain.usecase.GetShows
import dev.pimentel.series.domain.usecase.SearchShows

@Module
@InstallIn(ViewModelComponent::class)
object DomainUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetShows(showsRepository: ShowsRepository): GetShows =
        GetShows(showsRepository = showsRepository)

    @Provides
    @ViewModelScoped
    fun provideGetMoreShows(showsRepository: ShowsRepository): GetMoreShows =
        GetMoreShows(showsRepository = showsRepository)

    @Provides
    @ViewModelScoped
    fun provideSearchShows(showsRepository: ShowsRepository): SearchShows =
        SearchShows(showsRepository = showsRepository)
}
