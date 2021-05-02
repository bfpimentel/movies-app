package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.repository.ShowsRepository

class FavoriteShow(private val showsRepository: ShowsRepository) : SuspendedUseCase<FavoriteShow.Params, Unit> {

    override suspend fun invoke(params: Params) = showsRepository.favoriteShow(showId = params.showId)

    data class Params(val showId: Int)
}
