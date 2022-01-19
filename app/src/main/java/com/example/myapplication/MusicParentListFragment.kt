package com.example.myapplication

import android.content.Context
import android.view.View
import android.widget.ListView
import androidx.fragment.app.ListFragment

abstract class MusicParentListFragment:ListFragment() {
    companion object {
        interface Listener {
            fun selected(id: Int)
        }
    }
    protected var listener: Listener? = null


    override fun onAttach(context: Context) {
        listener = context as Listener
        super.onAttach(context)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        listener?.selected(id.toInt())
        super.onListItemClick(l, v, position, id)
    }
}