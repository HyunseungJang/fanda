package com.lx.myapplication

import android.view.View
import com.lx.myapplication.CartAdapter

interface OnCartItemClickListener {
    fun onItemClick(holder: CartAdapter.ViewHolder?, view: View?, position: Int)

}