package com.example.frndtask.ui


import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.frndtask.R
import com.example.frndtask.databinding.LayoutCalendarDaysBinding
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class CalendarDaysAdapter(
    private var dateList: ArrayList<String>,
    private val clickListener: DateClickListener,
    private val currentDate: String,
) :
    RecyclerView.Adapter<CalendarDaysAdapter.ViewHolder>() {

    val bool = BooleanArray(dateList.size)
    private val itemViewList = ArrayList<LayoutCalendarDaysBinding>()
    private var selectedDate: LocalDate? = null
    private var curDate: List<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = DataBindingUtil.inflate<LayoutCalendarDaysBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_calendar_days,
            parent,
            false
        )
        itemViewList.add(viewBinding)
        return ViewHolder(viewBinding, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = dateList[position]
        holder.setData(day)
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    inner class ViewHolder(
        private val viewBinding: LayoutCalendarDaysBinding,
        private val clickListener: DateClickListener,
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        internal fun setData(date: String) {
            viewBinding.apply {

                tvDay.text = date

                //yyyy-MM-dd
                var today = currentDate.substring(0, currentDate.length - 2)
                today += date

                selectedDate = LocalDate.now()
                curDate = selectedDate.toString().split("-")

                val ar = currentDate.split("-")

                if (curDate!![2] == dateList[adapterPosition] && curDate!![0] == ar[0] && curDate!![1] == ar[1]) {
                    bool[adapterPosition] = true
                    viewBinding.rlDate.setBackgroundResource(R.drawable.current_selected_date_bg)
                    viewBinding.tvDay.setTextColor(Color.parseColor("#FFFFFFFF"))
                }

                if (viewBinding.tvDay.text.toString() != "") {

                    rlDate.setOnClickListener {
                        if (bool[adapterPosition]) {
                            if (date == curDate!![2]) {
                                viewBinding.rlDate.setBackgroundResource(R.drawable.current_selected_date_bg)
                                viewBinding.tvDay.setTextColor(Color.parseColor("#FFFFFFFF"))
                            }
//                        rlDate.setBackgroundResource(R.drawable.selected_date_bg)
                        } else {
                            boolCheck()
                            if (date == curDate!![2]) {
                                viewBinding.rlDate.setBackgroundResource(R.drawable.current_selected_date_bg)
                                viewBinding.tvDay.setTextColor(Color.parseColor("#FFFFFFFF"))
                            } else {
                                rlDate.setBackgroundResource(R.drawable.selected_date_bg)
                            }
                            bool[adapterPosition] = true
                        }
                        clickListener.onDateClicked(today, adapterPosition)
                    }
                }
            }
        }

        private fun boolCheck() {
            for (i in 0 until dateList.size) {
                if (bool[i] && i != adapterPosition) {
                    if (itemViewList[i].tvDay.text.toString() == curDate!![2]) {
                        itemViewList[i].tvDay.setTextColor(Color.parseColor("#527FF3"))
                        itemViewList[i].rlDate.setBackgroundResource(0)
                    } else {
                        itemViewList[i].rlDate.setBackgroundResource(0)
                    }
                    bool[i] = false
//                    itemViewList[i].labourLayout.visibility = View.GONE
                }
            }

        }

    }
}

interface DateClickListener {
    fun onDateClicked(today: String, position: Int)
}
