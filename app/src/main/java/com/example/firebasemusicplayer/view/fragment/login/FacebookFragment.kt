package com.example.firebasemusicplayer.view.fragment.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.firebasemusicplayer.R
import com.example.firebasemusicplayer.databinding.FragmentFacebookBinding
import com.example.firebasemusicplayer.model.entity.Tools
import com.example.firebasemusicplayer.view.adapter.ToolsAdapter
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONException


class FacebookFragment : Fragment() {

    private lateinit var binding: FragmentFacebookBinding
    private lateinit var listTools: ArrayList<Tools>
    private lateinit var toolsAdapter: ToolsAdapter
    private var bundle_url_avatar: Bundle? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_facebook, container, false)

        var accessToken = AccessToken.getCurrentAccessToken()

        val request = GraphRequest.newMeRequest(
            accessToken
        ) { `object`, response -> // Khi request được xử lý xong, hàm callback này sẽ được gọi.

            if (accessToken == null) {
                try {
                    binding.tvUserName.text = "user"
                    Glide.with(this).load(R.drawable.user).into(binding.imgAvatarFacebook)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            } else {
                try {
                    // Lấy tên của người dùng từ JSON Object được trả về.
                    var fullName: String = `object`!!.getString("name")
                    binding.tvUserName.text = fullName

                    // Lấy đường dẫn ảnh đại diện của người dùng từ JSON Object được trả về.
                    var url =
                        `object`.getJSONObject("picture").getJSONObject("data").getString("url")

                    // Sử dụng thư viện Glide để tải ảnh đại diện từ URL.
                    Glide.with(this)
                        .load(url)
                        .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
                        .into(binding.imgAvatarFacebook)

                    // Đóng gói đường dẫn ảnh đại diện vào Bundle để truyền dữ liệu sang Activity khác (nếu cần).
                    bundle_url_avatar = Bundle().apply {
                        putString("Key_url_avatar_facebook", url)
                        putString("Key_User","user")
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

        }

// Tạo đối tượng GraphRequest để lấy thông tin của người dùng từ Facebook API.
        val parameters = Bundle()
        parameters.putString("fields", "id,name,link,picture")
        request.parameters = parameters
        request.executeAsync() // Gửi yêu cầu lấy thông tin người dùng đến Facebook API.


        listTools = ArrayList<Tools>()
        listTools = Tools.getMock() as ArrayList<Tools>

        val linearLayoutManager = LinearLayoutManager(context)
        binding.rcvTools?.layoutManager = linearLayoutManager

        toolsAdapter = ToolsAdapter(listTools)
        binding.rcvTools?.adapter = toolsAdapter

        toolsAdapter?.setOnItemClickListener(object : ToolsAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                Log.d("PPP", "position : " + position)

                when (position) {
                    0 -> findNavController().navigate(
                        R.id.action_facebookFragment3_to_homeFragment,
                        bundle_url_avatar
                    )
                    1 -> findNavController().navigate(R.id.action_facebookFragment3_to_versionFragment)
                    2 -> findNavController().navigate(R.id.action_facebookFragment3_to_helpFragment)
                    3 -> findNavController().navigate(R.id.action_facebookFragment3_to_termsOfServiceFragment)
                    4 -> findNavController().navigate(R.id.action_facebookFragment3_to_securityFragment)
                    5 -> Logout()
                }

            }
        })


        return binding.root

    }

    private fun Logout() {
        val mAuth = FirebaseAuth.getInstance()
        LoginManager.getInstance().logOut() // đăng xuất khỏi Facebook
        mAuth.signOut() // đăng xuất khỏi Firebase
        findNavController().navigate(R.id.action_facebookFragment3_to_signInFragment)
    }


}