package pl.autohouse.autohousemobileapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.autohouse.autohousemobileapp.repository.Repository
import pl.autohouse.autohousemobileapp.repository.SettingsRepository

class MainViewModelFactory(
    private val settingsRepository: SettingsRepository,
    private val repository: Repository
    ):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(settingsRepository, repository) as T
    }
}