package com.ecmoclinicalcalculator.ui.cannula

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.innovativeecmo.ecmoclinicalcalculator.R
import com.innovativeecmo.ecmoclinicalcalculator.models.StaticValues


class TargetBloodFlowListAdapter(
    private val staticValuesList: List<StaticValues>?
) : RecyclerView.Adapter<TargetBloodFlowListAdapter.ViewHolder>() {

    var mContext: Context? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSelection(selectedValue: String?) {
        if (!selectedValue.isNullOrEmpty()) {
            staticValuesList?.forEach { item ->
                if (item.value == selectedValue) {
                    item.isSelected = true
                }else{
                    item.isSelected = false
                }
            }
            notifyDataSetChanged()
        }
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

        if (staticValue?.isSelected == true) {
            holder.tvName.setTextAppearance(R.style.Result)
        }else{
            holder.tvName.setTextAppearance(R.style.DataText)
        }
    }

    override fun getItemCount(): Int {
        return staticValuesList?.size ?: 0
    }
}