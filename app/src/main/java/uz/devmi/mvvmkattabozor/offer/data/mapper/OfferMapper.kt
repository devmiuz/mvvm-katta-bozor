package uz.devmi.mvvmkattabozor.offer.data.mapper

import uz.devmi.mvvmkattabozor.offer.data.model.*

fun OfferResponse.responseToOfferEntity(): OfferEntity =
    OfferEntity(
        id = id,
        name = name ?: "",
        brand = brand ?: "",
        category = category ?: "",
        merchant = merchant ?: "",
        attributes = attributes?.responsesToAttributes() ?: listOf()
    )


fun List<OfferRelation>.relationsToPreviews(): List<OfferPreview> =
    map { it.relationToPreview() }

fun OfferRelation.relationToPreview(): OfferPreview =
    OfferPreview(
        id = offerEntity.id,
        productName = offerEntity.name,
        merchantName = offerEntity.merchant,
        image = image?.entityToImage() ?: PlaceHolderImage
    )

fun OfferRelation.relationToOffer(): Offer =
    Offer(
        id = offerEntity.id,
        name = offerEntity.name,
        brand = offerEntity.brand,
        category = offerEntity.category,
        merchant = offerEntity.merchant,
        attributes = offerEntity.attributes,
        image = image?.entityToImage() ?: PlaceHolderImage
    )

fun ImageEntity.entityToImage() =
    Image(
        id = id,
        width = width,
        height = height,
        url = url
    )


fun ImageResponse.responseToEntity(offerId: Int): ImageEntity =
    ImageEntity(
        width = width ?: 0,
        height = height ?: 0,
        url = url ?: "",
        offerId = offerId
    )

fun List<AttributeResponse>.responsesToAttributes(): List<Attribute> =
    map { it.responseToAttribute() }

fun AttributeResponse.responseToAttribute(): Attribute =
    Attribute(
        name = name?:"",
        value = value?:""
    )

val PlaceHolderImage = Image(
    id = 1,
    width = 150,
    height = 150,
    url = "https://joadre.com/wp-content/uploads/2019/02/no-image.jpg"
)
