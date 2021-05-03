package dev.pimentel.shows.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dev.pimentel.shows.shared.dispatchers.AppDispatchersProvider
import dev.pimentel.shows.shared.dispatchers.DispatchersProvider
import dev.pimentel.shows.shared.navigator.Navigator
import dev.pimentel.shows.shared.navigator.NavigatorBinder
import dev.pimentel.shows.shared.navigator.NavigatorImpl
import dev.pimentel.shows.shared.navigator.NavigatorRouter
import dev.pimentel.shows.shared.shows.ShowViewDataMapper
import dev.pimentel.shows.shared.shows.ShowViewDataMapperImpl
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationApplicationModule {

    @Provides
    @Singleton
    fun provideDispatchersProvider(): DispatchersProvider = AppDispatchersProvider()

    @Provides
    @Singleton
    fun provideNavigator(
        dispatchersProvider: DispatchersProvider
    ): Navigator = NavigatorImpl(dispatchersProvider = dispatchersProvider)

    @NavigatorBinderQualifier
    @Provides
    fun provideNavigatorBinder(navigator: Navigator): NavigatorBinder = navigator

    @NavigatorRouterQualifier
    @Provides
    fun provideNavigatorRouter(navigator: Navigator): NavigatorRouter = navigator
}

@Module
@InstallIn(ViewModelComponent::class)
object PresentationMapperModule {

    @Provides
    @ViewModelScoped
    fun provideShowViewDataMapper(@ApplicationContext context: Context): ShowViewDataMapper =
        ShowViewDataMapperImpl(context = context)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NavigatorBinderQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NavigatorRouterQualifier
