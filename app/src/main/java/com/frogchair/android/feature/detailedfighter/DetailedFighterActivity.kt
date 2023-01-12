package com.frogchair.android.feature.detailedfighter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.frogchair.android.R
import com.frogchair.android.common.ui.FighterLevelView
import com.frogchair.android.common.ui.PrimaryButton
import com.frogchair.android.common.utils.animateUpDown
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.common.utils.setDrawableAliasing
import com.frogchair.android.feature.catalog.model.*
import com.frogchair.android.feature.home.ui.HomeActivity

class DetailedFighterActivity : AppCompatActivity() {

    companion object {
        const val BUNDLE_FIGHTER = "BUNDLE_FIGHTER"

        fun navigate(context: Context, fighter: Fighter? = null) =
            Intent(context, DetailedFighterActivity::class.java).apply {
                if (fighter != null) {
                    putExtra(BUNDLE_FIGHTER, fighter)
                }
            }
    }

    private val toolbarText: TextView by lazy { findViewById(R.id.detailed_fighter_toolbar_text) }
    private val toolbarHomeButton: View by lazy { findViewById(R.id.detailed_fighter_home_button) }
    private val backArrow: View by lazy { findViewById(R.id.detailed_fighter_navigation_back_arrow) }

    private val fuseButton: PrimaryButton by lazy { findViewById(R.id.detailed_fighter_navigation_fuse_button) }
    private val moveButton: View by lazy { findViewById(R.id.detailed_fighter_navigation_move) }
    private val replaceButton: View by lazy { findViewById(R.id.detailed_fighter_navigation_replace) }

    private val lockButton: ImageButton by lazy { findViewById(R.id.detailed_fighter_lock_button) }
    private val levelView: FighterLevelView by lazy { findViewById(R.id.detailed_fighter_level) }
    private val sefTextView: TextView by lazy { findViewById(R.id.detailed_fighter_sef_value) }

    private val hpText: TextView by lazy { findViewById(R.id.detailed_fighter_stats_hp_text) }
    private val atkText: TextView by lazy { findViewById(R.id.detailed_fighter_stats_atk_text) }
    private val wisText: TextView by lazy { findViewById(R.id.detailed_fighter_stats_wis_text) }
    private val defText: TextView by lazy { findViewById(R.id.detailed_fighter_stats_def_text) }
    private val agiText: TextView by lazy { findViewById(R.id.detailed_fighter_stats_agi_text) }

    private val tribeImage: View by lazy { findViewById(R.id.detailed_fighter_tribe_ic) }
    private val tribeText: TextView by lazy { findViewById(R.id.detailed_fighter_tribe_text) }
    private val classImage: View by lazy { findViewById(R.id.detailed_fighter_class_ic) }
    private val classText: TextView by lazy { findViewById(R.id.detailed_fighter_class_text) }
    private val signImage: View by lazy { findViewById(R.id.detailed_fighter_sign_ic) }
    private val signText: TextView by lazy { findViewById(R.id.detailed_fighter_sign_text) }

    private val pedestralView: View by lazy { findViewById(R.id.detailed_fighter_pedestral) }
    private val rarityView: View by lazy { findViewById(R.id.detailed_fighter_rarity) }
    private val fighterImage: ImageView by lazy { findViewById(R.id.detailed_fighter_fighter_image) }
    private val weaponGroup: View by lazy { findViewById(R.id.detailed_fighter_weapon_group) }

    private val loreTitle: View by lazy { findViewById(R.id.detailed_fighter_lore_title) }
    private val loreArrow: View by lazy { findViewById(R.id.detailed_fighter_lore_arrow) }
    private val loreText: TextView by lazy { findViewById(R.id.detailed_fighter_lore_text) }
    private val skillTitle: View by lazy { findViewById(R.id.detailed_fighter_skill_title) }
    private val skillArrow: View by lazy { findViewById(R.id.detailed_fighter_skill_arrow) }
    private val skillName: TextView by lazy { findViewById(R.id.detailed_fighter_skill_name) }
    private val skillDescription: TextView by lazy { findViewById(R.id.detailed_fighter_skill_description) }
    private val skillInfoGroup: View by lazy { findViewById(R.id.detailed_fighter_skill_info_group) }

