package com.rrat.demoroom.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rrat.demoroom.data.entities.ClientCrops
import com.rrat.demoroom.data.entities.Word
import kotlinx.coroutines.flow.Flow


@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Query("SELECT * FROM client_crops_table ORDER BY client ASC")
    fun getAlphabetizedClientsCrops(): Flow<List<ClientCrops>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertClientCrop(clientCrops: ClientCrops)


    @Query("DELETE FROM word_table")
    suspend fun deleteAllWord()

    @Query("DELETE FROM client_crops_table")
    suspend fun deleteAllClientCrops()
}