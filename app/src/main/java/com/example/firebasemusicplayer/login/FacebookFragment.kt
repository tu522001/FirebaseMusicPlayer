package com.example.firebasemusicplayer.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentFacebookBinding
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import org.json.JSONException


class FacebookFragment : Fragment() {

    private lateinit var binding: FragmentFacebookBinding
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
                var fullName : String = `object`!!.getString("name")
                binding.tvUserName.text = fullName
                var url = `object`.getJSONObject("picture").getJSONObject("data").getString("url")
                Glide.with(this).load(url).into(binding.imgAvatarFacebook)
//                Glide.get()
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            // Application code
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,link,picture")
        request.parameters = parameters
        request.executeAsync()

        binding.btnLogout.setOnClickListener {
            LoginManager.getInstance().logOut()
            findNavController().navigate(R.id.action_facebookFragment3_to_signInFragment2)
        }
        return binding.root
    }


}