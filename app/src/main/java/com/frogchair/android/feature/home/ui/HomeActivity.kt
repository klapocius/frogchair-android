package com.frogchair.android.feature.home.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.frogchair.android.R
import com.frogchair.android.common.model.AnimState
import com.frogchair.android.common.model.CrownType
import com.frogchair.android.common.network.LocalPlayerRepository
import com.frogchair.android.common.ui.EnergyRegenView
import com.frogchair.android.common.ui.NuxRegenView
import com.frogchair.android.common.ui.PrimaryButton
import com.frogchair.android.common.ui.UserLevelView
import com.frogchair.android.common.utils.animateUpDown
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.feature.band.BandActivity
import com.frogchair.android.feature.missionlist.ui.MissionListActivity
import com.frogchair.android.feature.more.MoreMenuDialogFragment
import com.frogchair.android.feature.summon.home.SummonHomeActivity


class HomeActivity : AppCompatActivity() {

    companion object {
        fun navigate(context: Context) = Intent(context, HomeActivity::class.java)
    }

    private val playerLevelView: UserLevelView by lazy { findViewById(R.id.toolbar_level_view) }
    private val energyRegenView: EnergyRegenView by lazy { findViewById(R.id.toolbar_energy_view) }
    private val nuxRegenView: NuxRegenView by lazy { findViewById(R.id.toolbar_nux_view) }

    private val playerNameView: TextView by lazy { findViewById(R.id.home_account_name) }
    private val bronzeCountView: TextView by lazy { findViewById(R.id.home_account_bronze_crown_text) }
    private val bronzeImageView: ImageView by lazy { findViewById(R.id.home_account_bronze_crown_image) }
    private val silverCountView: TextView by lazy { findViewById(R.id.home_account_silver_crown_text) }
    private val silverImageView: ImageView by lazy { findViewById(R.id.home_account_silver_crown_image) }
    private val goldCountView: TextView by lazy { findViewById(R.id.home_account_gold_crown_text) }
    private val goldImageView: ImageView by lazy { findViewById(R.id.home_account_gold_crown_image) }

    private val buildButton: View by lazy { findViewById(R.id.home_navigation_build) }
    private val bandButton: View by lazy { findViewById(R.id.home_navigation_band) }
    private val primaryButton: PrimaryButton by lazy { findViewById(R.id.home_navigation_main) }
    private val moreButton: View by lazy { findViewById(R.id.home_navigation_more) }
    private val fighterViewList: List<ImageView> by lazy {
        listOf(
            findViewById(R.id.home_band_1),
            findViewById(R.id.home_band_2),
            findViewById(R.id.home_band_3),
            findViewById(R.id.home_band_4),
            findViewById(R.id.home_band_5),
            findViewById(R.id.home_band_6),
            findViewById(R.id.home_band_7),
            findViewById(R.id.home_band_8),
            findViewById(R.id.home_band_9)
        )
    }

    private val player = LocalPlayerRepository.getPlayer()
    private val noCrownDrawable by lazy { ContextCompat.getDrawable(this, R.drawable.ic_crown_none) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initRegenViews()
        initPlayerInfo()
        initBand(AnimState.IDLE)

        buildButton.onClick { startActivity(SummonHomeActivity.navigate(this)) }
        bandButton.onClick { startActivity(BandActivity.navigate(this)) }

        primaryButton.displayJourney()
        primaryButton.onClick {
            startActivity(MissionListActivity.navigate(this))
        }

        moreButton.onClick { displayMoreDialog() }
    }

    private fun initRegenViews() {
        playerLevelView.init(player.userClass, player.currentXp, player.classUpXp)
        energyRegenView.init(player.enCountdown)
        nuxRegenView.init(player.bpCountdown)
    }

    private fun initPlayerInfo() {
        playerNameView.text = player.name
        val bronzeCount = player.crowns?.firstOrNull { it.type == CrownType.BRONZE }?.amount ?: 0
        val silverCount = player.crowns?.firstOrNull { it.type == CrownType.SILVER }?.amount ?: 0
        val goldCount = player.crowns?.firstOrNull { it.type == CrownType.GOLD }?.amount ?: 0
        bronzeCountView.text = bronzeCount.toString()
        silverCountView.text = silverCount.toString()
        goldCountView.text = goldCount.toString()

        if (bronzeCount == 0) {
            bronzeImageView.setImageDrawable(noCrownDrawable)
            bronzeCountView.visibility = View.INVISIBLE
        }
        if (silverCount == 0) {
            silverImageView.setImageDrawable(noCrownDrawable)
            silverCountView.visibility = View.INVISIBLE
        }
        if (goldCount == 0) {
            goldImageView.setImageDrawable(noCrownDrawable)
            goldCountView.visibility = View.INVISIBLE
        }
    }

    private fun displayMoreDialog() {
        MoreMenuDialogFragment.newInstance().show(supportFragmentManager, MoreMenuDialogFragment.TAG)
    }

    var animstate = 1
    private fun initBand(animState: AnimState) {
        fighterViewList.forEachIndexed { index, view ->
            val fighterAnimId = player.pavilion[index].findAnimId(resources, this, animState)
            if (fighterAnimId != 0) {
                Glide.with(this).load(fighterAnimId).into(view)
            } else {
                view.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        player.pavilion[index].findImageId(resources, this)
                    )
                )
                view.animateUpDown()
            }
        }
    }

    override fun onBackPressed() {
        val state = when (animstate) {
            0 -> {
                animstate = 1
                AnimState.IDLE
            }
            1 -> {
                animstate = 2
                AnimState.WALK
            }
            2 -> {
                animstate = 0
                AnimState.ATTACK
            }
            else -> return
        }

        initBand(state)
    }
}