package under.the.bridge.injection.module

import dagger.Module
import dagger.Provides
import under.the.bridge.data.remote.ApiHandler
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by shivam on 8/7/17.
 */
@Module(includes = arrayOf(NetworkModule::class))
class ApiModule {

    @Provides
    @Singleton
    internal fun providePokemonApi(retrofit: Retrofit): ApiHandler =
            retrofit.create(ApiHandler::class.java)
}