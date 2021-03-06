package com.tinf18ai2.vorlesungsplan.ui

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tinf18ai2.vorlesungsplan.R
import com.tinf18ai2.vorlesungsplan.models.VorlesungsplanItem
import com.tinf18ai2.vorlesungsplan.ui.MainActivity.Companion.LOG
import kotlinx.android.synthetic.main.recycler_view_item_layout.view.*

/**
 * This is the ViewHolder for all Vorlesungsplantag
 */
class ViewHolderVorlesungsplanItem(
    view: View,
    var context: Context
) :
    RecyclerView.ViewHolder(view) {

    fun create() { }

    fun bind(item: VorlesungsplanItem) {
        // data
        itemView.textViewTitle.text = item.title
        itemView.textViewTime.text = item.time
        itemView.textViewRoom.text = item.description
        itemView.progressBar.progress = item.progress

        // progressBar style color
        val progress = item.progress
        when {
            progress <= 20 -> itemView.progressBar.progressTintList =
                ColorStateList.valueOf(context.getColor(R.color.progress_long))
            progress >= 80 -> itemView.progressBar.progressTintList =
                ColorStateList.valueOf(context.getColor(R.color.progress_short))
            progress == 100 -> itemView.progressBar.progressTintList =
                ColorStateList.valueOf(context.getColor(R.color.progress_done))
            else -> itemView.progressBar.progressTintList =
                ColorStateList.valueOf(context.getColor(R.color.progress_mid))
        }
    }

}

class RecyclerViewAdapterVorlesungsplanDay(
    val items: List<VorlesungsplanItem>,
    val context: Context
) :
    RecyclerView.Adapter<ViewHolderVorlesungsplanItem>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVorlesungsplanItem {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.recycler_view_item_layout, parent, false)
        val viewHolder = ViewHolderVorlesungsplanItem(view, context)
        viewHolder.create()
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolderVorlesungsplanItem, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

}
