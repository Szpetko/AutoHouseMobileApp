package pl.autohouse.autohousemobileapp.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import pl.autohouse.autohousemobileapp.repository.SettingsRepository.PreferenceKeys.ipAddress
import java.io.IOException

const val PREFERENCE_NAME = "settings_preference"

class SettingsRepository(private val context: Context) {

    //Create Performance DataStore
    private val Context.dataStore by preferencesDataStore(name = PREFERENCE_NAME)


    //Preference Keys
    private object PreferenceKeys {
        val ipAddress = stringPreferencesKey("ipAddress")
        val latitude = doublePreferencesKey("latitude")
        val longitude = doublePreferencesKey("longitude")
        val cityName = stringPreferencesKey("cityName")
    }

    //IpAddress GET/SET
    val getIpAddress: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException){
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map { preferences ->
            val ipAddress = preferences[PreferenceKeys.ipAddress] ?: "none"
            ipAddress

        }

    suspend fun saveIpAddressDS(ipAddress: String){
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.ipAddress] = ipAddress
        }
    }

    //Location GET/SET
    val getLocation: Flow<Pair<Double, Double>> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException){
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map { preferences ->
            val latitude = preferences[PreferenceKeys.latitude] ?: 0.00
            val longitude = preferences[PreferenceKeys.longitude] ?: 0.00
            Pair(latitude,longitude)
        }

    suspend fun saveLocation(latitude: Double, longitude:Double){
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.latitude] = latitude
            preferences[PreferenceKeys.longitude] = longitude
        }
    }

    //cityName GET/SET
    val getCityName: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException){
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map { preferences ->
            val cityName = preferences[PreferenceKeys.cityName] ?: "none"
            cityName

        }

    suspend fun saveCityName(cityName: String){
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.cityName] = cityName
        }
    }

    //Necessary to pass the Repository
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: SettingsRepository? = null

        fun getInstance(context: Context): SettingsRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = SettingsRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }

}