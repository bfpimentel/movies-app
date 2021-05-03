package dev.pimentel.shows.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.pimentel.shows.domain.repository.ShowsRepository
import dev.pimentel.shows.domain.usecase.FavoriteOrRemoveShow
import dev.pimentel.shows.domain.usecase.GetFavorites
import dev.pimentel.shows.domain.usecase.GetMoreShows
import dev.pimentel.shows.domain.usecase.GetShows
import dev.pimentel.shows.domain.usecase.SearchFavorites
import dev.pimentel.shows.domain.usecase.SearchShows

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
    fun provideFavoriteOrRemoveShow(showsRepository: ShowsRepository): FavoriteOrRemoveShow =
        FavoriteOrRemoveShow(showsRepository = showsRepository)

    @Provides
    @ViewModelScoped
    fun provideGetFavorites(showsRepository: ShowsRepository): GetFavorites =
        GetFavorites(showsRepository = showsRepository)

    @Provides
    @ViewModelScoped
    fun provideSearchFavorites(showsRepository: ShowsRepository): SearchFavorites =
        SearchFavorites(showsRepository = showsRepository)

    @Provides
    @ViewModelScoped
    fun provideSearchShows(showsRepository: ShowsRepository): SearchShows =
        SearchShows(showsRepository = showsRepository)
}
