package com.example.mironote.di

import com.example.mironote.api.Api
import com.example.mironote.repository.BoardRepository
import com.example.mironote.repository.BoardRepositoryImpl
import com.example.mironote.utils.PrefUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideHouseRepository(api: Api, prefUtils: PrefUtils): BoardRepository = BoardRepositoryImpl(api = api, prefUtils = prefUtils)

}