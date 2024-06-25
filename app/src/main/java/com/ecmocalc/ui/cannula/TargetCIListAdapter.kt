package com.ecmocalc.ui.cannula

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ecmocalc.R
import com.ecmocalc.models.StaticValues


class TargetCIListAdapter(
    private val staticValuesList: List<StaticValues>?,
    private val onItemListener: SetTargetCIValue
) : RecyclerView.Adapter<TargetCIListAdapter.ViewHolder>() {

    var mContext:Context?=null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSelection(selectedValue:String) {
        staticValuesList?.forEach { item ->
            if (item.value == selectedValue){
                item.isSelected = true
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.target_blood_flow_item, parent, false)
        mContext = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val staticValue = staticValuesList?.get(position)
        holder.tvName.text = staticValue?.name

        holder.itemView.setOnClickListener {
            onItemListener.onValueClick(staticValue,position)
        }
    }

    override fun getItemCount(): Int {
        return staticValuesList?.size ?: 0
    }

    interface SetTargetCIValue {
        fun onValueClick(value: StaticValues?, position: Int)
    }
}