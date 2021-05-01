package dev.pimentel.series.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.pimentel.series.domain.repository.ShowsRepository
import dev.pimentel.series.domain.usecase.GetSeries
import dev.pimentel.series.domain.usecase.SearchSeries

@Module
@InstallIn(ViewModelComponent::class)
object DomainUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetSeries(showsRepository: ShowsRepository): GetSeries =
        GetSeries(seriesRepository = showsRepository)

    @Provides
    @ViewModelScoped
    fun provideSearchSeries(showsRepository: ShowsRepository): SearchSeries =
        SearchSeries(seriesRepository = showsRepository)
}
