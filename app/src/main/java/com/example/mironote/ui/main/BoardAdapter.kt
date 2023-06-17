package com.example.mironote.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mironote.R
import com.example.mironote.model.Board
import com.example.mironote.utils.DateUtil
import com.example.mironote.utils.TimeUtil

interface OnBoardDetailListener {
    fun onBoardClick(model: Board)
}

class BoardAdapter (
    private var onBoardDetailListener: OnBoardDetailListener
): RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    private var listBoards: List<Board> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): BoardViewHolder {
        return BoardViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_board_view, parent, false)
        )
    }

    override fun getItemCount(): Int = listBoards.size

    override fun onBindViewHolder(viewHolder: BoardViewHolder, position: Int) {
        viewHolder.bindView(listBoards.get(position))
    }

    inner class BoardViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        private lateinit var boardItem: ConstraintLayout
        private lateinit var boardName: TextView
        private lateinit var teamName: TextView
        private lateinit var boardDate: TextView
        private lateinit var boardImg: ImageView

        fun bindView(item: Board) = with(view) {
            boardItem = findViewById(R.id.boardItem)
            boardName = findViewById(R.id.boardName)
            teamName = findViewById(R.id.teamName)
            boardDate = findViewById(R.id.boardDate)
            boardImg = findViewById(R.id.boardImg)

            boardName.text = item.name
            teamName.text = item.team.name
            val date = DateUtil.convertIsoToDate(item.modifiedAt)
            val longTime = date.time
            boardDate.text = "modified " + TimeUtil(context = context).getTimeAgo(longTime)

            Glide.with(view)
                .load(item.picture.imageURL)
                .into(boardImg)

            boardItem.setOnClickListener {
                onBoardDetailListener.onBoardClick(item)
            }
        }
    }

    fun initBoards(list: List<Board>) {
        listBoards = checkNotNull(list)
        notifyDataSetChanged()
    }
}