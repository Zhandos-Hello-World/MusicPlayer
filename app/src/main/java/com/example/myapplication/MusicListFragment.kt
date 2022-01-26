package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter


open class MusicListFragment : MusicParentListFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val names = Array(CurrentMusic.size()){CurrentMusic.namesOfMusics[it]}

        val adapter = ArrayAdapter(inflater.context, R.layout.list_component, R.id.name_music, names)
        listAdapter = adapter

        return super.onCreateView(inflater, container, savedInstanceState);
    }

}