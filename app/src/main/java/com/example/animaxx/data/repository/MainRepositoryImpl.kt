package com.example.animaxx.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.animaxx.data.data_source.exploreList
import com.example.animaxx.data.data_source.list
import com.example.animaxx.data.data_source.recentlySearchedList
import com.example.animaxx.data.data_source.searchAnimeByKeyword
import com.example.animaxx.data.data_source.starredList
import com.example.animaxx.data.data_source.topRatedList
import com.example.animaxx.domain.model.Anime
import com.example.animaxx.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MainRepositoryImpl(context: Context) : MainRepository {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val mDataStore: DataStore<Preferences> = context.dataStore


    override val isAppDark: Flow<Boolean>
        get() = mDataStore.data.map { preferences ->
            val state = preferences[THEME_STATE] ?: false
            state
        }

    companion object {
        private val THEME_STATE = booleanPreferencesKey("is_app_dark")
    }


    override suspend fun toggleLoginState() {
        mDataStore.edit { preferences ->
            val state = preferences[THEME_STATE] ?: false
            preferences[THEME_STATE] = !state
        }
    }


    override suspend fun getTopRatedList(): Flow<List<Anime>> = flow {
        emit(topRatedList)
    }

    override suspend fun getRecentlySearchedList(): Flow<List<Anime>> = flow {
        emit(recentlySearchedList)
    }

    override suspend fun getExploreList(): Flow<List<Anime>> = flow {
        emit(exploreList)
    }

    override suspend fun getAnimeFromKeyword(keyword: String): Flow<List<Anime>> = flow {
        emit(searchAnimeByKeyword(list, keyword))
    }

    override suspend fun getStarredList(): Flow<List<Anime>> = flow {
        emit(starredList)
    }
}