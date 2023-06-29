package com.example.mironote.ui.main

import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mironote.R
import com.example.mironote.model.StickyNote

interface OnItemListener {
    fun onItemUpdateClick(id: String, noteText: String, noteColor: String)
    fun onItemDelete(id: String)
}

class ItemAdapter (
    private var onItemListener: OnItemListener
): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var listItems: List<StickyNote> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_view, parent, false)
        )
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        viewHolder.bindView(listItems.get(position))
    }

    inner class ItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        private lateinit var boardItem: ConstraintLayout
        private lateinit var boardName: TextView
        private lateinit var delete: ImageView
        private lateinit var boardImg: ImageView

        fun bindView(item: StickyNote) = with(view) {
            boardItem = findViewById(R.id.boardItem)
            boardName = findViewById(R.id.boardName)
            boardImg = findViewById(R.id.boardImg)
            delete = findViewById(R.id.delete)

            boardName.text = item.data.content
            val noteColor = when (item.style.fillColor) {
                "green" -> "#D4E17C"
                "gray" -> "#F6F7F9"
                "yellow" -> "#F0D352"
                "pink" -> "#E1A2C1"
                "violet" -> "#C0A3D0"
                "blue" -> "#89D6F6"
                "red" -> "#E5A2A9"
                "orange" -> "#F1A25B"
                else -> "#F0D352"
            }

            boardImg.getDrawable().setColorFilter(Color.parseColor(noteColor), PorterDuff.Mode.SRC_OVER)

            boardItem.setOnClickListener {
                onItemListener.onItemUpdateClick(id = item.id, noteText = item.data.content, noteColor = item.style.fillColor)
            }

            delete.setOnClickListener {
                onItemListener.onItemDelete(id = item.id)
            }
        }
    }

    fun initItems(list: List<StickyNote>) {
        listItems = checkNotNull(list)
        notifyDataSetChanged()
    }
}