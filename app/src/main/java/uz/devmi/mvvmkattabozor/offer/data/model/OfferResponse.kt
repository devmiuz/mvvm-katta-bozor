package uz.devmi.mvvmkattabozor.offer.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfferResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String?,
    @SerialName("brand")
    val brand: String?,
    @SerialName("category")
    val category: String?,
    @SerialName("merchant")
    val merchant: String?,
    @SerialName("attributes")
    val attributes: List<AttributeResponse>?,
    @SerialName("image")
    val image: ImageResponse?
)
