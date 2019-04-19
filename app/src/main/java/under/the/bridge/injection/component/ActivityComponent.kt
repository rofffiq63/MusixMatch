package under.the.bridge.injection.component

import under.the.bridge.injection.PerActivity
import under.the.bridge.injection.module.ActivityModule
import under.the.bridge.features.base.BaseActivity
import under.the.bridge.features.detail.DetailActivity
import under.the.bridge.features.main.MainActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(detailActivity: DetailActivity)
}
