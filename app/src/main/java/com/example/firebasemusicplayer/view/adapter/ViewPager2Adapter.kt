package com.example.firebasemusicplayer.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.firebasemusicplayer.view.home.HomeFragment
import com.example.firebasemusicplayer.view.login.FacebookFragment
import com.example.firebasemusicplayer.view.setting.SettingFragment

class ViewPager2Adapter (fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SettingFragment()
            2 -> FacebookFragment()
            else -> HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}
