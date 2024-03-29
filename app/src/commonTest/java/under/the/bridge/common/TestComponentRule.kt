package under.the.bridge.common

import under.the.bridge.MvpStarterApplication
import under.the.bridge.common.injection.component.DaggerTestComponent
import under.the.bridge.common.injection.component.TestComponent
import under.the.bridge.common.injection.module.ApplicationTestModule
import under.the.bridge.data.DataManager
import android.content.Context
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Test rule that creates and sets a Dagger TestComponent into the application overriding the
 * existing application component.
 * Use this rule in your test case in order for the app to use mock dependencies.
 * It also exposes some of the dependencies so they can be easily accessed from the tests, e.g. to
 * stub mocks etc.
 */
class TestComponentRule(val context: Context) : TestRule {

    val testComponent: TestComponent

    init {
        val application = MvpStarterApplication.get(context)
        testComponent = DaggerTestComponent.builder()
                .applicationTestModule(ApplicationTestModule(application))
                .build()
    }

    val mockDataManager: DataManager
        get() = testComponent.dataManager()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                val application = MvpStarterApplication.get(context)
                application.component = testComponent
                base.evaluate()
            }
        }
    }
}