package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.repository.ShowsRepository

class SearchFavorites(
    private val showsRepository: ShowsRepository
) : SuspendedUseCase<SearchFavorites.Params, Unit> {

    override suspend fun invoke(params: Params) = showsRepository.searchFavorites(query = params.query)

    data class Params(val query: String)
}
