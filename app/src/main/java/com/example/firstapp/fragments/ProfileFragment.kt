package com.example.firstapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.firstapp.R
import com.example.firstapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ProfileFragment : Fragment() {
lateinit var databaseReference: DatabaseReference
    lateinit var binding: FragmentProfileBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference =FirebaseDatabase.getInstance("https://firstappdb-c7ebb-default-rtdb.firebaseio.com/").reference





        binding.btnSignOut.setOnClickListener{
            firebaseAuth.signOut();
            it.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)

        }
        setUp()
        return binding.root
    }

    private fun setUp(){
        val id = firebaseAuth.currentUser!!.uid
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    binding.textFname.text="First Name: " + dataSnapshot.child("FirstName").value.toString()
                    binding.textLName.text="Last Name: " +dataSnapshot.child("LastName").value.toString()
                    binding.textAddress.text="Address: " +dataSnapshot.child("Address").value.toString()
                    binding.textBitrhDay.text="BrithDay: " +dataSnapshot.child("BirthDay").value.toString()
                    binding.textCountry.text="Country: " +dataSnapshot.child("Country").value.toString()
                    binding.textStateProvince.text = "State/Province: " +dataSnapshot.child("ProvinceState").value.toString()
                    binding.textSex.text ="Sex: " +dataSnapshot.child("Sex").value.toString()
                    binding.textPhoneNumber.text ="Phone Number: " +dataSnapshot.child("PhoneNumber").value.toString()
                    binding.textEmail.text ="Email: " +dataSnapshot.child("Email").value.toString()
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        databaseReference.child("Users").child(id).addValueEventListener(postListener)

    }

}