package com.example.nuevo.modelo

import com.example.nuevo.modelo.local.entities.DetailsPhoneEntity
import com.example.nuevo.modelo.local.entities.PhoneEntity
import com.example.nuevo.modelo.remote.frominternet.DetailsPhoneApiClass
import com.example.nuevo.modelo.remote.frominternet.PhoneApiClass

//el mapper traer la información de la API y lo guarda en la database del telefono

fun fromInternetToPhoneEntity(phoneList: List<PhoneApiClass>): List<PhoneEntity>{ // se usa luego en el Repository
// it representa al phoneApi
    return phoneList.map {

        PhoneEntity(
            id= it.id,
            phoneName = it.name,
            phonePrice = it.price,
            phoneImage = it.image

        )
    }
}

fun fromInternetToPhoneDetailEntity(phone: DetailsPhoneApiClass): DetailsPhoneEntity{ // se usa luego en el Repository

    return DetailsPhoneEntity(
        id=phone.id,
        phoneName = phone.name,
        phonePrice = phone.price,
        phoneImage = phone.image,
        phoneDescription = phone.description,
        phoneLastPrice = phone.lastPrice,
        phoneCredit = phone.credit,

    )

}
