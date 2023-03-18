package com.zeroillusion.shopapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListLatest (
    @SerializedName("latest")
    @Expose
    var latestList: List<Latest>
)