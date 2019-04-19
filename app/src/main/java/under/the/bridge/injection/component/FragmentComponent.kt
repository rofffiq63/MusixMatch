package under.the.bridge.injection.component

import under.the.bridge.injection.PerFragment
import under.the.bridge.injection.module.FragmentModule
import dagger.Subcomponent

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent