package com.example.firebasemusicplayer.view.fragment.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.firebasemusicplayer.BASE_URL
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentSignUpBinding
import com.example.firebasemusicplayer.viewmodel.SignUpViewModel
import com.google.firebase.database.*


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentSignUpBinding>(
            inflater,
            R.layout.fragment_sign_up,
            container,
            false
        )

        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)


        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(BASE_URL)
        binding.btnConfirm.setOnClickListener {

            // get data from EditText into String variables
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val phone = binding.edtPhone.text.toString()

            //check if user fill all the fields before sending data to firebase
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }

            // check if passwords are matching with each other
            // if not matching with each other then show a toast message

            else{

                databaseReference.child("users").addListenerForSingleValueEvent( object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChild(phone)){
                            Toast.makeText(requireContext(), "Phone is already registered", Toast.LENGTH_SHORT).show()
                        }else{
                            // sending data to  firebase Realtime Database
                            // we are using phone number as unique identity of every user
                            // so all the other details of user comes under phone number

                            databaseReference.child("users").child(phone).child("name").setValue(name)
                            databaseReference.child("users").child(phone).child("email").setValue(email)
                            databaseReference.child("users").child(phone).child("password").setValue(password)
                            databaseReference.child("users").child(phone).child("phone").setValue(phone)
                        }
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            }

            signUpViewModel.signUp(name,email,phone,password)
        }

        binding.imgBtnLogout.setOnClickListener{
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        signUpViewModel.signUpSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                // Xác thực thành công
                Toast.makeText(
                    requireContext(),
                    "Đăng ký tài khoản thành công.",
                    Toast.LENGTH_SHORT

                ).show()
                // Tiếp tục với các bước tiếp theo
            }
        })

        return binding.root
    }

}
