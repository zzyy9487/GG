package com.example.gg.Fragment_Friend

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(childFragmentManager: FragmentManager): FragmentPagerAdapter(childFragmentManager) {

    override fun getItem(position: Int): Fragment {
        when (position){
            0 -> return Fragment_Friend()
            1 -> return Fragment_Account()
            2 -> return Fragment_Service()
            3 -> return Fragment_Tag()
            else -> return Fragment_Theme()
        }
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position){
            0 -> return "朋友"
            1 -> return "帳戶"
            2 -> return "服務"
            3 -> return "貼圖"
            else -> return "主題"
        }
    }
}
