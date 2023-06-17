package com.example.mironote.di

import com.example.mironote.ui.info.InfoFragment
import com.example.mironote.ui.main.BoardDetailFragment
import com.example.mironote.ui.main.MainFragment
import com.example.mironote.ui.menu.MenuFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMenuFragment(): MenuFragment

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeBoardDetailFragment(): BoardDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeInfoFragment(): InfoFragment
}