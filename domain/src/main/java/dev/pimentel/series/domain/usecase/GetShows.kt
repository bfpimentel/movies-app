package dev.pimentel.series.domain.usecase

import dev.pimentel.series.domain.entity.Show
import dev.pimentel.series.domain.entity.ShowsPage
import dev.pimentel.series.domain.repository.ShowsRepository
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
                    imageUrl = showsModel.imageUrl
                )
            },
            nextPage = showsPageModel.nextPage
        )
    }

    companion object {
        const val NO_MORE_PAGES = -1
    }
}
