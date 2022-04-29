package com.rrat.demoroom.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rrat.demoroom.data.entities.ClientCrops
import com.rrat.demoroom.data.entities.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Word::class, ClientCrops::class], version = 2, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao() : WordDao

    companion object{
        //Singleton
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class WordDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback()
    {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                wordRoomDatabase -> scope.launch { populateDatabase(wordRoomDatabase.wordDao()) }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao){
            //Delete all content here.
            wordDao.deleteAllWord()

            // Add sample words.
            var word = Word(word = "Hello")
            wordDao.insertWord(word)
            word = Word(word = "World!")
            wordDao.insertWord(word)


            wordDao.deleteAllClientCrops()

            var clientCrops = ClientCrops("Pedro", "PAPA")
            wordDao.insertClientCrop(clientCrops)
            clientCrops = ClientCrops("Carlos", "PAPA")
            wordDao.insertClientCrop(clientCrops)
            clientCrops = ClientCrops("Paula", "PAPA")
            wordDao.insertClientCrop(clientCrops)
            clientCrops = ClientCrops("Maria", "PAPA")
            wordDao.insertClientCrop(clientCrops)
        }
    }
}