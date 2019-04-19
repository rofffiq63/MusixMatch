package under.the.bridge.features.main

import under.the.bridge.R
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import under.the.bridge.data.model.TrackArtist
import javax.inject.Inject

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var items: ArrayList<TrackArtist.Message.Body.Track>? = null
    private var mClickListener: ClickListener? = null

    init {
        items = ArrayList()
    }

    fun setClickListener(clickListener: ClickListener) {
        mClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(p0: ItemViewHolder, p1: Int) {
        val track = items?.get(p1)
        p0.item = track
        p0.nameText?.text = track?.track?.artist_name + " - " + track?.track?.track_name + "\n\n" + track?.track?.track_id
    }

    override fun getItemCount(): Int {
        return items?.size as Int
    }

    fun addData(data: List<TrackArtist.Message.Body.Track>) {
        items = ArrayList(data)
        notifyDataSetChanged()
    }

    fun remove(item: TrackArtist.Message.Body.Track?) {
        var position = items?.indexOf(item)
        items?.remove(item)
        notifyItemRemoved(position!!)
        notifyItemRangeChanged(position, itemCount)
    }

    interface ClickListener {
        fun onItemClicked(item: TrackArtist.Message.Body.Track?)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var item: TrackArtist.Message.Body.Track? = null
        @BindView(R.id.text_name)
        @JvmField
        var nameText: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener { if (mClickListener != null) mClickListener?.onItemClicked(item) }
        }
    }
}
