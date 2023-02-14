package uz.devmi.mvvmkattabozor.offer.data.model

import androidx.room.Embedded
import androidx.room.Relation


data class OfferRelation(
    @Embedded
    val offerEntity: OfferEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "offerId"
    )
    val image: ImageEntity?
)