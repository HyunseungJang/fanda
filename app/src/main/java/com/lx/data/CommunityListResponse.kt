package com.lx.data


import com.google.gson.annotations.SerializedName

data class CommunityListResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("output")
    val output: Output
) {
    data class Output(
        @SerializedName("data")
        val `data`: List<Data>,
        @SerializedName("header")
        val header: Header
    ) {
        data class Data(
            @SerializedName("content")
            val content: String,
            //@SerializedName("date")
            //val date: String,
            @SerializedName("filepath")
            val filepath: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("title")
            val title: String
        )

        data class Header(
            @SerializedName("total")
            val total: Int
        )
    }
}