package com.example.mironote.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mironote.R
import com.example.mironote.di.Injectable
import com.example.mironote.model.Board
import com.example.mironote.ui.menu.MenuActivity
import com.example.mironote.utils.Constants
import com.example.mironote.utils.Screen
import javax.inject.Inject

class BoardItemsFragment : Fragment(), Injectable {

    private var boardId: String = ""
    private lateinit var recyclerView: RecyclerView
    private lateinit var addSticker: ImageView
    private lateinit var customToolbar: Toolbar

    companion object {
        fun newInstance(data: Bundle? = null): BoardItemsFragment =
            BoardItemsFragment().apply {
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_board_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setData()
        setAdapter()
    }

    private fun bindViews(view: View) = with(view) {
        recyclerView = findViewById(R.id.recyclerView)
        addSticker = findViewById(R.id.addSticker)
        customToolbar = findViewById(R.id.customToolbar)

        customToolbar.setOnClickListener {
            (requireActivity() as MenuActivity).onBackPressed()
        }

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.getContext(),
            layoutManager.getOrientation()
        )
        ContextCompat.getDrawable(context, R.drawable.divider_20dp)?.let { drawable ->
            dividerItemDecoration.setDrawable(drawable)
        }
        recyclerView.addItemDecoration(dividerItemDecoration)

        addSticker.setOnClickListener {
            val bundle = bundleOf(Constants.BOARD_ID to boardId)
            (requireActivity() as MenuActivity).navigateTo(
                fragment = BoardDetailFragment.newInstance(bundle),
                tag = Screen.BOARD_DETAIL.name,
                addToStack = true
            )
        }
    }

    private val onItemListener: OnItemListener = object : OnItemListener {
        override fun onItemUpdateClick(id: String, noteText: String, noteColor: String) {
            val bundle = bundleOf(Constants.BOARD_ID to boardId,
                Constants.ITEM_ID to id, Constants.ITEM_TEXT to noteText,
                Constants.ITEM_COLOR to noteColor)
            (requireActivity() as MenuActivity).navigateTo(
                fragment = BoardDetailFragment.newInstance(bundle),
                tag = Screen.BOARD_DETAIL.name,
                addToStack = true
            )
        }

        override fun onItemDelete(id: String) {
            viewModel.deleteStickyNode(boardId = boardId, itemId = id)
        }
    }

    private val itemAdapter by lazy {
        ItemAdapter(onItemListener)
    }

    private fun setAdapter() {
        recyclerView.adapter = itemAdapter
    }

    private fun setData() {
        viewModel.getBoardItems(boardId = boardId)
        viewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is MainViewModel.Result.BoardItems -> {
                    itemAdapter.initItems(result.itemsList.filter { !it.data.content.isNullOrEmpty() })
                }
                is MainViewModel.Result.StickerDelete -> {
                    Toast.makeText(activity, "Sticker is deleted", Toast.LENGTH_LONG).show()
                    viewModel.getBoardItems(boardId = boardId)
                }
                is MainViewModel.Result.StickerError -> {}
                is MainViewModel.Result.StickerSuccess -> {}
                is MainViewModel.Result.Boards -> {}
                is MainViewModel.Result.Error -> {}
            }
        })

        sharedViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is SharedViewModel.SharedResult.BoardItems -> {
                    itemAdapter.initItems(result.itemsList.filter { !it.data.content.isNullOrEmpty() })
                }
                is SharedViewModel.SharedResult.Error -> {}
            }
        })
    }

}