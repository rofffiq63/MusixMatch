package under.the.bridge.common.injection.component

import dagger.Component
import under.the.bridge.common.injection.module.ApplicationTestModule
import under.the.bridge.injection.component.AppComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationTestModule::class))
interface TestComponent : AppComponent