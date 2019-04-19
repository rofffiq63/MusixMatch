package under.the.bridge.features.detail

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_find.*
import under.the.bridge.R
import under.the.bridge.data.model.TrackArtist
import under.the.bridge.features.base.BaseActivity

class DetailActivity : BaseActivity(), ScheduleAdapter.ClickListener {
    var scheduleAdapter: ScheduleAdapter? = null
    var date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)

        date = intent.getStringExtra("date")

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Event " + date

        scheduleAdapter = ScheduleAdapter()
        scheduleAdapter!!.setClickListener(this)
        eventList.adapter = scheduleAdapter
        eventList.layoutManager = LinearLayoutManager(this)

        val dividerItemDecoration = DividerItemDecoration(eventList.context,
                (eventList.layoutManager as LinearLayoutManager).orientation)
        eventList.addItemDecoration(dividerItemDecoration)

        scheduleAdapter?.addData(inDatabaseManager?.getSchedule(date)!!)

    }

    override fun onItemClicked(item: TrackArtist.Message.Body.Track?) {

    }

    override val layout: Int
        get() = R.layout.activity_detail

}