package under.the.bridge.features.main.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.fragment_find.*

import under.the.bridge.R
import under.the.bridge.data.model.TrackArtist
import under.the.bridge.features.base.BaseFragment
import android.support.v7.widget.DividerItemDecoration
import android.view.inputmethod.InputMethodManager
import under.the.bridge.data.database.table.WishlistTable
import under.the.bridge.features.main.ItemAdapter
import under.the.bridge.features.main.MainActivity


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FindFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FindFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FindFragment : BaseFragment(), ItemAdapter.ClickListener {
    override val layout: Int
        get() = R.layout.fragment_find

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemAdapter = ItemAdapter()
        itemAdapter.setClickListener(this)
        resultList.adapter = itemAdapter
        resultList.layoutManager = LinearLayoutManager(context)

        val dividerItemDecoration = DividerItemDecoration(resultList.context,
                (resultList.layoutManager as LinearLayoutManager).orientation)
        resultList.addItemDecoration(dividerItemDecoration)

        searchBar.setOnFocusChangeListener { v, hasFocus ->
            if (v.id == R.id.searchBar && !hasFocus) {
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }

        searchBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                (context as MainActivity).getData(searchBar.text.toString())
                searchBar.clearFocus()
            }
            true
        }
    }

    override fun onItemClicked(item: TrackArtist.Message.Body.Track?) {
        var alertDialog = AlertDialog.Builder(context!!)

        var found = false
        var menus: Array<String>
        for (itemW in getDatabase()?.wishlist!!) {
            if (itemW.songId.equals(item?.track?.track_id)){
                found = true
                break
            }
        }

        if (!found) {
            menus = arrayOf("Add to Wishlist")
        } else {
            menus = arrayOf("Remove from Wishlist")
        }

        alertDialog.setItems(menus) { dialog, which ->
            when (menus[which]) {
                "Add to Wishlist" -> {
                    if (getDatabase()?.addWishlist(WishlistTable(
                                    item?.track?.track_id!!,
                                    item.track.artist_name,
                                    item.track.track_name))!!)
                        showToast("Added to Wishlist")
                }
                "Remove from Wishlist" -> {
                    if (getDatabase()?.removeWishlist(item?.track?.track_id!!)!!) {
                        showToast("Wishlist removed")
                    }
                }
                else -> {
                    showToast("Nothing selected")
                }
            }
            dialog.dismiss()
        }.show()
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun getDataSuccess(data: List<TrackArtist.Message.Body.Track>) {
        itemAdapter.addData(data)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FindFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FindFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
