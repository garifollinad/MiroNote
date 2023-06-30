package com.example.mironote.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mironote.R
import com.example.mironote.di.Injectable
import com.example.mironote.ui.menu.MenuActivity
import com.example.mironote.utils.Constants
import com.example.mironote.utils.widgets.DropDownView
import javax.inject.Inject

class BoardDetailFragment : Fragment(), Injectable {

    private var boardId: String = ""
    private var itemId: String ?= null
    private var itemText: String ?= null
    private var itemColor: String ?= null
    private lateinit var valueEdit: EditText
    private lateinit var buttonAdd: Button
    private lateinit var dropDown: DropDownView
    private lateinit var customToolbar: Toolbar

    companion object {
        fun newInstance(data: Bundle? = null): BoardDetailFragment =
            BoardDetailFragment().apply {
                arguments = data
            }
    }

    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(requireActivity() as MenuActivity).get(SharedViewModel::class.java)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        boardId = checkNotNull(args?.getString(Constants.BOARD_ID))
        itemId = args?.getString(Constants.ITEM_ID)
        itemText = args?.getString(Constants.ITEM_TEXT)
        itemColor = args?.getString(Constants.ITEM_COLOR)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_board_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setData()
    }

    private fun bindViews(view: View) = with(view) {
        valueEdit = findViewById(R.id.valueEdit)
        buttonAdd = findViewById(R.id.buttonAdd)
        dropDown = findViewById(R.id.dropDown)
        customToolbar = findViewById(R.id.customToolbar)
        valueEdit.setText(itemText)

        buttonAdd.setOnClickListener {
            if (itemId.isNullOrEmpty()) {
                viewModel.addStickyNote(
                    boardId = boardId,
                    noteText = valueEdit.text.toString(),
                    noteColor = checkNotNull(getSelectedColor()))
            } else {
                viewModel.updateStickyNode(
                    boardId = boardId,
                    itemId = itemId!!,
                    noteText = valueEdit.text.toString(),
                    noteColor = checkNotNull(getSelectedColor())
                )
            }

        }

        customToolbar.setOnClickListener {
            (requireActivity() as MenuActivity).onBackPressed()
        }

        val listOfColors = listOf(
            "Social (immediate)", "Social (enabling)", "Social (structural)",
            "Economic (immediate)", "Economic (enabling)", "Economic (structural)",
            "Environmental (immediate)", "Environmental (enabling)", "Environmental (structural)",
            "Individual (immediate)", "Individual (enabling)", "Individual (structural)",
            "Technical (immediate)", "Technical (enabling)", "Technical (structural)"
        )
        dropDown.setOptions(getDimension(), listOfColors)
    }

    private fun getSelectedColor(): String {
        val noteColor = when (dropDown.getSelectedOption()) {
            "Social (immediate)" -> "light_yellow"
            "Social (enabling)" -> "yellow"
            "Social (structural)" -> "orange"
            "Economic (immediate)" -> "light_pink"
            "Economic (enabling)" -> "dark_pink"
            "Economic (structural)" -> "violet"
            "Environmental (immediate)" -> "light_green"
            "Environmental (enabling)" -> "green"
            "Environmental (structural)" -> "dark_green"
            "Individual (immediate)" -> "gray"
            "Individual (enabling)" -> "red"
            "Individual (structural)" -> "cyan"
            "Technical (immediate)" -> "light_blue"
            "Technical (enabling)" -> "blue"
            "Technical (structural)" -> "dark_blue"
            else -> "#F0D352"
        }
        return noteColor
    }

    private fun getDimension(): String {
        val dimension = when (itemColor) {
            "light_yellow" -> "Social (immediate)"
            "yellow" -> "Social (enabling)"
            "orange" -> "Social (structural)"
            "light_pink" -> "Economic (immediate)"
            "dark_pink" -> "Economic (enabling)"
            "violet" -> "Economic (structural)"
            "light_green" -> "Environmental (immediate)"
            "green" -> "Environmental (enabling)"
            "dark_green" -> "Environmental (structural)"
            "gray" -> "Individual (immediate)"
            "red" -> "Individual (enabling)"
            "cyan" -> "Individual (structural)"
            "light_blue" -> "Technical (immediate)"
            "blue" -> "Technical (enabling)"
            "dark_blue" -> "Technical (structural)"
            else -> "Social (immediate)"
        }
        return dimension
    }

    private fun setData() {
        viewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is MainViewModel.Result.StickerError -> {
                    Toast.makeText(activity, result.error, Toast.LENGTH_LONG).show()
                }
                is MainViewModel.Result.StickerSuccess -> {
                    Toast.makeText(activity, "Sticker is added", Toast.LENGTH_LONG).show()
                    (requireActivity() as MenuActivity).onBackPressed()
                    sharedViewModel.getBoardItems(boardId = boardId)
                }
                is MainViewModel.Result.StickerDelete -> {}
                is MainViewModel.Result.BoardItems -> {}
                is MainViewModel.Result.Boards -> {}
                is MainViewModel.Result.Error -> {}
            }
        })

        sharedViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is SharedViewModel.SharedResult.BoardItems -> {}
                is SharedViewModel.SharedResult.Error -> {}
            }
        })
    }

}