package under.the.bridge.features.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import under.the.bridge.R
import under.the.bridge.features.login.LoginActivity
import under.the.bridge.features.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(this)

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
        }, 5000)
    }
}
