package under.the.bridge.features.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup



class HomeAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private var currentFragment: Fragment? = null
    private val fragments = ArrayList<Fragment>()

    override fun getItem(p0: Int): Fragment {
        return fragments[p0]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        if (getCurrentFragment() !== `object`) {
            currentFragment = `object` as Fragment
        }
        super.setPrimaryItem(container, position, `object`)
    }

    fun getCurrentFragment(): Fragment? {
        return currentFragment
    }

    fun addFragment(baseFragment: Fragment) {
        fragments.add(baseFragment)
    }

}