package com.frogchair.android.feature.catalog.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.frogchair.android.R
import com.frogchair.android.common.ui.ErrorDialogFragment
import com.frogchair.android.common.utils.SpaceItemDecoration
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.feature.catalog.model.CatalogViewModel
import com.frogchair.android.feature.catalog.model.Fighter
import com.frogchair.android.feature.detailedfighter.DetailedFighterActivity
import com.frogchair.android.feature.home.ui.HomeActivity
import java.lang.ref.WeakReference

class CatalogActivity : AppCompatActivity(), CatalogAdapter.Listener {

    companion object {
        fun navigate(context: Context) = Intent(context, CatalogActivity::class.java)
    }

    private val toolbarText: TextView by lazy { findViewById(R.id.title_toolbar_text) }
    private val toolbarHomeButton: View by lazy { findViewById(R.id.title_home_button) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.catalog_recycler_view) }
    private val backArrow: View by lazy { findViewById(R.id.catalog_back_arrow) }

    private var page = 0
    private var columnNumber = 3
    private val viewModel: CatalogViewModel by viewModels()

    private lateinit var adapter: CatalogAdapter
    private val spaceBetweenItem by lazy {
        SpaceItemDecoration(
            WeakReference(this),
            R.dimen.fighter_list_item_margin,
            -1
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        backArrow.onClick { onBackPressed() }

        toolbarText.text = "CATALOG"
        toolbarHomeButton.onClick { startActivity(HomeActivity.navigate(this)) }

        initRecyclerView()
        loadFighters(page)
    }

    private fun initRecyclerView() {
        adapter = CatalogAdapter(this)
        adapter.listener = this

        recyclerView.layoutManager = GridLayoutManager(this, columnNumber).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int) = when (adapter.getItemViewType(position)) {
                    CatalogAdapter.TYPE_HEADER -> columnNumber
                    CatalogAdapter.TYPE_FOOTER -> columnNumber
                    else -> 1
                }
            }
            spanCount = columnNumber
        }
        recyclerView.adapter = adapter

        recyclerView.removeItemDecoration(spaceBetweenItem)
        recyclerView.addItemDecoration(spaceBetweenItem)
    }

    private fun loadFighters(targetPage: Int) {
        viewModel.getCatalog(targetPage).observe(this, { catalogResponse ->
            if (catalogResponse.response != null && !catalogResponse.response.isNullOrEmpty()) {
                page = targetPage
                adapter.data = catalogResponse.response
                adapter.notifyDataSetChanged()
            } else {
                displayError(targetPage, catalogResponse.error)
            }

        })
    }

    private fun displayError(targetPage: Int, error: String?) {
        ErrorDialogFragment.newInstance(error).apply {
            listener = object : ErrorDialogFragment.Listener {
                override fun onRetry() {
                    loadFighters(targetPage)
                }
            }
            show(supportFragmentManager, ErrorDialogFragment.TAG)
        }
    }

    override fun onFighterClick(fighter: Fighter) {
        startActivity(DetailedFighterActivity.navigate(this, fighter))
    }

    override fun onZoomIn() {
        if (columnNumber < 10) {
            ++columnNumber
            TransitionManager.beginDelayedTransition(recyclerView)
            (recyclerView.layoutManager as GridLayoutManager).spanCount = columnNumber
        } else {
            Toast.makeText(this, "You have great eyes !", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onZoomOut() {
        if (columnNumber > 1) {
            --columnNumber
            TransitionManager.beginDelayedTransition(recyclerView)
            (recyclerView.layoutManager as GridLayoutManager).spanCount = columnNumber
        } else {
            Toast.makeText(this, "Bruh I can't divide by zero", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPreviousPage() {
        if (page > 1) {
            loadFighters(page - 1)
        }
    }

    override fun onNextPage() {
        if (page < 44) {
            loadFighters(page + 1)
        }
    }

}