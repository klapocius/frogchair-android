package com.frogchair.android.feature.missionlist.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.frogchair.android.R
import com.frogchair.android.common.utils.onClick
import com.frogchair.android.feature.missionlist.domain.Mission

class MissionListAdapter(private val context: Context) :
    RecyclerView.Adapter<MissionListAdapter.MissionItemViewHolder>() {

    interface Listener {
        fun onItemClick(position: Int)
        fun onRewardClick(position: Int)
    }

    var data: List<Mission> = emptyList()
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionItemViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_mission, parent, false)
        return MissionItemViewHolder(item)
    }

    override fun onBindViewHolder(holder: MissionItemViewHolder, position: Int) {
        data.get(position).run {
            holder.code.text = code
            holder.title.text = title
            holder.completed.visibility = if (isCompleted()) View.VISIBLE else View.GONE
            holder.locked.visibility = if (isLocked()) View.VISIBLE else View.GONE
            holder.lockedBackground.visibility = if (isLocked()) View.VISIBLE else View.GONE
            holder.rewardImage.visibility = if (rewardPreview != null) View.VISIBLE else View.GONE
            holder.rewardMore.visibility = if (rewardPreview != null) View.VISIBLE else View.GONE
            //TODO set rewardImage from enum

            if (!isLocked()) {
                holder.background.onClick {
                    listener?.onItemClick(position)
                }
            } else {
                holder.background.setOnClickListener(null)
            }
            if (rewardPreview != null) {
                holder.rewardClickArea.onClick {
                    listener?.onRewardClick(position)
                }
            } else {
                holder.rewardClickArea.setOnClickListener(null)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MissionItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val background: View = view.findViewById(R.id.item_mission_background)
        val code: TextView = view.findViewById(R.id.item_mission_code)
        val title: TextView = view.findViewById(R.id.item_mission_title)
        val completed: View = view.findViewById(R.id.item_mission_completed)
        val locked: View = view.findViewById(R.id.item_mission_locked)
        val lockedBackground: View = view.findViewById(R.id.item_mission_locked_background)
        val rewardImage: View = view.findViewById(R.id.item_mission_reward)
        val rewardMore: View = view.findViewById(R.id.item_mission_reward_more)
        val rewardClickArea: View = view.findViewById(R.id.item_mission_reward_click_area)
    }
}