package com.rrg.sicpa_test.models.primary

import com.google.gson.annotations.SerializedName


data class Multimedia(

    @SerializedName("rank") var rank: Int? = null,
    @SerializedName("subtype") var subtype: String? = null,
    @SerializedName("caption") var caption: String? = null,
    @SerializedName("credit") var credit: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("legacy") var legacy: Legacy? = Legacy(),
    @SerializedName("subType") var subType: String? = null,
    @SerializedName("crop_name") var cropName: String? = null

)