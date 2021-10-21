package com.example.firstapp.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.firstapp.MainActivity
import com.example.firstapp.R
import com.example.firstapp.databinding.FragmentResetPassBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class ResetPassFragment : Fragment() {
lateinit var binding: FragmentResetPassBinding
    val mAuth = FirebaseAuth.getInstance()

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPassBinding.inflate(inflater,container,false)

        binding.btnRecover.setOnClickListener{

            if (binding.textEmailReset.text.isNotEmpty()){
                resetPassword();
            }
            else{
                Toast.makeText(activity,"Enter Email",Toast.LENGTH_SHORT).show()
            }

        }
        binding.button5.setOnClickListener{
            it.findNavController().navigate(R.id.action_resetPassFragment_to_loginFragment)
        }

        return binding.root
    }

    private fun resetPassword() {
    mAuth.sendPasswordResetEmail(binding.textEmailReset.text.toString()).addOnCompleteListener{
        if (it.isSuccessful){
            Toast.makeText(activity, "Confirmation email sent it",Toast.LENGTH_SHORT).show()

        }
        else {

            Toast.makeText(activity, "The confirmation email can't be sent it",Toast.LENGTH_SHORT).show()

        }



    }

    }



}