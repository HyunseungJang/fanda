package com.lx.myapplication.data


import com.google.gson.annotations.SerializedName

data class CartListResponse(
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
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("price")
            val price: String
        )

        data class Header(
            @SerializedName("total")
            val total: Int
        )
    }
}