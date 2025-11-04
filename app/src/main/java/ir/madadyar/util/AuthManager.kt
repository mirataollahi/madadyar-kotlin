package ir.madadyar.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import ir.madadyar.data.api.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

object AuthManager {
    private const val TOKEN_KEY = "token"
    
    suspend fun saveToken(context: Context, token: String) {
        val tokenKey = stringPreferencesKey(TOKEN_KEY)
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = "Bearer $token"
        }
        ApiClient.setToken("Bearer $token")
    }
    
    fun getToken(context: Context): Flow<String?> {
        val tokenKey = stringPreferencesKey(TOKEN_KEY)
        return context.dataStore.data.map { preferences ->
            preferences[tokenKey]
        }
    }
    
    suspend fun getTokenSync(context: Context): String? {
        val tokenKey = stringPreferencesKey(TOKEN_KEY)
        return try {
            context.dataStore.data.first()[tokenKey]
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun clearToken(context: Context) {
        val tokenKey = stringPreferencesKey(TOKEN_KEY)
        context.dataStore.edit { preferences ->
            preferences.remove(tokenKey)
        }
        ApiClient.setToken(null)
    }
    
    suspend fun isAuthenticated(context: Context): Boolean {
        val token = getTokenSync(context)
        return token != null && token.isNotEmpty()
    }
}
