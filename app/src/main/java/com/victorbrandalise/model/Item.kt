package com.victorbrandalise.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Item(
    val name: String,
    val description: String,
    val image: Uri
) : Parcelable