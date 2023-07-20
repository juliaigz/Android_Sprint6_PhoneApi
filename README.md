# Android_Sprint6_PhoneApi
### Project made in Android Studio using kotlin, witn MVVM Patron, Connected to an API that bring a list of phones with, thereis Unit &amp; instrumented Test.

You can check the video of the result here: (English)
https://www.instagram.com/reel/Cu3q7_lxCz9/?utm_source=ig_web_button_share_sheet

comenzamos creando el basic views activity que ya viene con los Fragments
 
Hago las vistas en el FISRTFRAGMENT pongo el Recycler View 
 
Y en el second Fragment pongo el detalle de lo que voy a mostrar 
 
Creamos un article_item.xml para poner el detalle de lo que va en el recycler view
 
El main activity muestra lo del first fragment
 
Antes de pasar a la base de datos 
Hacer lo de los colores 
 
Y hacer lo de los strings 
Agregar este código en strings.xml
<string name="enviar_correo">enviar correo</string>
 
Luego llamarlo desde el xml 
 

 

Y ahora pasar al código  (LOCAL con CARPETA Database , CARPETA entities y el dao) (REMOTO api Retrofit y CARPETA FromInternet con las clases) y afuera en el MODELO están el repository y el mapper  (falta el adapter del recycler view eso va en el view)
 
Ahora lo que va dentro de las carpetas (falta el adapter del recicler view)
 
Código
REMOTE, frominternet , con las dos api classes
 
REMOTE, ApiRetrofit,
RetrofitClient  //trae el link url , y conecta a la Api

 


PhoneApi // es el que te agrega los links  products y el otro que dice details

 
LOCAL 
Entities (Details phone) una por cada url de json
 
Entities (Phone) 
 
Database 
PhoneRoomDatabase 
 
DAO  
PhoneDao
 
MAPPER.KT

 
REPOSITORY 
 
Ahora ya hemos terminado con el Package MODELO 
Asi que pasaremos a los Views
Comenzamos con el Package ViewModel, le creamos la clase PhoneViewModel
 
El adapter del recyclerview
 
Ahora al First Fragment
 
Luego el Second Fragment
 
 
Y en el main activity no hay que ser nada más que borrar código y dejarlo así 
 






TEST
Para esta parte recuerda agregar las dependencias necesarias en el BUILD GRADLE (module:app)
// dependecias de test
    // Robolectric environment fragmento. actividades
    testImplementation 'org.robolectric:robolectric:4.4'
    // anotaciones de test
    testImplementation "androidx.arch.core:core-testing:2.2.0"
    // mockito
    testImplementation "com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0-alpha03@jar"
    testImplementation 'androidx.test:core-ktx:1.5.0'
    testImplementation 'androidx.test.ext:junit-ktx:1.1.5'

    // Dependencia de OkHttp
    testImplementation 'com.squareup.okhttp3:okhttp:4.9.3'

    // Dependencia de OkHttp MockWebServer (para pruebas)
    testImplementation 'com.squareup.okhttp3:mockwebserver:4.8.0'
    configurations.all {
        exclude module: 'okhttp-ws'
    }
    implementation 'com.squareup.okhttp3:okhttp-ws:3.4.1'
    androidTestImplementation 'com.squareup.okhttp3:mockwebserver:4.8.0'

Ahora si vamos a hacer un TEST UNITARIO 
Creamos una clase en el package  com.example.tuproyecto(test)
Por ejemplo RetrofitInstanceTest (Testea la integración de la API) 
 
Ahora vamos a correr el test
 
OTRO EJEMPLO DE TEST UNITARIO
Testeando una entidad
 
TEST INSTRUMENTADO
Creamos una clase en el package  com.example.tuproyecto(Androidtest)

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


OTRO Test instrumented
@RunWith(AndroidJUnit4::class)
@Config(sdk=[Q], manifest = Config.NONE)
class CoursesDaoTest {


    // referencias
    private lateinit var coursesDaoTest : CentroFuturoDao
    private lateinit var db: CoursesDataBase

    //   private lateinit var  coursesRepositoryMock: CentroFuturoRepository
//    @Before
//        fun setUp2(){
//
//        val context= ApplicationProvider.getApplicationContext<Context>()
//        db= Room.inMemoryDatabaseBuilder(context,CoursesDataBase::class.java).build()
//        coursesDaoTest= db.getCentroFuturoDao()
//        // inicializar el mock
//        coursesRepositoryMock = Mockito.mock(CentroFuturoRepository::class.java)
//
////        Mockito.`when`( coursesRepositoryMock.coursesListLiveData()).thenReturn(listOf(
////            CoursesEntity("43", "Prueba1","test1","url",4,"march"),
////            CoursesEntity("44", "Prueba2","test2","url",4,"march"),
//
//      //  ))
//
//    }




    @Before
    fun setUp(){
        val context= ApplicationProvider.getApplicationContext<Context>()
        db= Room.inMemoryDatabaseBuilder(context,CoursesDataBase::class.java).build()
        coursesDaoTest= db.getCentroFuturoDao()

    }



    @After
    fun shutDown(){

        db.close()
    }


    // testear insertar una lista de courses
    @Test
    fun insertCoursesList()= runBlocking {

        val coursesEntity= listOf(

            CoursesEntity("43", "Prueba1","test1","url",4,"march"),
            CoursesEntity("44", "Prueba2","test2","url",4,"march"),

            )
        coursesDaoTest.insertAllCourses(coursesEntity)
        val coursesLiveData = coursesDaoTest.getAllCourses()
        val courseList : List<CoursesEntity> = coursesLiveData.value?: emptyList()

        // verificamos que la lista no este vacia

        assertThat(courseList, not(emptyList()))
        assertThat(courseList.size, equalTo(2))
    }


    // inserta y trae un curso por id
    @Test
    fun inserDetailCourses() = runBlocking {


        val courseDetail= CoursesDetailEntity(

            "2",
            "Curso Test 3",
            "text unitarios",
            "url",
            5,
            "March",
            "developer",
            true,
            "presencial",
            "November"

        )
        coursesDaoTest.insertCourseDetail(courseDetail)
        val courseLiveData = coursesDaoTest.getCourseDetailByID("2")
        val courseValue = courseLiveData.value

        assertThat( courseValue?.id, equals("2"))
        assertThat(courseValue?.weeks.toString(), equalTo(5))

    }



}


