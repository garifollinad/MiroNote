package com.example.mironote.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mironote.R
import com.example.mironote.di.Injectable
import com.example.mironote.ui.info.InfoFragment
import com.example.mironote.ui.main.MainFragment
import com.example.mironote.utils.PrefUtils
import com.example.mironote.utils.Screen
import com.example.mironote.utils.changeFragment
import javax.inject.Inject

class MenuFragment : Fragment(), Injectable {

    @Inject
    lateinit var prefUtils: PrefUtils

    companion object {
        fun newInstance(data: Bundle? = null): MenuFragment =
            MenuFragment().apply {
                arguments = data
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            if (prefUtils.getDataString(PrefUtils.TOKEN).isNotEmpty()) {
                changeFragment(
                    R.id.navHostContainer,
                    MainFragment.newInstance(arguments),
                    Screen.MAIN_PAGE.name
                )
            } else {
                changeFragment(
                    R.id.navHostContainer,
                    InfoFragment.newInstance(arguments),
                    Screen.INFO_PAGE.name
                )
            }
        }
    }
}