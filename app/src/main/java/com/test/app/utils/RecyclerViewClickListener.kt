package com.test.app.utils

import android.view.View

interface RecyclerViewClickListener {
    fun recyclerViewListClicked(V: View, pos: Int, id: Int?, extraData: Any?)
}