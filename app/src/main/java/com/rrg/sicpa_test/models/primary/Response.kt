package com.rrg.sicpa_test.models.primary

import com.google.gson.annotations.SerializedName


data class Response(

    @SerializedName("docs") var docs: ArrayList<Article> = arrayListOf(),
    @SerializedName("meta") var meta: Meta? = Meta()

)