package com.example.cvsapplication.data.api

import com.example.cvsapplication.data.model.ApiCharacter
import com.example.cvsapplication.data.model.ApiCharacterListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * author lgarg on 12/9/25.
 */
interface RickMortyApiService {

    @GET("character/")
    suspend fun searchCharacters(
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null
    ): ApiCharacterListResponse

    @GET("character/{id}")
    suspend fun getCharacterDetailsById(@Path("id") id: Int): ApiCharacter
}