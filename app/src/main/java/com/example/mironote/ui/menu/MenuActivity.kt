package com.example.mironote.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mironote.R
import com.example.mironote.utils.Screen
import com.example.mironote.utils.changeActFragment
import dagger.android.support.DaggerAppCompatActivity

class MenuActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigateTo(
                fragment = MenuFragment.newInstance(intent.extras),
                tag = Screen.MENU.name
            )
        }
    }

    //navigation to screens
    fun navigateTo(
        fragment: Fragment,
        tag: String,
        addToStack: Boolean = false
    ) {
        changeActFragment(
            layoutId = R.id.menuContainer,
            fragment = fragment,
            tagFragmentName = tag,
            addToStack = addToStack
        )
    }
}