package com.example.c323_project_6
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Represents the main database for the application using Room persistence library
 * It contains a table for `Note` entities
 */
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase()
{
    /**
     * Provides access to the NoteDao for database operations on the Note table
     * @return The Data Access Object
     */
    abstract fun noteDao(): NoteDao

    companion object
    {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        /**
         * Returns an instance of the NoteDatabase
         * @param context The application context, used to get the application's context for the database builder
         * @return The singleton instance of the NoteDatabase
         */
        fun getDatabase(context: Context): NoteDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}