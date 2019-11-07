package com.example.gg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.badge.BadgeUtils
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log


class MainActivity : AppCompatActivity() {

    var fragmentFriend = FragmentFriend()
    var fragmentChat = FragmentChat()
    var fragmentPost = FragmentPost()
    var fragmentToday = FragmentToday()
    var fragmentWallet = FragmentWallet()
    val manager = this.supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = manager.beginTransaction()
        transaction.replace(R.id.framelayout, fragmentChat).commit()
        bottomnavigation.selectedItemId = R.id.chat

        bottomnavigation.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener)

    }

    override fun onStart() {
        super.onStart()

        val bottomnavigation:BottomNavigationView=findViewById(R.id.bottomnavigation)
        val badgeDrawable = bottomnavigation.getOrCreateBadge(R.id.chat)
        badgeDrawable.number = fragmentChat.inputList.sumBy { it.count }
        if(badgeDrawable.number == 0 ){bottomnavigation.removeBadge(R.id.chat)}
        else {bottomnavigation.getOrCreateBadge(R.id.chat)}
    }

    fun setbadge(){
        val bottomnavigation:BottomNavigationView=findViewById(R.id.bottomnavigation)
        val badgeDrawable = bottomnavigation.getOrCreateBadge(R.id.chat)
        badgeDrawable.number = fragmentChat.inputList.sumBy { it.count }
        if(badgeDrawable.number == 0 ){bottomnavigation.removeBadge(R.id.chat)}
        else {bottomnavigation.getOrCreateBadge(R.id.chat)}
    }

    fun removebadge(){
        bottomnavigation.removeBadge(R.id.chat)
    }

    val OnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.friend -> {
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.framelayout, fragmentFriend, "friend").commit()
                    return true
                }

                R.id.chat -> {
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.framelayout, fragmentChat, "chat").commit()
                    return true
                }

                R.id.say -> {
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.framelayout, fragmentPost, "post").commit()
                    return true
                }

                R.id.today -> {
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.framelayout, fragmentToday, "today").commit()
                    return true
                }

                R.id.money -> {
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.framelayout, fragmentWallet, "wallet").commit()
                    return true
                }
            }
            return false
        }
    }
}

