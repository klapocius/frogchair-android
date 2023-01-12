package com.frogchair.android.feature.fighterlist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frogchair.android.R
import com.frogchair.android.common.ui.FighterSimpleView
import com.frogchair.android.common.utils.onClick

class FighterListAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
        const val TYPE_FOOTER = 2
    }

    interface Listener {
        fun onFighterClick(position: Int)
        fun onWeaponClick(position: Int)
    }

    //        var data: List<Mission> = emptyList()
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        TYPE_HEADER -> HeaderViewHolder(View.inflate(context, R.layout.item_fighter_list_header, null))
        TYPE_FOOTER -> FooterViewHolder(View.inflate(context, R.layout.item_fighter_list_footer, null))
        else -> FighterViewHolder(FighterSimpleView(parent.context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        data.get(position).run {
//            holder.code.text = code
//            holder.title.text = title
//            holder.completed.visibility = if (completed) View.VISIBLE else View.GONE
//            holder.rewardImage.visibility = if (reward) View.VISIBLE else View.GONE
//            holder.rewardMore.visibility = if (reward) View.VISIBLE else View.GONE
//        }

//        holder.background.setOnClickListener {
//            listener?.onItemClick(position)
//        }
        if (holder is FighterViewHolder) {
            holder.fighterImage.onClick { listener?.onFighterClick(position) }
            holder.weaponImage.onClick { listener?.onWeaponClick(position) }
        }
    }

    override fun getItemCount(): Int {
        return 300 + 2
    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> TYPE_HEADER
        itemCount - 1 -> TYPE_FOOTER
        else -> TYPE_ITEM
    }

    class FighterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fighterImage: View = view.findViewById(R.id.view_fighter_simple_image)
        val weaponImage: View = view.findViewById(R.id.view_fighter_simple_weapon_image)
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val background: View = view.findViewById(R.id.item_mission_background)
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val background: View = view.findViewById(R.id.item_mission_background)
    }
}