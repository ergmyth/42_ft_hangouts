package ru.school21.eleonard.data.network.helpers

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResponseWrapper<T> : Serializable {
    val status: String? = null

    @SerializedName("detail")
    val data: T? = null

    @SerializedName("status_id")
    val statusId: Int? = null

    @SerializedName("error_message")
    val error: String? = null
}