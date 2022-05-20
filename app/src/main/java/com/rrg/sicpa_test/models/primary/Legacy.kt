package com.rrg.sicpa_test.models.primary

import com.google.gson.annotations.SerializedName


data class Legacy(

    @SerializedName("xlarge") var xlarge: String? = null,
    @SerializedName("xlargewidth") var xlargewidth: Int? = null,
    @SerializedName("xlargeheight") var xlargeheight: Int? = null

)