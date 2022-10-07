package com.lx.myapplication

import android.view.View

interface OnNoticeItemClickListener {

    fun onItemClick(holder: NoticeAdapter.ViewHolder?, view: View?, position: Int)

}