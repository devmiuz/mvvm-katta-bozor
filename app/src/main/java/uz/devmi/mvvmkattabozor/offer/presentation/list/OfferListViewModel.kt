package uz.devmi.mvvmkattabozor.offer.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import uz.devmi.mvvmkattabozor.base.extention.launchCatchingIn
import uz.devmi.mvvmkattabozor.offer.data.model.OfferPreview
import uz.devmi.mvvmkattabozor.offer.domain.GetOfferPreviews
import javax.inject.Inject

@HiltViewModel
class OfferListViewModel @Inject constructor(
    private val getOfferPreviews: GetOfferPreviews
) : ViewModel() {

    private val _offerPreviewUiState: MutableStateFlow<OfferPreviewUiState> =
        MutableStateFlow(OfferPreviewUiState.EMPTY)
    val offerPreviewUiState: StateFlow<OfferPreviewUiState> = _offerPreviewUiState


    fun getOffers() {
        getOfferPreviews()
            .onStart {
                _offerPreviewUiState.value = OfferPreviewUiState.LOADING
            }
            .launchCatchingIn(viewModelScope)
            .onSuccess {
                _offerPreviewUiState.value = OfferPreviewUiState.SUCCESS(it)
            }
            .onFailure {
                _offerPreviewUiState.value = OfferPreviewUiState.ERROR(it)
            }
    }
}

sealed class OfferPreviewUiState {
    object EMPTY : OfferPreviewUiState()
    object LOADING : OfferPreviewUiState()
    data class ERROR(val error: Throwable) : OfferPreviewUiState()
    data class SUCCESS(val data: List<OfferPreview>) : OfferPreviewUiState()
}

