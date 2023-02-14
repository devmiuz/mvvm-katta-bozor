package uz.devmi.mvvmkattabozor.offer.data.repository

import kotlinx.coroutines.flow.Flow
import uz.devmi.mvvmkattabozor.offer.data.model.Offer
import uz.devmi.mvvmkattabozor.offer.data.model.OfferPreview

interface OfferRepository {

    fun getOfferPreviews(): Flow<List<OfferPreview>>

    fun getOfferById(offerId : Int): Flow<Offer>

}