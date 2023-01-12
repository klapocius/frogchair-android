package com.frogchair.android.feature.fighterlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.frogchair.android.R
import com.frogchair.android.common.utils.SpaceItemDecoration
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.feature.detailedfighter.DetailedFighterActivity
import com.frogchair.android.feature.home.ui.HomeActivity
import java.lang.ref.WeakReference


class FighterListActivity : AppCompatActivity(), FighterListAdapter.Listener {

    companion object {
        private const val COLUMN_NUMBER = 4

        fun navigate(context: Context) = Intent(context, FighterListActivity::class.java)
    }

    private val toolbarText: TextView by lazy { findViewById(R.id.title_toolbar_text) }
    private val toolbarHomeButton: View by lazy { findViewById(R.id.title_home_button) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.fighter_list_recycler_view) }
    private val backArrow: View by lazy { findViewById(R.id.fighter_list_back_arrow) }

    private lateinit var adapter: FighterListAdapter
    private val spaceBetweenItem by lazy {
        SpaceItemDecoration(
            WeakReference(this),
            R.dimen.fighter_list_item_margin,
            COLUMN_NUMBER
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fighter_list)

        backArrow.onClick { onBackPressed() }

        toolbarText.text = "FIGHTERS"
        toolbarHomeButton.onClick { startActivity(HomeActivity.navigate(this)) }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = FighterListAdapter(this)
        adapter.listener = this

        recyclerView.layoutManager = GridLayoutManager(this, COLUMN_NUMBER).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int) = when (adapter.getItemViewType(position)) {
                    FighterListAdapter.TYPE_HEADER -> COLUMN_NUMBER
                    FighterListAdapter.TYPE_FOOTER -> COLUMN_NUMBER
                    else -> 1
                }
            }
            spanCount = COLUMN_NUMBER
        }
        recyclerView.adapter = adapter

        recyclerView.removeItemDecoration(spaceBetweenItem)
        recyclerView.addItemDecoration(spaceBetweenItem)
    }

    override fun onFighterClick(position: Int) {
        startActivity(DetailedFighterActivity.navigate(this))
    }

    override fun onWeaponClick(position: Int) {
    }

}