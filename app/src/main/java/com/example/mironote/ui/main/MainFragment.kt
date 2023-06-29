package com.example.mironote.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
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
import com.example.mironote.ui.info.InfoFragment
import com.example.mironote.ui.menu.MenuActivity
import com.example.mironote.utils.Constants
import com.example.mironote.utils.Screen
import javax.inject.Inject

class MainFragment : Fragment(), Injectable {

    private lateinit var recyclerView: RecyclerView
    private lateinit var qrcodeScan: ImageView

    companion object {
        fun newInstance(data: Bundle? = null): MainFragment =
            MainFragment().apply {
                arguments = data
            }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setData()
        setAdapter()
    }

    private fun bindViews(view: View) = with(view) {
        recyclerView = findViewById(R.id.recyclerView)
        qrcodeScan = findViewById(R.id.qrcodeScan)
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

        qrcodeScan.setOnClickListener {
            (requireActivity() as MenuActivity).navigateTo(
                fragment = InfoFragment.newInstance(),
                tag = Screen.INFO_PAGE.name,
                addToStack = false
            )
        }
    }

    private val onBoardDetailListener: OnBoardDetailListener = object : OnBoardDetailListener {
        override fun onBoardClick(model: Board) {
            val bundle = bundleOf(Constants.BOARD_ID to model.id)
            (requireActivity() as MenuActivity).navigateTo(
                fragment = BoardItemsFragment.newInstance(bundle),
                tag = Screen.BOARD_ITEMS.name,
                addToStack = true
            )
        }
    }

    private val boardAdapter by lazy {
        BoardAdapter(onBoardDetailListener)
    }

    private fun setAdapter() {
        recyclerView.adapter = boardAdapter
    }

    private fun setData() {
        viewModel.getBoards()
        viewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is MainViewModel.Result.Boards -> {
                    boardAdapter.initBoards(result.boardsList)
                }
                is MainViewModel.Result.BoardItems -> {}
                is MainViewModel.Result.StickerSuccess -> {}
                is MainViewModel.Result.StickerDelete -> {}
                is MainViewModel.Result.StickerError -> {}
                is MainViewModel.Result.Error -> {
                    Toast.makeText(activity, "token is expired", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}