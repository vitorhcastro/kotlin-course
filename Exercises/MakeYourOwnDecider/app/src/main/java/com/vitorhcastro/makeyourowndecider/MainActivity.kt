package com.vitorhcastro.makeyourowndecider

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val foodArray = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addOptionBtn.setOnClickListener {
            val newFood = addOptionTxt.text.toString()

            if(!foodArray.contains((newFood)) && newFood.isNotBlank()){
                foodArray.add(newFood)

                if(foodArray.count() > 1){
                    decideBtn.visibility = View.VISIBLE
                }
            }

            addOptionTxt.text.clear()
        }

        decideBtn.setOnClickListener {
            val randomFoodNumber = Random().nextInt(foodArray.count())
            val chosenFood = foodArray[randomFoodNumber]
            chosenOptionTxt.text = chosenFood
        }
    }
}
