package uz.devmi.mvvmkattabozor.offer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import uz.devmi.mvvmkattabozor.offer.data.type_converter.OfferAttributeTypeConverter

@Entity(tableName = "offers")
@TypeConverters(OfferAttributeTypeConverter::class)
data class OfferEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val brand: String,
    val category: String,
    val merchant: String,
    val attributes: List<Attribute> = arrayListOf()
)
