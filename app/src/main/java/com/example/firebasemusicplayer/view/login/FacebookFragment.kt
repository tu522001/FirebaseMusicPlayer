package com.example.firebasemusicplayer.view.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentFacebookBinding
import com.facebook.AccessToken
import com.facebook.GraphRequest
import org.json.JSONException
import com.bumptech.glide.request.target.Target
import com.example.firebasemusicplayer.model.Music
import com.example.firebasemusicplayer.model.Tools
import com.example.firebasemusicplayer.view.adapter.MusicAdapter
import com.example.firebasemusicplayer.view.adapter.ToolsAdapter
import java.util.ArrayList


class FacebookFragment : Fragment() {

    private lateinit var binding: FragmentFacebookBinding
    private lateinit var listTools: ArrayList<Tools>
    private lateinit var toolsAdapter: ToolsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_facebook, container, false)

        var accessToken = AccessToken.getCurrentAccessToken()

        val request = GraphRequest.newMeRequest(
            accessToken
        ) { `object`, response ->

            try {
                var fullName: String = `object`!!.getString("name")
                binding.tvUserName.text = fullName
                var url = `object`.getJSONObject("picture").getJSONObject("data").getString("url")

                Glide.with(this)
                    .load(url)
                    .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
                    .into(binding.imgAvatarFacebook)

                var bundle_url_avatar = Bundle().apply {
                    putString("Key_url_avatar_facebook", url)
                }
//                binding.btnCallHome.setOnClickListener{
//                    findNavController().navigate(R.id.action_facebookFragment3_to_homeFragment,bundle_url_avatar)
//                }


            } catch (e: JSONException) {
                e.printStackTrace()
            }

            // Application code
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,link,picture")
        request.parameters = parameters
        request.executeAsync()


//        binding.btnLogout.setOnClickListener {
//            LoginManager.getInstance().logOut()
//            findNavController().navigate(R.id.action_facebookFragment3_to_signInFragment2)
//        }

        listTools = ArrayList<Tools>()
        listTools = Tools.getMock() as ArrayList<Tools>

        val linearLayoutManager = LinearLayoutManager(context)
        binding.rcvTools?.layoutManager = linearLayoutManager

        toolsAdapter = ToolsAdapter(listTools)
        binding.rcvTools?.adapter = toolsAdapter

//        toolsAdapter.setOnItemClick(object : ToolsAdapter.OnItemListener{
//            override fun onClick(position: Int) {
//                Log.d("PPP","Position : "+position)
//            }
//        })
        toolsAdapter?.setOnItemClickListener(object : ToolsAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
               Log.d("PPP","position : "+position)
//                if(position==0){
//                    findNavController().navigate(R.id.action_facebookFragment3_to_termsOfServiceFragment)
//                }
                when(position){
//                    0 ->
                    1 -> findNavController().navigate(R.id.action_facebookFragment3_to_helpFragment)
                    2 ->findNavController().navigate(R.id.action_facebookFragment3_to_termsOfServiceFragment)
//                    3 ->
//                    4 ->
                }

            }
        })

        return binding.root
    }


}