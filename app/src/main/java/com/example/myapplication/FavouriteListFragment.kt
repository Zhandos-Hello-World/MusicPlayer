package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast

class FavouriteListFragment : MusicParentListFragment() {
    fun update() {
        val adapter = ArrayAdapter(layoutInflater.context, R.layout.list_component, R.id.name_music,
            CurrentMusic.favouriteMusicList)
        listAdapter = adapter

        Toast.makeText(layoutInflater.context, "UPDATED", Toast.LENGTH_SHORT).show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {


        val adapter = ArrayAdapter(inflater.context, R.layout.list_component, R.id.name_music,
            CurrentMusic.favouriteMusicList)


        listAdapter = adapter

        return super.onCreateView(inflater, container, savedInstanceState)
    }

}