package com.frogchair.android.feature.catalog.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.frogchair.android.R
import com.frogchair.android.common.ui.FighterSimpleView
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.feature.catalog.model.Fighter

class CatalogAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
        const val TYPE_FOOTER = 2
    }

    interface Listener {
        fun onFighterClick(fighter: Fighter)
        fun onZoomIn()
        fun onZoomOut()
        fun onPreviousPage()
        fun onNextPage()
    }

    var data: List<Fighter> = emptyList()
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        TYPE_HEADER -> HeaderViewHolder(View.inflate(context, R.layout.item_fighter_list_header, null))
        TYPE_FOOTER -> FooterViewHolder(View.inflate(context, R.layout.item_fighter_list_footer, null))
        else -> FighterViewHolder(FighterSimpleView(parent.context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.fighterCurrentNumberView.text = data.size.toString()
            holder.fighterMaxNumberView.text = "/" + data.size.toString()
            holder.secondText.visibility = View.VISIBLE

            holder.plusView.visibility = View.VISIBLE
            holder.plusView.onClick { listener?.onZoomOut() }

            holder.minusView.visibility = View.VISIBLE
            holder.minusView.onClick { listener?.onZoomIn() }

            holder.previousButton.onClick { listener?.onPreviousPage() }
            holder.nextButton.onClick { listener?.onNextPage() }
        }

        if (holder is FighterViewHolder) {
            (holder.itemView as FighterSimpleView).apply {
                displayOnlyRarityAndSign()
                init(data[getRealPosition(position)])
            }
            holder.fighterImage.onClick { listener?.onFighterClick(data[getRealPosition(position)]) }
        }

        if (holder is FooterViewHolder) {
            holder.previousButton.onClick { listener?.onPreviousPage() }
            holder.nextButton.onClick { listener?.onNextPage() }
        }
    }

    override fun getItemCount() = data.size + 2

    override fun getItemViewType(position: Int) = when (position) {
        0 -> TYPE_HEADER
        itemCount - 1 -> TYPE_FOOTER
        else -> TYPE_ITEM
    }

    private fun getRealPosition(position: Int) = position - 1 //removes header index

    class FighterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fighterImage: View = view.findViewById(R.id.view_fighter_simple_image)
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fighterCurrentNumberView: TextView = view.findViewById(R.id.fighter_list_current_number)
        val fighterMaxNumberView: TextView = view.findViewById(R.id.fighter_list_max_number)
        val secondText: View = view.findViewById(R.id.fighter_list_header_second_text)
        val plusView: View = view.findViewById(R.id.fighter_list_header_plus)
        val minusView: View = view.findViewById(R.id.fighter_list_header_minus)
        val previousButton: View = view.findViewById(R.id.fighter_list_header_page_previous)
        val nextButton: View = view.findViewById(R.id.fighter_list_header_page_next)
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val previousButton: View = view.findViewById(R.id.fighter_list_footer_page_previous)
        val nextButton: View = view.findViewById(R.id.fighter_list_footer_page_next)
    }
}