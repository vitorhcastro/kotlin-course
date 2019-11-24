package com.vhcastro.swoosh

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_league.*

class LeagueActivity : BaseActivity() {

    var selectedLeague = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league)
    }

    fun onMensClicked(view: View){
        womensClickedBtn.isChecked = false
        coedClickedBtn.isChecked = false
        selectedLeague = "mens"
    }

    fun onWomensClicked(view: View){
        mensClickedBtn.isChecked = false
        coedClickedBtn.isChecked = false
        selectedLeague = "womens"
    }

    fun onCoedClicked(view: View){
        mensClickedBtn.isChecked = false
        womensClickedBtn.isChecked = false
        selectedLeague = "coed"
    }

    fun leagueNextClicked(view: View){
        if(selectedLeague != ""){
            val skillActivity = Intent(this, SkillActivity::class.java)
            skillActivity.putExtra(EXTRA_LEAGUE, selectedLeague)
            startActivity(skillActivity)
        }else{
            Toast.makeText(this, "Please select a league.", Toast.LENGTH_SHORT).show()
        }
    }
}
