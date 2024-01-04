package com.example.mindsharpener

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userInput: EditText = findViewById(R.id.user_input) //Obtaining variables by using findViewById
        val checkBtn: Button = findViewById(R.id.check_button)
        val pointObtained: TextView = findViewById(R.id.point_value)
        val level: RadioGroup = findViewById(R.id.difficulty_level)
        val operand1: TextView = findViewById(R.id.operand_1)
        val operand2: TextView = findViewById(R.id.operand_2)
        val operator: TextView = findViewById(R.id.operator)

        setSupportActionBar(findViewById(R.id.header)) //Setting up the toolbar as Action bar
        supportActionBar?.title = "MindSharpener" //Setting the title of the action bar
        supportActionBar?.setDisplayShowTitleEnabled(true)

        level.setOnCheckedChangeListener { _, _ -> //checking which radio button is selected
            val lvl = when (level.checkedRadioButtonId) {
                R.id.i3_button -> 1
                R.id.i5_button -> 2
                R.id.i7_button -> 3
                else -> 0
            }
            operand1.text = getOperand(lvl).toString() //setting value of first operand

            //Picking an operator randomly
            when ((0..3).shuffled().last()) { //Setting the type of operator randomly
                1 -> operator.text = "+"
                2 -> operator.text = "-"
                3 -> operator.text = "*"
                else -> operator.text = "/"
            }
            operand2.text = getOperand(lvl).toString()//setting value of second operand
        }

        var totalPoint = 0  //Declaring a temporary variable to store total points gained by user

        checkBtn.setOnClickListener {
            if (verifyAnswer(
                    operator.text.toString(),
                    userInput.text.toString().toInt(),
                    operand1.text.toString().toInt(),
                    operand2.text.toString().toInt()
                )
            ) {
                ++totalPoint    //When verifyAnswer() returns true, user answered correctly
            } else {
                --totalPoint    //When verifyAnswer() returns false, user has answered incorrectly
            }
            pointObtained.text = totalPoint.toString() //Returning the total points user has gathered back to activity_main.xml
        }
    }

    private fun getOperand(lvl: Int): Int {
        val randomOperand: Int = when (lvl) {
            1 -> (0..9).shuffled().last() //Getting 1 digit operands (i3)
            2 -> (0..99).shuffled().last() //Getting 2 digits operands (i5)
            3 -> (0..999).shuffled().last() //Getting 3 digits operands (i7)
            else -> 0
        }
        return randomOperand
    }

    private fun verifyAnswer(operator: String, userInput: Int, operand1: Int, operand2: Int): Boolean {
        return try {
            val ans = when (operator) { // Answers of the questions provided
                "+" -> operand1 + operand2
                "-" -> operand1 - operand2
                "*" -> operand1 * operand2
                "/" -> operand1 / operand2
                else -> 0
            }
            userInput == ans    //Comparing answer with user input
        } catch (e: ArithmeticException) {
            // Handle division by zero
            false
        }
    }
}
