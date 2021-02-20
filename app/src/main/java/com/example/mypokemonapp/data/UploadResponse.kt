package com.example.mypokemonapp.data

/**
 * data class for parsing the API Response.
 */
data class UploadResponse(
    val status: String,
    val message: String,
    val payload: PayLoad
)

data class PayLoad(
    val fileId: String,
    val fileType: String,
    val fileName: String,
    val downloadUri: String,
    val uploadStatus: Boolean
)
