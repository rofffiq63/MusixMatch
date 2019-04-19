package under.the.bridge.features.main.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_wishlist.*

import under.the.bridge.R
import under.the.bridge.data.model.TrackArtist
import under.the.bridge.features.base.BaseFragment
import under.the.bridge.features.main.ItemAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [WishlistFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [WishlistFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class WishlistFragment : BaseFragment(), ItemAdapter.ClickListener {
    override val layout: Int
        get() = R.layout.fragment_wishlist
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    var itemAdapter: ItemAdapter? = null

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
        resultList.adapter = itemAdapter
        resultList.layoutManager = LinearLayoutManager(context)

        itemAdapter?.setClickListener(this)

        val dividerItemDecoration = DividerItemDecoration(resultList.context,
                (resultList.layoutManager as LinearLayoutManager).orientation)
        resultList.addItemDecoration(dividerItemDecoration)

        var tracks = ArrayList<TrackArtist.Message.Body.Track>()

        for (favorite in getDatabase()?.wishlist!!) {
            tracks.add(TrackArtist.new(favorite.songId, favorite.artist, favorite.title))
        }

        itemAdapter!!.addData(tracks)
    }

    override fun onItemClicked(item: TrackArtist.Message.Body.Track?) {
        var alertDialog = AlertDialog.Builder(context!!)

        var menus = arrayOf("Remove from Wishlist")

        alertDialog.setItems(menus) { dialog, which ->
            when (which) {
                0 -> {
                    if (getDatabase()?.removeWishlist(item?.track?.track_id!!)!!) {
                        itemAdapter?.remove(item)
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

    override fun onResume() {
        super.onResume()
        var tracks = ArrayList<TrackArtist.Message.Body.Track>()

        for (favorite in getDatabase()?.wishlist!!) {
            tracks.add(TrackArtist.new(favorite.songId, favorite.artist, favorite.title))
        }

        itemAdapter!!.addData(tracks)
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
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
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
         * @return A new instance of fragment WishlistFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                WishlistFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
