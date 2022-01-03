package com.example.musicant.mvvm.model.storage.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.musicant.mvvm.model.storage.room.entity.Album

@Database(entities = [Album::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }

    abstract fun getAlbumDao(): AlbumDao
}