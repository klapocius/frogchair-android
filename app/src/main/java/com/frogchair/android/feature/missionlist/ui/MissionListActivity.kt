package com.frogchair.android.feature.missionlist.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frogchair.android.R
import com.frogchair.android.common.ui.ErrorDialogFragment
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.feature.band.BandActivity
import com.frogchair.android.feature.home.ui.HomeActivity
import com.frogchair.android.feature.missionlist.domain.Mission
import com.frogchair.android.feature.missionlist.domain.MissionListViewModel


class MissionListActivity : AppCompatActivity(), MissionListAdapter.Listener {

    companion object {
        private const val ARG_MISSION = "ARG_MISSION"
        private const val ARG_HEADER = "ARG_HEADER"

        fun navigate(context: Context, missions: Mission? = null, headerText: String? = null) =
            Intent(context, MissionListActivity::class.java).apply {
                missions?.let {
                    putExtra(ARG_MISSION, it)
                    putExtra(ARG_HEADER, headerText)
                }
            }
    }

    private val homeButton: View by lazy { findViewById(R.id.title_home_button) }
    private val toolbarText: TextView by lazy { findViewById(R.id.title_toolbar_text) }
    private val subtoolbarText: TextView by lazy { findViewById(R.id.mission_list_subtoolbar_text) }
    private val chapterChoice: Spinner by lazy { findViewById(R.id.mission_list_chapter_choice) }
    private val background: View by lazy { findViewById(R.id.mission_list_background) }
    private val welcomeBackView: View by lazy { findViewById(R.id.mission_list_welcome_back) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.mission_list_recycler_view) }
    private val backArrowView: View by lazy { findViewById(R.id.mission_list_back_arrow) }

    private lateinit var adapter: MissionListAdapter
    private val headerText by lazy { intent.extras?.getString(ARG_HEADER) }
    private val chosenMission by lazy { intent.extras?.getParcelable(ARG_MISSION) as Mission? }
    private var chapterList: List<Mission> = emptyList()
    private var currentChapter = 0
    private val viewModel: MissionListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission_list)

        backArrowView.onClick { onBackPressed() }
        homeButton.onClick { goToHome() }
        initRecyclerView()

        fetchData()
    }

    private fun goToHome() {
        startActivity(HomeActivity.navigate(this))
        finish()
    }

    private fun fetchData() {
        if (chosenMission != null) {
            displaySubissions()
            displayList(chosenMission?.missions!!)
        } else {
            viewModel.getChapters().observe(this) { missionResponse ->
                if (missionResponse.response != null) {
                    chapterList = missionResponse.response

                    val chapterTitleArray = chapterList.map { it.code + ". " + it.title }.toTypedArray()
                    displayChapterDropdown(chapterTitleArray)

                    displayChapter()
                } else {
                    displayError(missionResponse.error)
                }
            }
        }
    }

    private fun displayError(error: String?) {
        ErrorDialogFragment.newInstance(error).apply {
            listener = object : ErrorDialogFragment.Listener {
                override fun onRetry() {
                    fetchData()
                }
            }
            show(supportFragmentManager, ErrorDialogFragment.TAG)
        }
    }

    private fun displayChapterDropdown(items: Array<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        chapterChoice.adapter = adapter
        chapterChoice.visibility = View.VISIBLE
        chapterChoice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentChapter = position
                displayChapter()
            }
        }
    }

    private fun displayList(missions: List<Mission>) {
        adapter.data = missions
        adapter.notifyDataSetChanged()
    }

    private fun displayChapter() {
        val animeFadeOut = AnimationUtils.loadAnimation(applicationContext, R.anim.anim_fade_out_in)
        welcomeBackView.visibility = View.VISIBLE
        welcomeBackView.startAnimation(animeFadeOut)
        background.visibility = View.VISIBLE
        //TODO set background

        chapterList[currentChapter].missions?.let { displayList(it) }
    }

    private fun displaySubissions() {
        toolbarText.text = headerText
        subtoolbarText.visibility = View.VISIBLE
        subtoolbarText.text = ">> ${chosenMission!!.code} ~ ${chosenMission!!.title} ~"
    }

    private fun initRecyclerView() {
        adapter = MissionListAdapter(this)
        adapter.listener = this

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        if (chosenMission == null) {
            val chapterTitle = "Chapter " + chapterList[currentChapter].code + ": " + chapterList[currentChapter].title
            startActivity(navigate(this, chapterList[currentChapter].missions?.get(position), chapterTitle))
        } else {
            startActivity(BandActivity.navigate(this, true))
        }
    }

    override fun onRewardClick(position: Int) {
        //TODO plug enum
        MissionRewardDialogFragment.newInstance("").show(supportFragmentManager, MissionRewardDialogFragment.TAG)
    }

}