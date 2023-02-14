package uz.devmi.mvvmkattabozor.offer.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import uz.devmi.mvvmkattabozor.base.extention.flatMapResult
import uz.devmi.mvvmkattabozor.offer.data.model.KeyValueModel
import uz.devmi.mvvmkattabozor.offer.data.model.Offer
import uz.devmi.mvvmkattabozor.offer.data.model.OfferDetail
import uz.devmi.mvvmkattabozor.offer.data.repository.OfferRepository
import javax.inject.Inject

class GetOfferDetails @Inject constructor(
    private val offerRepository: OfferRepository
) {

    data class Params(
        val offerId: Int
    )

    operator fun invoke(params: Params): Flow<Result<OfferDetail>> {
        return offerRepository
            .getOfferById(params.offerId)
            .map {
                OfferDetail(
                    offerId = it.id,
                    image = it.image,
                    keyValues = generateKeyValues(it)
                )
            }
            .flatMapResult()
            .flowOn(Dispatchers.IO)
    }

    private fun generateKeyValues(offer: Offer): List<KeyValueModel> {
        val keyValues = arrayListOf<KeyValueModel>()
        keyValues.add(KeyValueModel("Nom", offer.name))
        keyValues.add(KeyValueModel("Brend", offer.brand))
        keyValues.add(KeyValueModel("Kategoriya", offer.category))
        keyValues.add(KeyValueModel("Merchant", offer.merchant))
        offer.attributes.forEach {
            keyValues.add(KeyValueModel(it.name, it.value))
        }
        return keyValues
    }
}