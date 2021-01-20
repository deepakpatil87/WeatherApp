package com.hexaware.weatherapp.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest

import androidx.test.runner.AndroidJUnit4
import com.example.smslist.room.LocationRoomDatabase
import com.google.common.truth.ExpectFailure.assertThat
import com.hexaware.weatherapp.model.LocationTable
import com.hexaware.weatherapp.room.LocationDao

import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class LocationDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: LocationRoomDatabase
    private lateinit var dao: LocationDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocationRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.locationDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertLocationItem() = runBlockingTest {
        val locationItem = LocationTable("Indore", "MP", "IN", "Rajwada", 22.797, 78.899, 2)

        dao.insert(locationItem)

        val allLocationItems = dao.getAllLocation().asLiveData()

            //  assertThat(allLocationItems).contains(locationItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val locationItem = LocationTable("Indore", "MP", "IN", "Rajwada", 22.797, 78.899, 2)
        dao.insert(locationItem)
        val allLocationItems = dao.getAllLocation().asLiveData()

      //  assertThat(allLocationItems).doesNotContain(shoppinlocationItemgItem)
    }


}














