package uz.devmi.mvvmkattabozor.offer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Attribute(
    val name: String,
    val value: String
)
