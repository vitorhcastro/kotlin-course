package com.vhcastro.swoosh.controller

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import com.vhcastro.swoosh.R
import com.vhcastro.swoosh.model.Player
import com.vhcastro.swoosh.utilities.EXTRA_PLAYER
import kotlinx.android.synthetic.main.activity_league.*

class LeagueActivity : BaseActivity() {

    var player = Player("","")

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_PLAYER, player)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        player = savedInstanceState.getParcelable(EXTRA_PLAYER)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league)
    }

    fun onMensClicked(view: View){
        womensClickedBtn.isChecked = false
        coedClickedBtn.isChecked = false
        player.league = "mens"
    }

    fun onWomensClicked(view: View){
        mensClickedBtn.isChecked = false
        coedClickedBtn.isChecked = false
        player.league = "womens"
    }

    fun onCoedClicked(view: View){
        mensClickedBtn.isChecked = false
        womensClickedBtn.isChecked = false
        player.league = "coed"
    }

    fun leagueNextClicked(view: View){
        if(player.league != ""){
            val skillActivity = Intent(this, SkillActivity::class.java)
            skillActivity.putExtra(EXTRA_PLAYER, player)
            startActivity(skillActivity)
        }else{
            Toast.makeText(this, "Please select a league.", Toast.LENGTH_SHORT).show()
        }
    }
}
