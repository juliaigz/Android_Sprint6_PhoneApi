package com.example.nuevo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nuevo.databinding.ArticleItemBinding

import com.example.nuevo.modelo.local.entities.PhoneEntity

class PhoneListAdapter : RecyclerView.Adapter<PhoneListAdapter.PhoneVH>() { // Recuerda cambiar esto después (PhoneVH) SE CREA DESPUÉS no te preocupes x el rojo
//RECUERDA el adapter esta para conectar el recyclerView con SU chiquitito (Article_item.xml)

    private var listPhones = listOf<PhoneEntity>()
    private val SelectedPhones = MutableLiveData<PhoneEntity>()



    fun update(list:List<PhoneEntity>){
        listPhones= list
        notifyDataSetChanged()
    }


    // FUNCION PARA SELECCIONAR

    fun selectedPhone():
            LiveData<PhoneEntity> = SelectedPhones


    inner class  PhoneVH(private val mBinding: ArticleItemBinding): // el binding viene del (article_item.xml) del detalle del recyclerView
        RecyclerView.ViewHolder(mBinding.root), View.OnClickListener{

        fun bind(phone: PhoneEntity){
            Glide.with(mBinding.listImage).load(phone.phoneImage).centerCrop().into(mBinding.listImage)

            mBinding.name.text = phone.phoneName // NO TE olvides del .TEXT
            mBinding.price.text = phone.phonePrice
            mBinding.idMostrar.text = phone.id // recuerda agregar.TEXT al mBinding

            itemView.setOnClickListener(this)

        }
        override  fun onClick(v: View){

            SelectedPhones.value= listPhones[adapterPosition]
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneVH {
        return PhoneVH(ArticleItemBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: PhoneVH, position: Int) {
        val course = listPhones[position]
        holder.bind(course)
    }


    override fun getItemCount()=
        listPhones.size


}