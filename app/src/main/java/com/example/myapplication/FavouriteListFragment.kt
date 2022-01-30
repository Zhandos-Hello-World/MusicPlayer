package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class FavouriteListFragment : MusicParentListFragment() {
    fun update() {
        listAdapter = ArrayAdapter(layoutInflater.context,
            R.layout.list_component, R.id.name_music,
            CurrentMusic.getFavouriteMusic())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val adapter = ArrayAdapter(inflater.context, R.layout.list_component, R.id.name_music,
            CurrentMusic.getFavouriteMusic())

        listAdapter = adapter
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}