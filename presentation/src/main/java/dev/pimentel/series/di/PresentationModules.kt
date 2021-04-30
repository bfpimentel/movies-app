package dev.pimentel.series.di

import dev.pimentel.series.shared.dispatchers.AppDispatchersProvider
import dev.pimentel.series.shared.dispatchers.DispatchersProvider
import dev.pimentel.series.shared.navigator.Navigator
import dev.pimentel.series.shared.navigator.NavigatorBinder
import dev.pimentel.series.shared.navigator.NavigatorImpl
import dev.pimentel.series.shared.navigator.NavigatorRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NavigatorBinderQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NavigatorRouterQualifier
