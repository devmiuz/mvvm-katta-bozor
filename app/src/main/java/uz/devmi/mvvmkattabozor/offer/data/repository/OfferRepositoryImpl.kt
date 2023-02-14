package uz.devmi.mvvmkattabozor.offer.data.repository

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import uz.devmi.mvvmkattabozor.offer.data.*
import uz.devmi.mvvmkattabozor.offer.data.dao.OfferDao
import uz.devmi.mvvmkattabozor.offer.data.dao.OfferImageDao
import uz.devmi.mvvmkattabozor.offer.data.mapper.relationToOffer
import uz.devmi.mvvmkattabozor.offer.data.mapper.relationsToPreviews
import uz.devmi.mvvmkattabozor.offer.data.mapper.responseToEntity
import uz.devmi.mvvmkattabozor.offer.data.mapper.responseToOfferEntity
import uz.devmi.mvvmkattabozor.offer.data.model.Offer
import uz.devmi.mvvmkattabozor.offer.data.model.OfferPreview
import uz.devmi.mvvmkattabozor.offer.data.model.OffersResponse
import uz.devmi.mvvmkattabozor.offer.data.rest_service.OfferRestService
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(
    private val offerRestService: OfferRestService,
    private val offerDao: OfferDao,
    private val offerImageDao: OfferImageDao
) : OfferRepository {

    @OptIn(FlowPreview::class)
    override fun getOfferPreviews(): Flow<List<OfferPreview>> {
        return offerRestService.getOffers()
            .onEach { response ->
                response.offers?.let { offerResponses ->
                    offerResponses.forEach { offerResponse ->
                        offerDao.insert(offerResponse.responseToOfferEntity())
                        offerResponse.image?.let {
                            offerImageDao.insert(it.responseToEntity(offerResponse.id))
                        }
                    }
                }
            }
            .catch {
                if (it is ConnectException || it is UnknownHostException) {
                    emit(OffersResponse(null))
                } else
                    throw it
            }
            .flatMapConcat {
                offerDao.getAll()
            }
            .map {
                it.relationsToPreviews()
            }
    }

    override fun getOfferById(offerId: Int): Flow<Offer> {
        return offerDao.getById(offerId)
            .map {
                it.relationToOffer()
            }
    }
}