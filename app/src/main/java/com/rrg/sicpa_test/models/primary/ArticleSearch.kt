package com.rrg.sicpa_test.models.primary

import com.google.gson.annotations.SerializedName


data class ArticleSearch(

    @SerializedName("status") var status: String? = null,
    @SerializedName("copyright") var copyright: String? = null,
    @SerializedName("response") var response: Response? = Response(),
    @SerializedName("results") var results: ArrayList<Results> = arrayListOf()

)