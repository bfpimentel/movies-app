package dev.pimentel.template.di

import dev.pimentel.template.shared.dispatchers.AppDispatchersProvider
import dev.pimentel.template.shared.dispatchers.DispatchersProvider
import dev.pimentel.template.shared.navigator.Navigator
import dev.pimentel.template.shared.navigator.NavigatorBinder
import dev.pimentel.template.shared.navigator.NavigatorImpl
import dev.pimentel.template.shared.navigator.NavigatorRouter
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
