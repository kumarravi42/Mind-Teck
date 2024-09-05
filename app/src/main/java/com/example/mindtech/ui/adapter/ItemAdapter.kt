package com.example.mindtech.ui.adapter

import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mindtech.R
import com.example.mindtech.data.model.ListItem

class ItemAdapter(private val itemList: List<ListItem>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(), Filterable {

    private var filteredItems = itemList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = filteredItems[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.textView.text = item.text
        holder.textView1.text = item.text1
    }

    override fun getItemCount(): Int = filteredItems.size

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imagePic)
        val textView: TextView = itemView.findViewById(R.id.itemTitle)
        val textView1: TextView = itemView.findViewById(R.id.itemSubTitle)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchText = constraint?.toString()?.lowercase()
                filteredItems = if (searchText.isNullOrEmpty()) {
                    itemList.toMutableList()
                } else {
                    itemList.filter {
                        it.text.lowercase().contains(searchText) ||
                                it.text1.lowercase().contains(searchText)
                    }.toMutableList()
                }
                val filterResults = FilterResults()
                filterResults.values = filteredItems
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as MutableList<ListItem>
                notifyDataSetChanged()
            }
        }
    }
}
