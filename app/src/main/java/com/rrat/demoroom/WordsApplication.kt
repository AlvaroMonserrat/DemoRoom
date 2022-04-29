package com.rrat.demoroom

import android.app.Application
import com.rrat.demoroom.data.database.WordRepository
import com.rrat.demoroom.data.database.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application(){

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { WordRoomDatabase.getDatabase(this@WordsApplication, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}