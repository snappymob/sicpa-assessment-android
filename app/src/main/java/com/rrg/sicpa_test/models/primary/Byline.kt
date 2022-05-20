package com.rrg.sicpa_test.models.primary

import com.google.gson.annotations.SerializedName


data class Byline(

    @SerializedName("original") var original: String? = null,
    @SerializedName("person") var person: ArrayList<Person> = arrayListOf(),
    @SerializedName("organization") var organization: String? = null

)