package com.test.weatherinfo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.weatherinfo.data.dao.HistoryScreenDao
import com.test.weatherinfo.data.model.requestModel.LocationHistoryModel
import com.test.weatherinfo.data.remote.Constants.DATABASE_NAME


@Database(
    entities = [LocationHistoryModel::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun historyScreenDao(): HistoryScreenDao


    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

}
