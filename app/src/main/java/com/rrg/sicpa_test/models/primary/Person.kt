package com.rrg.sicpa_test.models.primary

import com.google.gson.annotations.SerializedName


data class Person(

    @SerializedName("firstname") var firstname: String? = null,
    @SerializedName("middlename") var middlename: String? = null,
    @SerializedName("lastname") var lastname: String? = null,
    @SerializedName("qualifier") var qualifier: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("role") var role: String? = null,
    @SerializedName("organization") var organization: String? = null,
    @SerializedName("rank") var rank: Int? = null

)