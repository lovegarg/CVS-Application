package com.example.cvsapplication.data.model

import com.google.gson.annotations.SerializedName

/**
 * author lgarg on 12/9/25.
 */
data class ApiCharacterListResponse(@SerializedName("info")
                                    val info: ApiPageInfo,
                                    @SerializedName("results")
                                    val results: List<ApiCharacter>)

data class ApiPageInfo(@SerializedName("count")
                       val count: Int,
                       @SerializedName("pages")
                       val pages: Int,
                       @SerializedName("next")
                       val next: String?,
                       @SerializedName("prev")
                       val prev: String?)
