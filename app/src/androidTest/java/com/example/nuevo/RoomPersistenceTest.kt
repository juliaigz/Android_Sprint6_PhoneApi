package com.example.nuevo

import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.nuevo.modelo.local.PhoneDao
import com.example.nuevo.modelo.local.database.PhoneRoomDatabase
import com.example.nuevo.modelo.local.entities.DetailsPhoneEntity
import com.example.nuevo.modelo.local.entities.PhoneEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// test instrumental que comprueba la persistencia de datos con Room

@RunWith(AndroidJUnit4::class)
class RoomPersistenceTest {

    private lateinit var phoneDao: PhoneDao
    private lateinit var phoneDatabase: PhoneRoomDatabase

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().context
        phoneDatabase = Room.inMemoryDatabaseBuilder(context, PhoneRoomDatabase::class.java).build()
        phoneDao = phoneDatabase.getPhoneDao()
    }

    @After
    fun teardown() {
        phoneDatabase.close()
    }

    @Test
    fun insertAndRetrievePhones() = runBlocking {
        // Arrange
        val phone1 = PhoneEntity("1", "Phone 1", "$100", "phone1.jpg")
        val phone2 = PhoneEntity("2", "Phone 2", "$200", "phone2.jpg")
        val phoneList = listOf(phone1, phone2)

        // Act
        phoneDao.insertAllPhones(phoneList)

        // Assert
        val retrievedPhones = phoneDao.getAllPhones().blockingObserve()
        assertEquals(2, retrievedPhones?.size)
        assertEquals(phone1.id, retrievedPhones?.get(0)?.id)
        assertEquals(phone2.phoneName, retrievedPhones?.get(1)?.phoneName)
    }

    @Test
    fun insertAndRetrievePhoneDetail() = runBlocking {
        // Arrange
        val phoneId = "1"
        val phoneDetail = DetailsPhoneEntity(
            phoneId,
            "Phone 1",
            "$100",
            "phone1.jpg",
            "Lorem ipsum dolor sit amet",
            "$90",
            true
        )

        // Act
        phoneDao.insertPhoneDetail(phoneDetail)

        // Assert
        val retrievedPhoneDetail = phoneDao.getCourseDetailByID(phoneId).blockingObserve()
        assertEquals(phoneId, retrievedPhoneDetail?.id)
        assertEquals(phoneDetail.phoneName, retrievedPhoneDetail?.phoneName)
        assertEquals(phoneDetail.phoneCredit, retrievedPhoneDetail?.phoneCredit)
    }

    // Helper function to observe LiveData during tests
    private fun <T> LiveData<T>.blockingObserve(): T? {
        var result: T? = null
        val latch = java.util.concurrent.CountDownLatch(1)
        val observer = androidx.lifecycle.Observer<T> { t ->
            result = t
            latch.countDown()
        }
        observeForever(observer)
        latch.await()
        return result
    }
}