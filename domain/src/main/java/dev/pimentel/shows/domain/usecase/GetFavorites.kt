package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.entity.Show
import dev.pimentel.shows.domain.repository.ShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavorites(private val showsRepository: ShowsRepository) : UseCase<NoParams, Flow<List<Show>>> {

    override fun invoke(params: NoParams): Flow<List<Show>> = showsRepository.getFavoriteShows().map { shows ->
        shows.map { showsModel ->
            Show(
                id = showsModel.id,
                name = showsModel.name,
                status = showsModel.status,
                premieredDate = showsModel.premieredDate,
                rating = showsModel.rating,
                imageUrl = showsModel.imageUrl,
                isFavorite = showsModel.isFavorite
            )
        }
    }
}
