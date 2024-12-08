package com.jitesh.assignment_khushibaby.Module

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jitesh.assignment_khushibaby.Entity.MovieEntity
import com.jitesh.assignment_khushibaby.RoomDAO.MovieDao

// Room Database Entity
@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
