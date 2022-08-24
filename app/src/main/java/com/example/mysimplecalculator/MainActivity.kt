package com.example.mysimplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    private var Numbers = StringBuilder()
    private lateinit var calc: TextView
    private lateinit var numButtons: Array<Button>
    private lateinit var OpButtons: Array<Button>
    private var operator: Operators = Operators.NONE
    private var Operatorclick: Boolean = false
    private var oldnum: Double = 0.0
    private var type: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Component()
        delete()
        buttonNegPos.setOnClickListener{ NegPosClick() }
        buttonTotal.setOnClickListener{ buttonEqualClick()}
        buttonCLR.setOnClickListener{(buttonClearClick())}
    }

    private fun NegPosClick() {
        if (type) {
            val test = Numbers.toString().toDouble() * -1.0
            Numbers.clear()
            Numbers.append(test)
        } else {
            val test2 = Numbers.toString().toDouble() * 1.0
            Numbers.clear()
            Numbers.append(test2)
        }
        calc.text = Numbers
    }

    private fun buttonClearClick(){
            Numbers.clear()
            calc.text = "0"
    }

    private fun delete() {
        buttonDel.setOnClickListener() {
            val length = Numbers.length
            if(length > 1){
                Numbers.deleteCharAt(Numbers.length - 1)
                updateDis()
            }
            else{
                Numbers.clear()
                calc.text = "0"
            }
        }
    }

    private fun updateDis() {
        try {
            val textValue = Numbers.toString().toDouble()
            calc.text = textValue.toString()
        }
        catch(e:IllegalArgumentException){
            Numbers.clear()
            calc.text = "ERROR"
        }
    }

    private fun Component(){
        calc = findViewById(R.id.calc)
        numButtons = arrayOf(button1,button2,button3,button4,button5,button6,button7,button8,button9,button0, buttonDecimal)

        for (i in numButtons){
            i.setOnClickListener{numButtonsClick(i)}
        }

        OpButtons = arrayOf(buttonAdd, buttonMinus, buttonDivide, buttonMulti)
        for (i in OpButtons){
            i.setOnClickListener{OperatorButtonsClick (i)}
        }

    }
    private fun numButtonsClick(btn:Button){
        if(Operatorclick){
            oldnum = Numbers.toString().toDouble()
            Numbers.clear()
            Operatorclick = false
        }
        Numbers.append(btn.text)
        updateDis()
    }

    private fun OperatorButtonsClick(btn:Button){
        if(btn.text == "+") {
            operator = Operators.ADD
        }
        else if(btn.text == "-"){
            operator = Operators.MINUS
        }
        else if(btn.text == "/"){
            operator = Operators.DIVIDE
        }
        else if(btn.text == "*"){
            operator = Operators.MULTI
        }
        else operator = Operators.NONE
        Operatorclick = true
    }

    private fun buttonEqualClick() {
        val newnum = Numbers.toString().toDouble()
        var result: Double
        when(operator){
            Operators.ADD -> result = oldnum + newnum
            Operators.MINUS -> result = oldnum - newnum
            Operators.MULTI -> result = oldnum * newnum
            Operators.DIVIDE -> result = oldnum / newnum
            else ->  result = 0.0
        }
        Numbers.clear()
        Numbers.append(result.toString())
        updateDis()
    }
}
enum class Operators {ADD, MINUS, DIVIDE, MULTI, NONE}
