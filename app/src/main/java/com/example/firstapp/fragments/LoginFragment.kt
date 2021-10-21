package com.example.firstapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.findNavController
import com.example.firstapp.R
import com.example.firstapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.textEmailLogin.doOnTextChanged(){text, start, before, count ->

            if (text.isNullOrEmpty()){
                binding.EmailLogin.helperText = "Required*"
            }
            else{
                binding.EmailLogin.helperText = null
            }

        }

        binding.PasswordLogin.doOnTextChanged(){text, start, before, count ->

            if (text.isNullOrEmpty()){
                binding.PasswordLog.helperText = "Required*"
            }
            else{
                binding.PasswordLog.helperText = null
            }

        }
        binding.btnRecoverPass.setOnClickListener{
            it.findNavController().navigate(R.id.action_loginFragment_to_resetPassFragment)
        }
        binding.btnRegisterLogin.setOnClickListener{
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        setup()

        return binding.root
    }

    private fun setup(){

        binding.btnSignInLogin.setOnClickListener{
            if(!binding.textEmailLogin.text.isNullOrEmpty() && !binding.PasswordLogin.text.isNullOrEmpty()){

                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.textEmailLogin.text.toString(),
                    binding.PasswordLogin.text.toString()).addOnCompleteListener{

                        if(it.isSuccessful){
                            binding.btnSignInLogin.setOnClickListener{
                                it.findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
                            }
                        }else{
                            showAlert("Authentication Error has ocurred");
                        }

                }

            }
            else{
                showAlert("Fill Credentials");
            }


        }
    }


    private fun showAlert(message : String){
        val builder =  AlertDialog.Builder(activity)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}