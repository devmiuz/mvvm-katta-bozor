package uz.devmi.mvvmkattabozor.offer.data.rest_service

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import uz.devmi.mvvmkattabozor.offer.data.model.OffersResponse

interface OfferRestService {

    @GET(API_OFFERS)
    fun getOffers(): Flow<OffersResponse>

    private companion object {
        const val API_OFFERS = "test/api/v1/offers"
    }
}