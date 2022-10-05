package com.lx.myapplication

import android.view.View
import java.text.FieldPosition

interface OnArtistItemClickListener {
    fun onItemClick(holder: ArtistAdapter.ViewHolder?,view: View?, position: Int)
}