    var loreExpanded = true
    var skillExpanded = true
    var locked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_fighter)

        backArrow.onClick { onBackPressed() }
        toolbarHomeButton.onClick { startActivity(HomeActivity.navigate(this)) }

        loreTitle.setOnClickListener { toggleLoreExpanded() }
        skillTitle.setOnClickListener { toggleSkillExpanded() }

        fighterImage.animateUpDown()

        val fighter = intent.extras?.getParcelable(BUNDLE_FIGHTER) as Fighter?
        if (fighter != null) {
            loadCatalogFighter(fighter)
        } else {
            toolbarText.text = "Lord of Dolor"
            fuseButton.displayFuse()
            lockButton.setOnClickListener { toggleLock() }
        }
    }

    private fun loadCatalogFighter(fighter: Fighter) {
        lockButton.visibility = View.INVISIBLE
        levelView.setMaxLevel(60)
        levelView.setCurrentLevel(0)
        sefTextView.text = "0/1"

        loadMainInfo(fighter)
        loadStats(fighter.minStats)
        loadSocial(fighter.tribe, fighter.sign, fighter.fighterClass)
        loadLoreAndSkill(fighter)

        fuseButton.visibility = View.GONE
        moveButton.visibility = View.GONE
        replaceButton.visibility = View.GONE
    }

    private fun loadLoreAndSkill(fighter: Fighter) {
        loreText.text = fighter.lore
        skillName.text = "Skill name"
        skillDescription.text = "Do something, and then big boom boom"
    }

    private fun loadMainInfo(fighter: Fighter) {
        toolbarText.text = fighter.name
        pedestralView.background =
            ContextCompat.getDrawable(this, fighter.findPedestralId(resources, this, fighter.rarity, fighter.tribe))
        fighterImage.setDrawableAliasing(fighter.findImageId(resources, this))
        rarityView.background = ContextCompat.getDrawable(this, fighter.rarity.resIdBig)
        weaponGroup.visibility = View.GONE
    }

    private fun loadSocial(tribe: Tribe, sign: Sign, fighterClass: Class) {
        tribeImage.background = ContextCompat.getDrawable(this, tribe.resId)
        tribeText.setTextColor(ContextCompat.getColor(this, tribe.colorId))
        tribeText.text = tribe.name.capitalize()

        signImage.background = ContextCompat.getDrawable(this, sign.resId)
        signText.setTextColor(ContextCompat.getColor(this, sign.colorId))
        signText.text = sign.name.capitalize()

        classImage.background = ContextCompat.getDrawable(this, fighterClass.resId)
        classText.text = fighterClass.name.capitalize()
    }

    private fun loadStats(stats: FighterStats) {
        hpText.text = stats.hp.toString()
        atkText.text = stats.atk.toString()
        wisText.text = stats.wis.toString()
        defText.text = stats.def.toString()
        agiText.text = stats.agi.toString()
    }

    private fun toggleSkillExpanded() {
        if (skillExpanded) {
            skillInfoGroup.visibility = View.GONE
            skillArrow.background = ContextCompat.getDrawable(this, R.drawable.ic_triangle_bottom)
        } else {
            skillInfoGroup.visibility = View.VISIBLE
            skillArrow.background = ContextCompat.getDrawable(this, R.drawable.ic_triangle_top)
        }
        skillExpanded = !skillExpanded
    }

    private fun toggleLoreExpanded() {
        if (loreExpanded) {
            loreText.visibility = View.GONE
            loreArrow.background = ContextCompat.getDrawable(this, R.drawable.ic_triangle_bottom)
        } else {
            loreText.visibility = View.VISIBLE
            loreArrow.background = ContextCompat.getDrawable(this, R.drawable.ic_triangle_top)
        }

        loreExpanded = !loreExpanded
    }

    private fun toggleLock() {
        lockButton.setImageResource(
            if (locked) {
                R.drawable.selector_button_unlocked
            } else {
                R.drawable.selector_button_locked
            }
        )
        locked = !locked
    }

}