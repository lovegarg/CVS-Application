package com.example.cvsapplication.data.model

import com.google.gson.annotations.SerializedName

/**
 * author lgarg on 12/9/25.
 */
data class ApiCharacter(@SerializedName("id")
                        val id: Int,
                        @SerializedName("name")
                        val name: String,
                        @SerializedName("status")
                        val status: String,
                        @SerializedName("species")
                        val species: String,
                        @SerializedName("type")
                        val type: String,
                        @SerializedName("gender")
                        val gender: String,
                        @SerializedName("origin")
                        val origin: ApiOrigin,
                        @SerializedName("location")
                        val location: ApiLocation,
                        @SerializedName("image")
                        val image: String,
                        @SerializedName("episode")
                        val episode: List<String>,
                        @SerializedName("url")
                        val url: String,
                        @SerializedName("created")
                        val created: String)

data class ApiOrigin(@SerializedName("name")
                     val name: String,
                     @SerializedName("url")
                     val url: String)

data class ApiLocation(@SerializedName("name")
                        val name: String,
                        @SerializedName("url")
                        val url: String)