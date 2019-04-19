package under.the.bridge.features.main

import android.content.Intent
import android.net.Uri
import under.the.bridge.R
import under.the.bridge.features.base.BaseActivity
import under.the.bridge.features.common.ErrorView
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import butterknife.BindView
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import under.the.bridge.data.model.TrackArtist
import under.the.bridge.features.main.fragments.CalendarFragment
import under.the.bridge.features.main.fragments.CreateFragment
import under.the.bridge.features.main.fragments.FindFragment
import under.the.bridge.features.main.fragments.WishlistFragment
import under.the.bridge.features.splash.SplashActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView, ViewPager.OnPageChangeListener, ErrorView.ErrorListener,
        FindFragment.OnFragmentInteractionListener,
        WishlistFragment.OnFragmentInteractionListener,
        CalendarFragment.OnFragmentInteractionListener,
        CreateFragment.OnFragmentInteractionListener {

    @Inject
    lateinit var mMainPresenter: MainPresenter

    lateinit var homeAdapter: HomeAdapter

    @BindView(R.id.view_error)
    @JvmField
    var mErrorView: ErrorView? = null
    @BindView(R.id.progress)
    @JvmField
    var mProgress: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mMainPresenter.attachView(this)

        setSupportActionBar(toolbar)

        homeAdapter = HomeAdapter(supportFragmentManager)
        homeAdapter.addFragment(FindFragment())
        homeAdapter.addFragment(WishlistFragment())
        homeAdapter.addFragment(CalendarFragment())

        fragmentContainer.adapter = homeAdapter
        fragmentContainer.offscreenPageLimit = 4

        fragmentContainer.addOnPageChangeListener(this)

        bottomNav.menu.add(Menu.NONE, 0, 0, "Find").setIcon(R.mipmap.ic_launcher)
        bottomNav.menu.add(Menu.NONE, 1, 1, "Wishlist").setIcon(R.mipmap.ic_launcher)
        bottomNav.menu.add(Menu.NONE, 2, 2, "Calendar").setIcon(R.mipmap.ic_launcher)
        bottomNav.menu.add(Menu.NONE, 3, 3, "Logout").setIcon(R.mipmap.ic_launcher)

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.order) {
                0, 1, 2 -> {
                    toolbar.title = it.title
                    true
                }
                3 -> {
                    startActivity(Intent(this, SplashActivity::class.java))
                    finishAffinity()
                    false
                }
                else -> {
                    toolbar.title = "Main"
                    true
                }
            }
        }

        bottomNav.setupWithViewPager(fragmentContainer, true)
        bottomNav.setTextVisibility(true)
        bottomNav.enableAnimation(false)
        bottomNav.enableShiftingMode(false)
        bottomNav.enableItemShiftingMode(false)

        mErrorView?.setErrorListener(this)
    }

    override fun onPageScrollStateChanged(p0: Int) {

    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

    }

    var currentPosition: Int = 0

    override fun onPageSelected(p0: Int) {
        val fragmentToShow = homeAdapter.getItem(p0)
        fragmentToShow.onResume()

        val fragmentToHide = homeAdapter.getItem(currentPosition)
        fragmentToHide.onPause()

        currentPosition = p0
    }

    override val layout: Int
        get() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        mMainPresenter.detachView()
    }

    override fun getDataSuccess(data: List<TrackArtist.Message.Body.Track>) {
        (homeAdapter.getCurrentFragment() as FindFragment).getDataSuccess(data)
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progress?.visibility = View.VISIBLE
            view_error?.visibility = View.INVISIBLE
        } else {
            progress?.visibility = View.INVISIBLE
        }
    }

    override fun showError(error: Throwable) {
        mErrorView?.visibility = View.VISIBLE
        Timber.e(error, "There was an error retrieving the pokemon")
    }

    override fun onReloadData() {
        mMainPresenter.getData(search)
    }

    private lateinit var search: String

    fun getData(search: String) {
        this.search = search
        mMainPresenter.getData(search)
    }

    override fun onFragmentInteraction(uri: Uri) {

    }

    companion object {

        private val POKEMON_COUNT = 20
    }
}