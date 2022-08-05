package com.js.calculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.js.calculator.utils.ToastUtil
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private var result: StringBuilder = StringBuilder("0")
    private var isNewInput: Boolean = true
    private var isFirstNumber: Boolean = true

    private lateinit var etInput1: EditText
    private lateinit var etInput2: EditText
    private lateinit var btnSetting: ImageButton
    private lateinit var tvOperator: TextView
    private lateinit var etOutput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configUI()
    }

    override fun onBackPressed() {
        val home = Intent(Intent.ACTION_MAIN)
        home.addCategory(Intent.CATEGORY_HOME)
        home.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(home)
    }

    private fun configUI() {
        etInput1 = findViewById(R.id.et_input1)
        etInput2 = findViewById(R.id.et_input2)

        tvOperator = findViewById(R.id.tv_oprt)
        etOutput = findViewById(R.id.et_output)

        btnSetting = findViewById(R.id.btn_setting)

        btnSetting.setOnClickListener {
            ToastUtil.showLastShortMessage(this, "设置（正在开发）")
//            intent.setClass(this, MoreActivity().javaClass)
//            startActivity(intent)
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
        val btn00: Button = findViewById(R.id.btn_00)

        val btnAdd: Button = findViewById(R.id.btn_add)
        val btnSub: Button = findViewById(R.id.btn_sub)
        val btnMul: Button = findViewById(R.id.btn_mul)
        val btnDiv: Button = findViewById(R.id.btn_div)

        val btnEq: Button = findViewById(R.id.btn_eq)

        val btnAC: Button = findViewById(R.id.btn_ac)
        val btnDel: Button = findViewById(R.id.btn_del)

        val btnPM: Button = findViewById(R.id.btn_pm)

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
        btn00.setOnClickListener { numClicked("00") }

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
        if (isNewInput) {
            etInput1.text.clear()
            etInput2.text.clear()
            tvOperator.text = ""
            isNewInput = false
        }

        if (isFirstNumber) {
//            一个数字至多包含一个小数点
            if (sth == "." && etInput1.text.contains("."))
                return
            etInput1.text.append(sth)
        } else {
            if (sth == "." && etInput2.text.contains("."))
                return
            etInput2.text.append(sth)
        }
    }

    private fun oprClicked(opr: String) {
        if (isNewInput) {
            etInput1.text.clear()
            etInput2.text.clear()
            isNewInput = false
        }
//        连续运算，直接点运算符求结果，无需按等号
        if (!isFirstNumber && etInput2.text.isNotEmpty()) {
            eqClicked()
            oprClicked(opr)
            return
        }
//        直接输入运算符则把结果作为第一个数
        if (etInput1.text.isEmpty()) {
            etInput1.setText(result)
            etInput2.text.clear()
        }
//        实时显示运算符
        tvOperator.text = opr
//        开启输入第二个数
        isFirstNumber = false
    }

    private fun eqClicked() {
//        计算前先判断用户输入：1. 第一个数 2. 第二个数 3. 运算符
        if (etInput1.text.isEmpty() || etInput2.text.isEmpty() || tvOperator.text.isEmpty()) {
            return ToastUtil.showLastShortMessage(this, "表达式不完整")
        }
//        转换为 BigDecimal 计算，结果更精确
        val number1 = BigDecimal(etInput1.text.toString())
        val number2 = BigDecimal(etInput2.text.toString())
        var number3 = BigDecimal("0")

        when (tvOperator.text) {
            "+" -> number3 = number1.add(number2)
            "-" -> number3 = number1.subtract(number2)
            "×" -> number3 = number1.multiply(number2)
            "÷" -> {
                if (number2.compareTo(number3) == 0) {
                    return displayResult("Division by Zero")
                }
//                保留 8 位小数
                number3 = number1.divide(number2, 8, RoundingMode.HALF_EVEN)
            }
        }

        displayResult(number3)
    }

    private fun displayResult(number: BigDecimal) {
//        去除末尾多余的 0，避免以科学记数法显示
        result = StringBuilder(number.stripTrailingZeros().toPlainString())
//        显式运算结果
        etOutput.setText(result)
//        开启新一轮输入
        isNewInput = true
//        开启输入第一个数
        isFirstNumber = true
    }

    //    显示错误输出，如除以 0
    private fun displayResult(message: String) {
        result = StringBuilder("0")
        etOutput.setText(message)
        isNewInput = true
        isFirstNumber = true
    }

    private fun allClear() {
//        清空缓存，还原初始状态
        result = StringBuilder("0")
        isNewInput = true
        isFirstNumber = true
        etInput1.text.clear()
        etInput2.text.clear()
        etOutput.text.clear()
        tvOperator.text = ""
    }

    private fun del() {
        if (isFirstNumber) {
//            数字为空则空操作
            if (etInput1.text.isNotEmpty()) {
                etInput1.setText(etInput1.text.dropLast(1))
//                防止表达式仅一个负号（-）导致出错
                if (etInput1.text.toString() == "-")
                    etInput1.text.clear()
            }
        } else {
            if (etInput2.text.isNotEmpty()) {
                etInput2.setText(etInput2.text.dropLast(1))
                if (etInput2.text.toString() == "-")
                    etInput2.text.clear()
//                第二个数为空，则删除第一个数，并将运算符设为未知值（？）
            } else {
                isFirstNumber = true
                tvOperator.text = ""
                del()
            }
        }

    }

    //    切换正负号
    private fun swapPM() {
        if (isFirstNumber) {
//            如果数字为空则空操作，防止表达式仅一个负号（-）导致出错
            if (etInput1.text.isEmpty())
                return
            if (etInput1.text.startsWith("-")) {
                etInput1.setText(etInput1.text.removePrefix("-"))
            } else {
                etInput1.text.insert(0, "-")
            }
        } else {
            if (etInput2.text.isEmpty())
                return
            if (etInput2.text.startsWith("-")) {
                etInput2.setText(etInput2.text.removePrefix("-"))
            } else {
                etInput2.text.insert(0, "-")
            }
        }
    }
}