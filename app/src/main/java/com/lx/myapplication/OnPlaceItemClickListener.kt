package com.example.list

import android.view.View

interface OnPlaceItemClickListener {
    fun onItemClick(holder: PlaceAdapter.ViewHolder?, view: View?, position: Int)

}