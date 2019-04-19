package under.the.bridge.injection.component

import android.app.Application
import android.content.Context
import dagger.Component
import under.the.bridge.data.DataManager
import under.the.bridge.data.remote.ApiHandler
import under.the.bridge.injection.ApplicationContext
import under.the.bridge.injection.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun dataManager(): DataManager

    fun pokemonApi(): ApiHandler
}
