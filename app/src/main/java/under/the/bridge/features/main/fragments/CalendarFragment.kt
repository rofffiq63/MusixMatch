package under.the.bridge.features.main.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.CalendarView
import kotlinx.android.synthetic.main.fragment_calendar.*

import under.the.bridge.R
import under.the.bridge.features.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.*
import android.R.drawable
import android.app.*
import android.support.v4.content.ContextCompat.getSystemService
import android.os.SystemClock
import under.the.bridge.util.NotificationPublisher
import android.content.Intent
import android.widget.DatePicker
import android.widget.TimePicker
import kotlinx.android.synthetic.main.fragment_create.*
import under.the.bridge.data.database.table.SchedulerTable
import under.the.bridge.features.detail.DetailActivity


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CalendarFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CalendarFragment : BaseFragment(), CalendarView.OnDateChangeListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    override val layout: Int
        get() = R.layout.fragment_calendar
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    lateinit var datePickerDialog: DatePickerDialog
    lateinit var timePickerDialog: TimePickerDialog
    var dateSetup: Long = 0
    var date = ""
    var time = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView.date = System.currentTimeMillis()
        calendarView.setOnDateChangeListener(this)

        var calendar = Calendar.getInstance()

        datePicker.setOnClickListener {
            datePickerDialog = DatePickerDialog(context, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }

        timePicker.setOnClickListener {
            timePickerDialog = TimePickerDialog(context, this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
            timePickerDialog.show()
        }

        createEvent.setOnClickListener { v: View? ->
            if (validate() && getDatabase()?.addSchedule(SchedulerTable(
                            eventName.text.toString(),
                            datePicker.text.toString(),
                            timePicker.text.toString()
                    ))!!) {
                dateSetup = SimpleDateFormat("dd MMMM yyyyHH:mm", Locale.US).parse(date + time).time
                scheduleNotification(dateSetup)
            }
        }
    }

    fun validate(): Boolean {
        var passed = true
        if (eventName.text.isEmpty()) {
            passed = false
            showToast("Event cannot be empty")
        }

        if (datePicker.text.isEmpty()) {
            passed = false
            showToast("Date cannot be empty")
        }

        if (timePicker.text.isEmpty()) {
            passed = false
            showToast("Time cannot be empty")
        }

        return passed
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        var cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        date = SimpleDateFormat("dd MMMM yyyy", Locale.US).format(cal.time)
        datePicker.text = date
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        var cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
        cal.set(Calendar.MINUTE, minute)

        time = SimpleDateFormat("HH:mm", Locale.US).format(cal.time)
        timePicker.text = time
    }

    override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.US)
        val selectedDates = sdf.format(Date(year - 1900, month, dayOfMonth))
        showToast(selectedDates)
        startActivity(Intent(context, DetailActivity::class.java)
                .putExtra("date", selectedDates))
        false
    }

    private fun scheduleNotification(date: Long) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val notificationIntent = Intent(context, NotificationPublisher::class.java)
                .putExtra("date", date)
        val broadcast = PendingIntent.getBroadcast(context, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, date, broadcast)
        eventName.text.clear()
        eventName.clearFocus()
        datePicker.text = ""
        timePicker.text = ""
        showToast("Schedule created")
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
         * @return A new instance of fragment CalendarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CalendarFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
