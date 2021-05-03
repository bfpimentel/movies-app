package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.repository.ShowsRepository

class FavoriteOrRemoveShow(
    private val showsRepository: ShowsRepository
) : SuspendedUseCase<FavoriteOrRemoveShow.Params, Unit> {

    override suspend fun invoke(params: Params) = showsRepository.favoriteOrRemoveShow(showId = params.showId)

    data class Params(val showId: Int)
}
