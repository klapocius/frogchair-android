package com.frogchair.android.feature.summon.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.frogchair.android.R
import com.frogchair.android.common.ui.ErrorDialogFragment
import com.frogchair.android.common.ui.FighterSimpleView
import com.frogchair.android.common.ui.GenericDialogFragment
import com.frogchair.android.common.ui.PrimaryButton
import com.frogchair.android.common.utils.animateUpDown
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.feature.home.ui.HomeActivity

class SummonDetailsActivity : AppCompatActivity() {

    companion object {
        fun navigate(context: Context) = Intent(context, SummonDetailsActivity::class.java)
    }

    private val backArrow: View by lazy { findViewById(R.id.summon_details_back_arrow) }
    private val sampleFighterList: List<FighterSimpleView> by lazy {
        listOf(
            findViewById(R.id.summon_details_sample_1),
            findViewById(R.id.summon_details_sample_2),
            findViewById(R.id.summon_details_sample_3),
            findViewById(R.id.summon_details_sample_4),
            findViewById(R.id.summon_details_sample_5),
            findViewById(R.id.summon_details_sample_6)
        )
    }
    private val ratesView: View by lazy { findViewById(R.id.summon_details_rates_text) }
    private val exoView: View by lazy { findViewById(R.id.summon_details_exo_view) }
    private val rarityView: View by lazy { findViewById(R.id.summon_details_rarity_view) }
    private val fighterView: View by lazy { findViewById(R.id.summon_details_fighter_view) }
    private val buildButton: PrimaryButton by lazy { findViewById(R.id.summon_details_build_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summon_details)

        backArrow.onClick { onBackPressed() }

        ratesView.onClick { displayRatesDialog() }
        sampleFighterList.forEach { it.displayOnlyRarity() }

        buildButton.displayText("Build 10")
        buildButton.onClick { startActivity(HomeActivity.navigate(this)) }

        exoView.animateUpDown()
        fighterView.animateUpDown()
    }

    private fun displayRatesDialog() {
        GenericDialogFragment.newInstance("BUILD RATES", "0% lol this is a scam").apply {
            show(supportFragmentManager, ErrorDialogFragment.TAG)
        }
    }
}