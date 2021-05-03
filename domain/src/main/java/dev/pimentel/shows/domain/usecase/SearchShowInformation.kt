package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.repository.ShowsRepository

class SearchShowInformation(
    private val showsRepository: ShowsRepository
) : SuspendedUseCase<SearchShowInformation.Params, Unit> {

    override suspend fun invoke(params: Params) = showsRepository.searchShowInformation(showId = params.showId)

    data class Params(val showId: Int)
}
