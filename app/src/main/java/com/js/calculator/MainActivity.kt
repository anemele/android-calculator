package com.js.calculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var num1: StringBuilder = StringBuilder()
    private var num2: StringBuilder = StringBuilder()
    private var num3: StringBuilder = StringBuilder()

    private var isFirstNumber: Boolean = true

    private lateinit var tvInput1: TextView
    private lateinit var tvInput2: TextView
    private lateinit var tvOperator: TextView
    private lateinit var tvOutput: TextView
    private lateinit var btnSetting: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput1 = findViewById(R.id.tv_input1)
        tvInput2 = findViewById(R.id.tv_input2)

        tvOperator = findViewById(R.id.tv_oprt)
        tvOutput = findViewById(R.id.tv_output)

        btnSetting = findViewById(R.id.btn_setting)

        btnSetting.setOnClickListener {
            val intent = Intent(this, MoreActivity().javaClass)
            startActivity(intent)
        }

        val btn0: Button = findViewById(R.id.btn_0)
        val btn1: Button = findViewById(R.id.btn_1)
        val btn2: Button = findViewById(R.id.btn_2)
        val btn3: Button = findViewById(R.id.btn_3)
        val btn4: Button = findViewById(R.id.btn_4)
        val btn5: Button = findViewById(R.id.btn_5)
        val btn6: Button = findViewById(R.id.btn_6)
        val btn7: Button = findViewById(R.id.btn_7)
        val btn8: Button = findViewById(R.id.btn_8)
        val btn9: Button = findViewById(R.id.btn_9)
        val btnDot: Button = findViewById(R.id.btn_dot)

        val btnAdd: Button = findViewById(R.id.btn_add)
        val btnSub: Button = findViewById(R.id.btn_sub)
        val btnMul: Button = findViewById(R.id.btn_mul)
        val btnDiv: Button = findViewById(R.id.btn_div)

        val btnEq: Button = findViewById(R.id.btn_eq)

        val btnAC: Button = findViewById(R.id.btn_ac)
        val btnDel: Button = findViewById(R.id.btn_del)

        val btnPM: Button = findViewById(R.id.btn_pm)
/*
        val btnPct: Button = findViewById(R.id.btn_pct)
*/
        btn0.setOnClickListener { numClicked("0") }
        btn1.setOnClickListener { numClicked("1") }
        btn2.setOnClickListener { numClicked("2") }
        btn3.setOnClickListener { numClicked("3") }
        btn4.setOnClickListener { numClicked("4") }
        btn5.setOnClickListener { numClicked("5") }
        btn6.setOnClickListener { numClicked("6") }
        btn7.setOnClickListener { numClicked("7") }
        btn8.setOnClickListener { numClicked("8") }
        btn9.setOnClickListener { numClicked("9") }
        btnDot.setOnClickListener { numClicked(".") }

        btnAdd.setOnClickListener { oprClicked("+") }
        btnSub.setOnClickListener { oprClicked("-") }
        btnMul.setOnClickListener { oprClicked("×") }
        btnDiv.setOnClickListener { oprClicked("÷") }

        btnEq.setOnClickListener { eqClicked() }

        btnAC.setOnClickListener { allClear() }
        btnDel.setOnClickListener { del() }

        btnPM.setOnClickListener { swapPM() }

    }

    private fun numClicked(sth: String) {
        if (isFirstNumber) {
            if (sth == "." && num1.contains("."))
                return
            num1.append(sth)
            tvInput1.text = num1
        } else {
            if (sth == "." && num2.contains("."))
                return
            num2.append(sth)
            tvInput2.text = num2
        }
    }

    private fun oprClicked(sth: String) {
        tvOperator.text = sth
        isFirstNumber = false
    }

    private fun eqClicked() {
        val number1 = String(num1).toDouble()
        val number2 = String(num2).toDouble()
        var result = 0.0

/*        try {*/

            when (tvOperator.text) {
                "+" -> result = number1 + number2
                "-" -> result = number1 - number2
                "×" -> result = number1 * number2
                "÷" -> result = number1 / number2
            }

//        如果结果是整数则只显示整数，如何写代码？
//        if (result.equals(result.toInt())) {
//            result = result.toInt().toDouble()
//        }
            num3 = StringBuilder(result.toString())
            tvOutput.text = num3

            isFirstNumber = true
            num1.clear()
            num2.clear()
/*        } catch (e: Exception) {
            allClear()
        }*/
    }

    private fun allClear() {
        num1.clear()
        num2.clear()
        num3.clear()
        tvInput1.text = "0"
        tvInput2.text = "0"
        tvOutput.text = "0"
        tvOperator.text = "?"
        isFirstNumber = true
    }

    private fun del() {
        if (isFirstNumber) {
            if (num1.isNotEmpty()) {
                num1.deleteCharAt(num1.length - 1)
                tvInput1.text = num1
            } else {
                if (num2.isNotEmpty()) {
                    num2.deleteCharAt(num2.length - 1)
                    tvInput2.text = num2
                }
            }
        }
    }

    private fun swapPM() {
        if (isFirstNumber) {
            if (num1.startsWith("-")) {
                num1.deleteCharAt(0)
            } else {
                num1.insert(0, "-")
            }
            tvInput1.text = num1
        } else {
            if (num2.startsWith("-")) {
                num2.deleteCharAt(0)
            } else {
                num2.insert(0, "-")
            }
            tvInput2.text = num2
        }
    }
}