package com.example.mironote.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        boardId = checkNotNull(args?.getString(Constants.BOARD_ID))
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

        buttonAdd.setOnClickListener {
            viewModel.addStickyNote(boardId = boardId, noteText = valueEdit.text.toString(), noteColor = checkNotNull(dropDown.getSelectedOption()))
        }

        customToolbar.setOnClickListener {
            (requireActivity() as MenuActivity).onBackPressed()
        }

        val listOfColors = listOf("green", "gray", "yellow", "pink", "violet", "blue", "red", "orange")
        dropDown.setOptions("green", listOfColors)
    }

    private fun setData() {
        viewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is MainViewModel.Result.StickerError -> {
                    Toast.makeText(activity, result.error, Toast.LENGTH_LONG).show()
                }
                is MainViewModel.Result.StickerSuccess -> {
                    valueEdit.text.clear()
                    Toast.makeText(activity, "Sticker is added", Toast.LENGTH_LONG).show()
                }
                is MainViewModel.Result.Boards -> {}
                is MainViewModel.Result.Error -> {}
            }
        })
    }

}