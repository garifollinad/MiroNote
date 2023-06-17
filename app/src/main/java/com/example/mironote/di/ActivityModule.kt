package com.example.mironote.di

import com.example.mironote.ui.menu.MenuActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMenuActivity(): MenuActivity

}