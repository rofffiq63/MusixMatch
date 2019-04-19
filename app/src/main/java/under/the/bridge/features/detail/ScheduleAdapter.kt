package under.the.bridge.features.detail

import under.the.bridge.R
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import under.the.bridge.data.database.table.SchedulerTable
import under.the.bridge.data.model.TrackArtist
import javax.inject.Inject

class ScheduleAdapter : RecyclerView.Adapter<ScheduleAdapter.ItemViewHolder>() {

    private var items: ArrayList<SchedulerTable>? = null
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
        val schedulerTable = items?.get(p1)
        p0.item = schedulerTable
        p0.nameText?.text = schedulerTable?.content + "\n" + schedulerTable?.date + " at " + schedulerTable?.time
    }

    override fun getItemCount(): Int {
        return items?.size as Int
    }

    fun addData(data: List<SchedulerTable>) {
        items = ArrayList(data)
        notifyDataSetChanged()
    }

    fun remove(item: SchedulerTable?) {
        var position = items?.indexOf(item)
        items?.remove(item)
        notifyItemRemoved(position!!)
        notifyItemRangeChanged(position, itemCount)
    }

    interface ClickListener {
        fun onItemClicked(item: TrackArtist.Message.Body.Track?)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var item: SchedulerTable? = null
        @BindView(R.id.text_name)
        @JvmField
        var nameText: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
