package com.frogchair.android.feature.band

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.frogchair.android.R
import com.frogchair.android.common.ui.FighterFrameView
import com.frogchair.android.common.ui.PrimaryButton
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.feature.detailedfighter.DetailedFighterActivity
import com.frogchair.android.feature.fighterlist.FighterListActivity
import com.frogchair.android.feature.home.ui.HomeActivity
import com.frogchair.android.feature.mission.MissionActivity

class BandActivity : AppCompatActivity(), FighterFrameView.Listener {

    companion object {
        private const val ARG_NEXT_SCREEN_IS_MISSION = "ARG_NEXT_SCREEN_IS_MISSION"

        fun navigate(context: Context, nextScreenIsMission: Boolean = false) =
            Intent(context, BandActivity::class.java).apply {
                putExtra(ARG_NEXT_SCREEN_IS_MISSION, nextScreenIsMission)
            }
    }

    private val toolbarText: TextView by lazy { findViewById(R.id.title_toolbar_text) }
    private val toolbarHomeButton: View by lazy { findViewById(R.id.title_home_button) }
    private val backArrow: View by lazy { findViewById(R.id.band_navigation_back_arrow) }
    private val goButton: PrimaryButton by lazy { findViewById(R.id.band_go_button) }

    private val skillCheckBox: CheckBox by lazy { findViewById(R.id.band_fighter_checkbox_skill) }
    private val statsTextView: View by lazy { findViewById(R.id.band_fighter_checkbox_stats_text) }
    private val statsCheckBox: CheckBox by lazy { findViewById(R.id.band_fighter_checkbox_stats) }
    private val skillTextView: View by lazy { findViewById(R.id.band_fighter_checkbox_skill_text) }

    private val fighterListButton: View by lazy { findViewById(R.id.band_fighter_button_fighter) }

    private val fighterViewList: List<FighterFrameView> by lazy {
        listOf(
            findViewById(R.id.band_fighter_1),
            findViewById(R.id.band_fighter_2),
            findViewById(R.id.band_fighter_3),
            findViewById(R.id.band_fighter_4),
            findViewById(R.id.band_fighter_5),
            findViewById(R.id.band_fighter_6),
            findViewById(R.id.band_fighter_7),
            findViewById(R.id.band_fighter_8),
            findViewById(R.id.band_fighter_9)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_band)

        if (intent.extras?.getBoolean(ARG_NEXT_SCREEN_IS_MISSION) == true) initGoButton()
        backArrow.onClick { onBackPressed() }

        toolbarText.text = "BAND"
        toolbarHomeButton.onClick { startActivity(HomeActivity.navigate(this)) }

        initCheckBoxes()

        fighterListButton.onClick { startActivity(FighterListActivity.navigate(this)) }

        fighterViewList.forEach {
            it.setListener(this)
        }
    }

    private fun initGoButton() {
        goButton.visibility = View.VISIBLE
        goButton.displayGo()
        goButton.onClick { startActivity(MissionActivity.navigate(this)) }
    }

    private fun initCheckBoxes() {
        skillCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                statsCheckBox.isChecked = false
                displaySkills()
            } else {
                hideSkills()
            }
        }
        statsCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                skillCheckBox.isChecked = false
                displayStats()
            } else {
                hideStats()
            }
        }
        skillTextView.setOnClickListener {
            skillCheckBox.isChecked = !skillCheckBox.isChecked
        }
        statsTextView.setOnClickListener {
            statsCheckBox.isChecked = !statsCheckBox.isChecked
        }
    }

    private fun hideStats() = fighterViewList.forEach { it.hideStats() }
    private fun displayStats() = fighterViewList.forEach { it.displayStats() }
    private fun hideSkills() = fighterViewList.forEach { it.hideSkills() }
    private fun displaySkills() = fighterViewList.forEach { it.displaySkills() }

    override fun onFighterClicked() {
        startActivity(DetailedFighterActivity.navigate(this))
    }

    override fun onWeaponClicked() {
    }

}