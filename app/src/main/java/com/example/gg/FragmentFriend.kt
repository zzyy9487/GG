package com.example.gg


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gg.Fragment_Friend.TabAdapter
import com.example.gg.Query.KeywordAdapter
import kotlinx.android.synthetic.main.fragment_1.view.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentFriend : Fragment() {

    lateinit var adapter:TabAdapter
    lateinit var rootview:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_1, container, false)



        rootview.viewPager.adapter = adapter

        rootview.fragtab.setupWithViewPager(rootview.viewPager)






        return rootview
    }


}
