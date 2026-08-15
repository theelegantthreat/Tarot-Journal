package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.JournalEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface TarotDao {

    @Query("SELECT * FROM tarot_journal_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<JournalEntry>>

    @Query("SELECT * FROM tarot_journal_entries WHERE id = :id LIMIT 1")
    suspend fun getEntryById(id: Long): JournalEntry?

    @Query("SELECT * FROM tarot_journal_entries WHERE entryType = :type ORDER BY timestamp DESC")
    fun getEntriesByType(type: String): Flow<List<JournalEntry>>

    @Query("SELECT * FROM tarot_journal_entries WHERE timestamp >= :sinceTimestamp ORDER BY timestamp ASC")
    fun getEntriesSince(sinceTimestamp: Long): Flow<List<JournalEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: JournalEntry): Long

    @Update
    suspend fun updateEntry(entry: JournalEntry)

    @Delete
    suspend fun deleteEntry(entry: JournalEntry)

    @Query("DELETE FROM tarot_journal_entries WHERE id = :id")
    suspend fun deleteEntryById(id: Long)

    @Query("SELECT COUNT(*) FROM tarot_journal_entries")
    fun getEntryCount(): Flow<Int>

    @Query("SELECT * FROM tarot_journal_entries WHERE isSyncedToCloud = 0")
    suspend fun getUnsyncedEntries(): List<JournalEntry>
}
