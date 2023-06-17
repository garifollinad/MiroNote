package com.example.mironote.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.mironote.utils.PrefUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Singleton
    @Provides
    internal fun providePreference(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun providePrefUtils(preferences: SharedPreferences): PrefUtils = PrefUtils(preferences)

}