package uz.devmi.mvvmkattabozor.offer.data.model

data class OfferDetail(
    val offerId: Int,
    val image: Image,
    val keyValues: List<KeyValueModel>
)
