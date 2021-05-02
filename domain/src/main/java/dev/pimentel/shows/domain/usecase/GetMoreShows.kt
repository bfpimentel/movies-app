package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.repository.ShowsRepository

class GetMoreShows(private val showsRepository: ShowsRepository) : SuspendedUseCase<GetMoreShows.Params, Unit> {

    override suspend fun invoke(params: Params) = showsRepository.getMoreShows(nextPage = params.nextPage)

    data class Params(val nextPage: Int)
}
