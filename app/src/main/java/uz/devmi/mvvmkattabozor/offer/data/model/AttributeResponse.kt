package uz.devmi.mvvmkattabozor.offer.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttributeResponse(
    @SerialName("name")
    val name: String?,
    @SerialName("value")
    val value: String?
)
