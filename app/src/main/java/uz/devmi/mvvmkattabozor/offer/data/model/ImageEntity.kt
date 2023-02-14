package uz.devmi.mvvmkattabozor.offer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offer_images")
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val width: Int,
    val height: Int,
    val url: String,
    val offerId: Int
)
