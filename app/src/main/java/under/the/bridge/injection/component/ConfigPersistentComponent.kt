package under.the.bridge.injection.component

import dagger.Component
import under.the.bridge.features.base.BaseActivity
import under.the.bridge.features.base.BaseFragment
import under.the.bridge.injection.ConfigPersistent
import under.the.bridge.injection.module.ActivityModule
import under.the.bridge.injection.module.FragmentModule

/**
 * A dagger component that will live during the lifecycle of an Activity or Fragment but it won't
 * be destroy during configuration changes. Check [BaseActivity] and [BaseFragment] to
 * see how this components survives configuration changes.
 * Use the [ConfigPersistent] scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = arrayOf(AppComponent::class))
interface ConfigPersistentComponent {

    fun activityComponent(activityModule: ActivityModule): ActivityComponent

    fun fragmentComponent(fragmentModule: FragmentModule): FragmentComponent

}
