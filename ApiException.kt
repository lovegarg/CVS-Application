package com.example.cvsapplication.data.api

/**
 * author lgarg on 12/9/25.
 */
sealed class ApiException(
    override val message: String
) : Exception(message) {

    class NetworkException(message: String = "Network error. Please check your internet connection.") :
        ApiException(message)

    class NotFound(message: String = "Requested resource was not found.") : ApiException(message)

    class ServerException(message: String = "Server error. Please try again later.") :
        ApiException(message)

    class UnknownException(message: String = "Unexpected error occurred.") : ApiException(message)
}