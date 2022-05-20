package com.rrg.sicpa_test.models.primary

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList


data class Article(

    @SerializedName("abstract") var abstract: String? = null,
    @SerializedName("web_url") var webUrl: String? = null,
    @SerializedName("snippet") var snippet: String? = null,
    @SerializedName("lead_paragraph") var leadParagraph: String? = null,
    @SerializedName("print_section") var printSection: String? = null,
    @SerializedName("print_page") var printPage: String? = null,
    @SerializedName("source") var source: String? = null,
    @SerializedName("multimedia") var multimedia: ArrayList<Multimedia> = arrayListOf(),
    @SerializedName("headline") var headline: Headline? = Headline(),
    @SerializedName("keywords") var keywords: ArrayList<Keywords> = arrayListOf(),
    @SerializedName("pub_date") var pubDate: Date? = null,
    @SerializedName("document_type") var documentType: String? = null,
    @SerializedName("news_desk") var newsDesk: String? = null,
    @SerializedName("section_name") var sectionName: String? = null,
    @SerializedName("subsection_name") var subsectionName: String? = null,
    @SerializedName("byline") var byline: Byline? = Byline(),
    @SerializedName("type_of_material") var typeOfMaterial: String? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("word_count") var wordCount: Int? = null,
    @SerializedName("uri") var uri: String? = null

)