package under.the.bridge.injection.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import under.the.bridge.injection.ApplicationContext

@Module(includes = arrayOf(ApiModule::class))
class AppModule(private val mApplication: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }
}