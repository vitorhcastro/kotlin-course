package com.vitorhcastro.makeyourowndecider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val foodArray = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFoodBtn.setOnClickListener {
            val newFood = addFoodTxt.text.toString()
            if(!foodArray.contains((newFood))){
                foodArray.add(newFood)
                if(foodArray.count() > 1){
                    decideBtn.visibility = View.VISIBLE
                }
            }
            addFoodTxt.text.clear()
        }

        decideBtn.setOnClickListener {
            val randomFoodNumber = Random().nextInt(foodArray.count())
            val chosenFood = foodArray[randomFoodNumber]
            selectedFoodTxt.text = chosenFood
        }
    }
}
