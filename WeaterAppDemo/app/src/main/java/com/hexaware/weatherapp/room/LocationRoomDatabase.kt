package com.example.smslist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hexaware.weatherapp.model.LocationTable
import com.hexaware.weatherapp.room.LocationDao
import kotlinx.coroutines.CoroutineScope

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [LocationTable::class], version = 2)
abstract class LocationRoomDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao

    companion object {
        @Volatile
        private var INSTANCE: LocationRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): LocationRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationRoomDatabase::class.java,
                    "sms_database.db"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()

                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }


    }
}
