package com.example.nuevo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nuevo.modelo.PhoneRepository
import com.example.nuevo.modelo.local.database.PhoneRoomDatabase
import com.example.nuevo.modelo.local.entities.DetailsPhoneEntity
import com.example.nuevo.modelo.local.entities.PhoneEntity
import kotlinx.coroutines.launch

class PhoneViewModel (application: Application): AndroidViewModel(application) { // conexion repositorio

    private val repository : PhoneRepository // tomamoes el repositorio que es el fin del modelo

    // entidad
    private val phoneDetailLiveData = MutableLiveData<DetailsPhoneEntity>()

    private  var idSelected: String ="-1"

    init{
        val bd= PhoneRoomDatabase.getDataBase(application) //Instanciar la base de datos  (para acceder a ella)
        val  phoneDao = bd.getPhoneDao() //Despues de instanciar la bade de datos, entramos y sacamos la funcion getPhoneDao

        repository = PhoneRepository(phoneDao) // Recuerda que el Repository Recibe un PhoneDao, pero no la clase sino la variable que hemos creado y que tb viene de ña bd

        // lama el método del respositorio
        viewModelScope.launch {

            repository.fechPhones() // esta es una función que viene del PhoneRepository, antes hemos instanciado el Repositorio como repository
        }
    }

    // listado de elementos

    fun getPhoneList(): LiveData<List<PhoneEntity>> = repository.phoneListLiveData //Futuro:: esto se usará en el 1ER FRAGMENTO
    // el Live DATA está tomando una lista de entidad PhoneEntity  y lo iguala a una variable del repositorio


    // obtener el detalle envuelto en liveData

    fun getPhoneDetail(): LiveData<DetailsPhoneEntity> = phoneDetailLiveData // el live data toma una entidad de DETALLE PHONE sin lista y los iguala a una variable que existe aqui en el VIEWMODEL


    // funcion para seleccionar elemento

    fun getPhoneDetailByIDFromInternet(id:String) = viewModelScope.launch { //Futuro:: esto se usara en el 2DO FRAGMENTO

        val phoneDetail = repository.fetchPhoneDetail(id)// IGUALAMOS la variable con una funcion que viene del Repositorio que recibe un id tipo String ENTRE SUS VARIABLES
        phoneDetail?.let {

            phoneDetailLiveData.postValue(it)
        }
    }




}