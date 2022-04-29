package com.rrat.demoroom.data.database

import androidx.annotation.WorkerThread
import com.rrat.demoroom.data.entities.ClientCrops
import com.rrat.demoroom.data.entities.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()
    val allClientCrops: Flow<List<ClientCrops>> = wordDao.getAlphabetizedClientsCrops()

    @WorkerThread
    suspend fun insertWord(word: Word){
        wordDao.insertWord(word)
    }

    @WorkerThread
    suspend fun insertClientCrops(clientCrops: ClientCrops){
        wordDao.insertClientCrop(clientCrops)
    }
}