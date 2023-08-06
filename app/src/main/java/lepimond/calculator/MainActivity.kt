package lepimond.calculator

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


const val SAVED_CALCULATOR = "saved_calculator"

class MainActivity : Activity() {

    lateinit var btn0: Button
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button
    lateinit var btn4: Button
    lateinit var btn5: Button
    lateinit var btn6: Button
    lateinit var btn7: Button
    lateinit var btn8: Button
    lateinit var btn9: Button
    lateinit var btn_plus: Button
    lateinit var btn_minus: Button
    lateinit var btn_multiply: Button
    lateinit var btn_divide: Button
    lateinit var btn_equals: Button
    lateinit var btn_c: Button
    lateinit var btn_delete: Button

    lateinit var calculator_text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn0 = findViewById<Button>(R.id.btn_0)
        btn1 = findViewById<Button>(R.id.btn_1)
        btn2 = findViewById<Button>(R.id.btn_2)
        btn3 = findViewById<Button>(R.id.btn_3)
        btn4 = findViewById<Button>(R.id.btn_4)
        btn5 = findViewById<Button>(R.id.btn_5)
        btn6 = findViewById<Button>(R.id.btn_6)
        btn7 = findViewById<Button>(R.id.btn_7)
        btn8 = findViewById<Button>(R.id.btn_8)
        btn9 = findViewById<Button>(R.id.btn_9)
        btn_plus = findViewById<Button>(R.id.btn_plus)
        btn_minus = findViewById<Button>(R.id.btn_minus)
        btn_multiply = findViewById<Button>(R.id.btn_multiply)
        btn_divide = findViewById<Button>(R.id.btn_divide)
        btn_equals = findViewById<Button>(R.id.btn_equals)
        btn_delete = findViewById<Button>(R.id.btn_delete)

        btn_c = findViewById<Button>(R.id.btn_c)

        calculator_text = findViewById<EditText>(R.id.calculator_text)

        btn0.setOnClickListener {
            addSymbol(btn0.text.toString())
        }
        btn1.setOnClickListener {
            addSymbol(btn1.text.toString())
        }
        btn2.setOnClickListener {
            addSymbol(btn2.text.toString())
        }
        btn3.setOnClickListener {
            addSymbol(btn3.text.toString())
        }
        btn4.setOnClickListener {
            addSymbol(btn4.text.toString())
        }
        btn5.setOnClickListener {
            addSymbol(btn5.text.toString())
        }
        btn6.setOnClickListener {
            addSymbol(btn6.text.toString())
        }
        btn7.setOnClickListener {
            addSymbol(btn7.text.toString())
        }
        btn8.setOnClickListener {
            addSymbol(btn8.text.toString())
        }
        btn9.setOnClickListener {
            addSymbol(btn9.text.toString())
        }
        btn_plus.setOnClickListener {
            addSymbol(btn_plus.text.toString())
        }
        btn_minus.setOnClickListener {
            addSymbol(btn_minus.text.toString())
        }
        btn_multiply.setOnClickListener {
            addSymbol(btn_multiply.text.toString())
        }
        btn_divide.setOnClickListener {
            addSymbol(btn_divide.text.toString())
        }
        btn_equals.setOnClickListener {
            calculateResult()
        }
        btn_c.setOnClickListener {
            clearText()
        }
        btn_delete.setOnClickListener {
            deleteOneSymbol()
        }
    }

    fun toggleKeyboard(v: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        if (imm.isAcceptingText) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        } else {
            imm.toggleSoftInputFromWindow(v.windowToken, InputMethodManager.SHOW_FORCED, 0)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        var result = keyCode - 7

        if (result in 0..9) {
            calculator_text.append(result.toString())
        }
        when (keyCode) {
            KeyEvent.KEYCODE_MINUS -> calculator_text.append("-")
            KeyEvent.KEYCODE_PLUS -> calculator_text.append("+")
            KeyEvent.KEYCODE_STAR -> calculator_text.append("*")
            KeyEvent.KEYCODE_SLASH -> calculator_text.append("/")
            KeyEvent.KEYCODE_DEL -> deleteOneSymbol()
            KeyEvent.KEYCODE_EQUALS -> calculateResult()
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_CALCULATOR, calculator_text.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        calculator_text.text = savedInstanceState.getString(SAVED_CALCULATOR)
    }

    private fun deleteOneSymbol() {
        var currentText = calculator_text.text.toString()
        if (currentText.length > 0) {
            calculator_text.text = currentText.subSequence(0, currentText.length - 1)
        }
    }

    private fun calculateResult() {
        var currentText = calculator_text.text.toString()

        var firstNumber: Int = 0
        var endNumberPoint = 0

        var isFirstNegative = false
        if (currentText[0] == '-') {
            isFirstNegative = true
            currentText = currentText.substring(1)
        }
        for (i in 0 until currentText.length) {
            if (currentText[i] > '9' || currentText[i] < '0') {
                endNumberPoint = i
                break
            }

            firstNumber = firstNumber * 10 + (currentText[i].toInt() - 48)
        }
        if (isFirstNegative) {
            firstNumber = - firstNumber
        }

        val betweenNumbersChar = currentText[endNumberPoint]

        var lastNumber: Int = 0
        for (i in endNumberPoint + 1 until currentText.length) {
            lastNumber = lastNumber * 10 + (currentText[i].toInt() - 48)
        }

        try {
            var result = 0

            when (betweenNumbersChar) {
                '+' -> result = firstNumber + lastNumber
                '-' -> result = firstNumber - lastNumber
                '*' -> result = firstNumber * lastNumber
                '/' -> result = firstNumber / lastNumber
            }

            calculator_text.text = result.toString()
        } catch (e: ArithmeticException) {}

    }

    private fun clearText() {
        calculator_text.text = ""
    }

    private fun addSymbol(symbol: String) {
        calculator_text.append(symbol)
    }
}