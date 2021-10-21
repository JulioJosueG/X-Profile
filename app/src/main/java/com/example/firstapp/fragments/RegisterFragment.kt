package com.example.firstapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.findNavController
import com.example.firstapp.MainActivity
import com.example.firstapp.R
import com.example.firstapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    lateinit var mfire: FirebaseAuth
lateinit var dbreference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        mfire = FirebaseAuth.getInstance()
        dbreference = FirebaseDatabase.getInstance("https://firstappdb-c7ebb-default-rtdb.firebaseio.com/").reference
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        SnipperSetUp();
        binding.txtPhone.doOnTextChanged() {text, start, before, count ->

            if (text!!.length > 10){
                binding.PhoneNumberRegister.error = "Dont pass the limit"
            }
            else if (text.length <10){
                binding.PhoneNumberRegister.error = null

            }




        }

        binding.textFNameRegister.doOnTextChanged(){text, start, before, count ->

            if (text.isNullOrEmpty()){
                binding.Name.helperText = "Required*"
            }
            else{
                binding.Name.helperText = null
            }

        }

        binding.textEmailRegister.doOnTextChanged(){text, start, before, count ->

            if (text.isNullOrEmpty()){
                binding.Email.helperText = "Required*"
            }
            else{
                binding.Email.helperText = null
            }

        }

        binding.PasswordRegister.doOnTextChanged(){text, start, before, count ->

            if (text.isNullOrEmpty()){
                binding.Password.helperText = "Required*"
            }
            else{
                binding.Password.helperText = null
            }

        }

        binding.btnRegister.setOnClickListener{
            if(!binding.textFNameRegister.text.isNullOrEmpty() && !binding.TextLNameR.text.isNullOrEmpty() && !binding.TextAdress.text.isNullOrEmpty()
                && !binding.DateBirthDay.text.isNullOrEmpty() && !binding.txtPhone.text.isNullOrEmpty() && binding.SpinnerSex.selectedItem.toString().isNotEmpty()
                && !binding.TextCountryRegister.text.isNullOrEmpty() && !binding.TextStateProvinceRegister.text.isNullOrEmpty()
                && !binding.textEmailRegister.text.isNullOrEmpty() && !binding.PasswordRegister.text.isNullOrEmpty()){
                    if (binding.PasswordRegister.text.toString().length >=6){
                        registerUser();

                    }
                    else{
                        Toast.makeText(activity, "The Password required at least 6 character",Toast.LENGTH_SHORT).show()
                    }
            }
            else{
                Toast.makeText(activity, "There are some fields empty",Toast.LENGTH_SHORT).show()

            }
        }

        binding.BtnSignIn.setOnClickListener{

            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)


        }
        return binding.root
    }

    private fun registerUser() {
        mfire.createUserWithEmailAndPassword(binding.textEmailRegister.text.toString(),binding.PasswordRegister.text.toString())
            .addOnCompleteListener{
                if(it.isSuccessful){

                    val map = mapOf(
                        "FirstName" to binding.textFNameRegister.text.toString(),
                        "LastName" to binding.TextLNameR.text.toString(),
                        "PhoneNumber" to binding.txtPhone.text.toString(),
                        "Sex" to binding.SpinnerSex.selectedItem.toString(),
                        "BirthDay" to binding.DateBirthDay.text.toString(),
                        "Email" to binding.textEmailRegister.text.toString(),
                        "Password" to binding.PasswordRegister.text.toString(),
                        "Country" to binding.TextCountryRegister.text.toString(),
                        "ProvinceState" to binding.TextStateProvinceRegister.text.toString(),
                        "Address" to binding.TextAdress.text.toString(),
                        )

                    if (mfire.currentUser?.uid.isNullOrEmpty()){

                    }
                    else{
                        var id = mfire.currentUser!!.uid;
                        dbreference.child("Users").child(id).setValue(map).addOnCompleteListener{

                            if (it.isSuccessful){
                                Toast.makeText(activity, "User Created", Toast.LENGTH_SHORT).show()
                            }

                        }

                    }
                }
                else{
                    Toast.makeText(activity, "It cant be Register",Toast.LENGTH_SHORT).show()


                }
            }
    }

    private fun SnipperSetUp(){
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.sex_array,
            android.R.layout.simple_list_item_1
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.SpinnerSex.adapter = adapter
        }
    }


}