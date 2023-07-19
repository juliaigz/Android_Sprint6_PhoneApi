package com.example.nuevo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.nuevo.databinding.FragmentSecondBinding
import com.example.nuevo.viewModel.PhoneViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var mBinding: FragmentSecondBinding  //instanciamos el 2do Fragmento
    private val mViewModel : PhoneViewModel by activityViewModels() //instanciamos el ViewModels
    //OJO va a almacenar el id del phone
    private var phoneId :String? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentSecondBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //validar que está llegando la variable
        arguments?.let { bundle ->

            phoneId = bundle.getString("courseId")
            Log.d("seleccion2", phoneId.toString())
        }

        phoneId?.let { id ->
            mViewModel.getPhoneDetailByIDFromInternet(id) // esta es la 3ERA FUN del PhoneViewModel
        }

        mViewModel.getPhoneDetail().observe(viewLifecycleOwner, Observer {
            Log.d("seleccion3",phoneId.toString())
            var id=it.id
            var name=it.phoneName

            Glide.with(mBinding.ivLogo).load(it.phoneImage).into(mBinding.ivLogo)   //conecta el id de la imagen del 2DO FRAGMENTO con la imagen del Entity Details
            mBinding.textId.text = it.id
            mBinding.textName.text = it.phoneName  //conecta EL 2ND FRAGMENT con el entity de  TABLE_PHONE_DETAILS
            mBinding.textPrice.text = it.phonePrice
            mBinding.textDescripcion.text = it.phoneDescription
            mBinding.textlastprice.text = it.phoneLastPrice
            mBinding.textCredit.text = it.phoneCredit.toString()

            //correo electronico

            mBinding.btMail.setOnClickListener{
                val mintent = Intent(Intent.ACTION_SEND)
                mintent.data = Uri.parse("mailto")
                mintent.type="text/plain"

                mintent.putExtra(Intent.EXTRA_EMAIL, arrayOf("phones@phonestore.com"))
                mintent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Solicito información sobre el teléfono"+id
                )

                mintent.putExtra(
                    Intent.EXTRA_TEXT,"Hola\n" +
                            "Quisiera pedir información sobre este teléfono"+name+",\n"+
                            "me gustaría que me contactaran a este correo o al siguiente número\n" +
                            " _________\n" +
                            "Quedo atento."
                )
                try {
                    startActivity(mintent)
                }catch (e: Exception){
                    Toast.makeText(context,e.message, Toast.LENGTH_LONG).show()
                }
            }



        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
}