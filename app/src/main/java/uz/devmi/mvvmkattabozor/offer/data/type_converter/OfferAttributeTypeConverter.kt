package uz.devmi.mvvmkattabozor.offer.data.type_converter

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import uz.devmi.mvvmkattabozor.base.extention.actual
import uz.devmi.mvvmkattabozor.offer.data.model.Attribute

class OfferAttributeTypeConverter {

    @TypeConverter
    fun attributesToString(attributes: List<Attribute>): String {
        return Json.Default.actual.encodeToString(attributes)
    }

    @TypeConverter
    fun stringToAttribute(jsonObject: String): List<Attribute> {
        return Json.Default.actual.decodeFromString(jsonObject)
    }
}