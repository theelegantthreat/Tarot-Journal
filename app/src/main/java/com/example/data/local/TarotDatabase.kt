package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.JournalEntry

@Database(entities = [JournalEntry::class], version = 1, exportSchema = false)
abstract class TarotDatabase : RoomDatabase() {

    abstract fun tarotDao(): TarotDao

    companion object {
        @Volatile
        private var INSTANCE: TarotDatabase? = null

        fun getDatabase(context: Context): TarotDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TarotDatabase::class.java,
                    "tarot_journal_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
