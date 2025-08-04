package com.binunu.todocheck

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var textViewDate: TextView
    private lateinit var buttonPrevDay: ImageButton
    private lateinit var buttonNextDay: ImageButton
    private lateinit var buttonCalendar: ImageButton

    private var currentDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewDate = findViewById(R.id.textViewDate)
        buttonPrevDay = findViewById(R.id.buttonPrevDay)
        buttonNextDay = findViewById(R.id.buttonNextDay)
        buttonCalendar = findViewById(R.id.buttonCalendar)

        updateDateView()

        buttonPrevDay.setOnClickListener{
            currentDate = currentDate.minusDays(1)
            updateDateView()
        }

        buttonNextDay.setOnClickListener{
            currentDate = currentDate.plusDays(1)
            updateDateView()
            animateSlide(-1)
        }

        buttonPrevDay.setOnClickListener {
            currentDate = currentDate.minusDays(1)
            updateDateView()
            animateSlide(-1)
        }

        buttonCalendar.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun updateDateView(){
        val fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        textViewDate.text = currentDate.format(fomatter)
    }

    private fun showDatePickerDialog(){
        val datePicker = DatePickerDialog(
            this, {
                _, year, month, dayOfMonth ->
                currentDate = LocalDate.of(year, month + 1, dayOfMonth)
                updateDateView()
                animateSlide()
            },
            currentDate.year,
            currentDate.monthValue - 1,
            currentDate.dayOfMonth
        )
        datePicker.show()
    }

    private fun animateSlide(direction: Int = 0) {
        // TODO: 애니메이션 구현 예정
    }
}