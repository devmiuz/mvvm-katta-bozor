package uz.devmi.mvvmkattabozor.offer.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import uz.devmi.mvvmkattabozor.base.extention.launchCatchingIn
import uz.devmi.mvvmkattabozor.offer.data.model.OfferDetail
import uz.devmi.mvvmkattabozor.offer.domain.GetOfferDetails
import javax.inject.Inject

@HiltViewModel
class OfferDetailViewModel @Inject constructor(
    private val getOfferDetails: GetOfferDetails
) : ViewModel() {

    private val _offerDetailUiState: MutableStateFlow<OfferDetailUiState> =
        MutableStateFlow(OfferDetailUiState.EMPTY)
    val offerDetailUiState: StateFlow<OfferDetailUiState> = _offerDetailUiState

    fun getOfferDetail(offerId: Int) {
        val params = GetOfferDetails.Params(
            offerId = offerId
        )
        getOfferDetails.invoke(params)
            .onStart {
                _offerDetailUiState.value = OfferDetailUiState.LOADING
            }
            .launchCatchingIn(viewModelScope)
            .onSuccess {
                _offerDetailUiState.value = OfferDetailUiState.SUCCESS(it)
            }
            .onFailure {
                _offerDetailUiState.value = OfferDetailUiState.ERROR(it)
            }
    }
}

sealed class OfferDetailUiState {
    object EMPTY : OfferDetailUiState()
    object LOADING : OfferDetailUiState()
    data class SUCCESS(val offerDetail: OfferDetail) : OfferDetailUiState()
    data class ERROR(val error: Throwable) : OfferDetailUiState()
}