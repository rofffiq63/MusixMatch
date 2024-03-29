package under.the.bridge.injection.module

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

import dagger.Module
import dagger.Provides
import under.the.bridge.injection.ActivityContext

@Module
class FragmentModule(private val mFragment: Fragment) {

    @Provides
    internal fun providesFragment(): Fragment {
        return mFragment
    }

    @Provides
    internal fun provideActivity(): FragmentActivity? {
        return mFragment.activity
    }

    @Provides
    @ActivityContext
    internal fun providesContext(): FragmentActivity? {
        return mFragment.activity
    }

}