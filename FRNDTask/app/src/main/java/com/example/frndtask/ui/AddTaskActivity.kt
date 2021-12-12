package com.example.frndtask.ui

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frndtask.MainActivity
import com.example.frndtask.data.models.request.PostDataRequest
import com.example.frndtask.data.models.request.Task
import com.example.frndtask.databinding.ActivityAddTaskAcitvityBinding
import com.example.frndtask.databinding.AddTaskLayoutBinding
import com.example.frndtask.viewModel.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity(), DateClickListener {

    private lateinit var binding: ActivityAddTaskAcitvityBinding
    private lateinit var addTaskLayoutBinding: AddTaskLayoutBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog

    private var selectedDate: LocalDate? = null
    private lateinit var calendarAdapter: CalendarDaysAdapter
    private lateinit var daysInMonth: ArrayList<String>
    private val viewModel:TaskViewModel by viewModels()
    private var date: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskAcitvityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedDate = LocalDate.now()
        date = selectedDate.toString()
//        Toast.makeText(this, date, Toast.LENGTH_SHORT).show()

        binding.fabAddTask.setOnClickListener {
            showBottomSheet()
        }

        setMonthView()

        binding.btnShowTasks.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setMonthView() {
        previousMonth()
        nextMonth()
        val currentDate = selectedDate.toString()
        daysInMonth = monthToArray(selectedDate!!)
        calendarAdapter = CalendarDaysAdapter(daysInMonth, this, currentDate)
        val layoutManager = GridLayoutManager(this, 7)

        binding.apply {
            monthYearTV.text = monthYearFromDate(selectedDate!!)
            calendarRecyclerView.layoutManager = layoutManager
            calendarRecyclerView.adapter = calendarAdapter
        }
    }

    private fun monthToArray(date: LocalDate): ArrayList<String> {
        val daysInMonthArray: ArrayList<String> = ArrayList()
        val yearMonth: YearMonth = YearMonth.from(date)
        val daysInMonth: Int = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                if (i > daysInMonth + dayOfWeek) break
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    private fun monthYearFromDate(date: LocalDate): String? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    private fun previousMonth() {
        binding.previousMonth.setOnClickListener {
            selectedDate = selectedDate!!.minusMonths(1)
            setMonthView()
        }
    }

    private fun nextMonth() {
        binding.nextMonth.setOnClickListener {
            selectedDate = selectedDate!!.plusMonths(1)
            setMonthView()
        }
    }

    private fun showBottomSheet() {
        bottomSheetDialog = BottomSheetDialog(this@AddTaskActivity)
        addTaskLayoutBinding = AddTaskLayoutBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(addTaskLayoutBinding.root)
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.show()
        addTaskLayoutBinding.btnDate.text = date.toString()
        addTaskLayoutBinding.ivCloseBottomSheet.setOnClickListener {
            bottomSheetDialog.cancel()
        }

        addTaskLayoutBinding.btnTvSubmit.setOnClickListener {
            viewModel.postTOApi(PostDataRequest(Task(addTaskLayoutBinding.btnDate.text.toString(), addTaskLayoutBinding.etDescription.text.toString(), addTaskLayoutBinding.etTaskName.text.toString()),1003))
            bottomSheetDialog.cancel()
        }
    }


    override fun onDateClicked(today: String, position: Int) {
        date = today
//        Toast.makeText(this, today, Toast.LENGTH_SHORT).show()
    }
}