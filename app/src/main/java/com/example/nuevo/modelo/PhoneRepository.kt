package com.example.nuevo.modelo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.nuevo.modelo.local.PhoneDao
import com.example.nuevo.modelo.local.entities.DetailsPhoneEntity
import com.example.nuevo.modelo.remote.apiRetrofit.RetrofitClient

class PhoneRepository (private val phoneDao: PhoneDao){ //minuscula para la variable phoneDao

    //retrofit cliente
    private val networkService = RetrofitClient.retrofitInstance()

    // dao

    val phoneListLiveData = phoneDao.getAllPhones()

    //Variable local
    val phoneDetailLiveData = MutableLiveData<DetailsPhoneEntity>()




    // insertar el listado de phones
    suspend fun  fechPhones(){ // suspend fun es de courrutines

        val service = kotlin.runCatching { networkService.fetchPhoneList()}  // ctrl click te busca donde esta esa función en el código : suspend fun fetchPhoneList(): Response<List<PhoneApiClass>>

        service.onSuccess {

            when(it.code()){
                in 200..299-> it.body()?.let {
                    // insertando la lista de cursos
                    phoneDao.insertAllPhones(fromInternetToPhoneEntity(it)) // viene del Dao ( insertall) y viene del mapper (fromInternetToPhoneEntity)
                    //insertAllPhones(listPhones: List<PhoneEntity>) esto es del Dao
                }

                else -> Log.d("Repo", "${it.code()}-${it.errorBody()}")
            }
            service.onFailure {
                Log.e("Error","${it.message}")
            }

        }

    }


    // insertar uan tarea

    suspend fun fetchPhoneDetail(id: String): DetailsPhoneEntity? {
        val service = kotlin.runCatching { networkService.fechPhoneDetail(id)} //.fechCourseDetail(id)
        return service.getOrNull()?.body()?.let { phoneDetail ->
            // guardp los datos que viene del mapper y luego se los paso a dao directo
            val phoneDetailEntity = fromInternetToPhoneDetailEntity(phoneDetail) // viene del mapper ( fromInternet
            //inserto los detalles de los curso DEL REPOSITORIO
            phoneDao.insertPhoneDetail(phoneDetailEntity) //la función viene del PhoneDao  :  suspend fun insertPhoneDetail(course: DetailsPhoneEntity)
            phoneDetailEntity
        }
    }

}