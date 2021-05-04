package dev.pimentel.shows.domain.usecase

import dev.pimentel.shows.domain.entity.Show
import dev.pimentel.shows.domain.entity.ShowsPage
import dev.pimentel.shows.domain.repository.ShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetShows(private val showsRepository: ShowsRepository) : UseCase<NoParams, Flow<ShowsPage>> {

    override fun invoke(params: NoParams): Flow<ShowsPage> = showsRepository.getShows().map { showsPageModel ->
        ShowsPage(
            shows = showsPageModel.shows.map { showsModel ->
                Show(
                    id = showsModel.id,
                    name = showsModel.name,
                    status = showsModel.status,
                    premieredDate = showsModel.premieredDate,
                    rating = showsModel.rating,
                    imageUrl = showsModel.imageUrl,
                    isFavorite = showsModel.isFavorite
                )
            },
            nextPage = showsPageModel.nextPage
        )
    }

    companion object {
        const val NO_MORE_PAGES = -1
    }
}
