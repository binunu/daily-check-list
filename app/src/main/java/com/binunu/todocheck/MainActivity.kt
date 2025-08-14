package com.binunu.todocheck

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomappbar.BottomAppBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var textViewDate: TextView
    private lateinit var buttonPrevDay: ImageButton
    private lateinit var buttonNextDay: ImageButton
    private lateinit var buttonCalendar: ImageButton

    private lateinit var todoListContainer: LinearLayout
    private lateinit var editTodo: EditText
    private lateinit var btnAdd: Button

    private var currentDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.topPanel)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // view와 변수 연결
        textViewDate = findViewById(R.id.textDate)
        buttonPrevDay = findViewById(R.id.btnPrev)
        buttonNextDay = findViewById(R.id.btnNext)
        buttonCalendar = findViewById(R.id.buttonCalendar)

        todoListContainer = findViewById(R.id.todoListContainer)
        editTodo = findViewById(R.id.editTodo)
        btnAdd = findViewById(R.id.btnAdd)


        updateDateView()

        //상단 날짜이동 버튼 클릭
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

        //할 일 추가 버튼 클릭
        btnAdd.setOnClickListener {
            val text = editTodo.text.toString()
            Log.d("btnAdd", "add click Event !!")
            if (text.isNotEmpty()) {
                addTodoItem(text)
                editTodo.text.clear()
            }
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
                //animateSlide()
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

    private fun addTodoItem(text : String){
        val inflater = LayoutInflater.from(this) //xml -> view변환도구
        val todoItemView = inflater.inflate(R.layout.todo_item, todoListContainer, false)

        //불러온 뷰에서 텍스트뷰 찾기
        val textView = todoItemView.findViewById<TextView>(R.id.textTodo)
        textView.text = text
        todoListContainer.addView(todoItemView)
    }
}