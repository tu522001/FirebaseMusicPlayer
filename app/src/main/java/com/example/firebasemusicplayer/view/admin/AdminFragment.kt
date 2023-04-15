package com.example.firebasemusicplayer.view.admin

import android.database.DatabaseUtils
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentAdminBinding
import com.example.firebasemusicplayer.model.entity.Tools
import com.example.firebasemusicplayer.model.entity.ToolsAdmin
import com.example.firebasemusicplayer.view.adapter.ToolsAdapter
import com.example.firebasemusicplayer.view.adapter.ToolsAdminAdapter
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth


class AdminFragment : Fragment() {

    private lateinit var binding : FragmentAdminBinding
    private lateinit var listToolsAdmin: ArrayList<ToolsAdmin>
    private lateinit var toolsAdminAdapter: ToolsAdminAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_admin,container, false)

        listToolsAdmin = ArrayList<ToolsAdmin>()
        listToolsAdmin = ToolsAdmin.getMock() as ArrayList<ToolsAdmin>

        val linearLayoutManager = LinearLayoutManager(context)
        binding.rcvToolsAdmin?.layoutManager = linearLayoutManager

        toolsAdminAdapter = ToolsAdminAdapter(listToolsAdmin)
        binding.rcvToolsAdmin?.adapter = toolsAdminAdapter

        toolsAdminAdapter?.setOnItemClickListener(object : ToolsAdminAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                Log.d("PPP", "position : " + position)

                when (position) {
                    0 -> findNavController().navigate(R.id.action_adminFragment_to_homeFragment3)
                    1 -> findNavController().navigate(R.id.action_adminFragment_to_outputFileExcelFragment)
//                    2 -> findNavController().navigate(R.id.action_adminFragment_to_helpFragment)
                    3 -> findNavController().navigate(R.id.action_adminFragment_to_versionFragment)
                    4 -> findNavController().navigate(R.id.action_adminFragment_to_helpFragment)
                    5 -> findNavController().navigate(R.id.action_adminFragment_to_termsOfServiceFragment)
                    6 -> findNavController().navigate(R.id.action_adminFragment_to_securityFragment)
                    7 -> Logout()
                }

            }
        })

        return binding.root
    }

    private fun Logout() {
        val mAuth = FirebaseAuth.getInstance()
        LoginManager.getInstance().logOut() // đăng xuất khỏi Facebook
        mAuth.signOut() // đăng xuất khỏi Firebase
        findNavController().navigate(R.id.action_adminFragment_to_signInFragment3)
    }

}