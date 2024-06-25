package com.ecmocalc.ui.cannula

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ecmocalc.R
import com.ecmocalc.models.StaticValues


class StaticValuesListAdapter(
    private val staticValuesList: List<StaticValues>?
) : RecyclerView.Adapter<StaticValuesListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvValue: TextView = itemView.findViewById(R.id.tv_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.static_values_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val staticValue = staticValuesList?.get(position)
        holder.tvName.text = staticValue?.name
        holder.tvValue.text = staticValue?.value
    }

    override fun getItemCount(): Int {
        return staticValuesList?.size ?: 0
    }
}