package uz.devmi.mvvmkattabozor.offer.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import uz.devmi.mvvmkattabozor.base.extention.flatMapResult
import uz.devmi.mvvmkattabozor.offer.data.model.OfferPreview
import uz.devmi.mvvmkattabozor.offer.data.repository.OfferRepository
import javax.inject.Inject

class GetOfferPreviews @Inject constructor(
    private val offerRepository: OfferRepository
) {

    operator fun invoke(): Flow<Result<List<OfferPreview>>> {
        return offerRepository
            .getOfferPreviews()
            .flatMapResult()
            .flowOn(Dispatchers.IO)
    }
}