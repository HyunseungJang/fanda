package com.lx.myapplication

import android.view.View

interface OnFeedItemClickListener {

    fun onItemClick(holder: ItemAdapter.ViewHolder?, view: View?, position: Int)

}