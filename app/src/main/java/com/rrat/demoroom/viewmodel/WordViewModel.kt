package com.rrat.demoroom.viewmodel

import androidx.lifecycle.*
import com.rrat.demoroom.data.database.WordRepository
import com.rrat.demoroom.data.entities.ClientCrops
import com.rrat.demoroom.data.entities.Word
import kotlinx.coroutines.launch

class WordViewModel (private val repository: WordRepository) : ViewModel() {

    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()
    val allClientCrops: LiveData<List<ClientCrops>> = repository.allClientCrops.asLiveData()

    fun insertWord(word: Word) = viewModelScope.launch {
        repository.insertWord(word)
    }
    fun insertClientCrops(clientCrops: ClientCrops) = viewModelScope.launch {
        repository.insertClientCrops(clientCrops)
    }
}

class WordViewModelFactory(private val repository: WordRepository): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}