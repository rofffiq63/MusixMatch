package under.the.bridge.features.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.tree.rh.googlelib.HiGoogle
import com.tree.rh.googlelib.OnLoginListener
import kotlinx.android.synthetic.main.activity_login.*
import under.the.bridge.R
import under.the.bridge.features.base.BaseActivity
import under.the.bridge.features.main.MainActivity

class LoginActivity : BaseActivity(), FacebookCallback<LoginResult>, OnLoginListener {
    override val layout: Int
        get() = R.layout.activity_login

    var callbackManager: CallbackManager? = null
    var hiGoogle: HiGoogle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callbackManager = CallbackManager.Factory.create()
        fbButton.registerCallback(callbackManager, this)

        hiGoogle = HiGoogle(this, this)

        fbLogin?.setOnClickListener {
            fbButton.performClick()
        }

        googleLogin?.setOnClickListener {
            hiGoogle?.signIn(this)
        }
    }

    override fun onSuccess(result: LoginResult?) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCancel() {

    }

    override fun onError(error: FacebookException?) {
        showToast(error?.message!!)
    }

    override fun onSuccess(p0: GoogleSignInAccount?) {
        showToast(p0?.displayName!!)
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onFailed(p0: String?) {
        showToast(p0!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        hiGoogle?.fromActivityResult(requestCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
