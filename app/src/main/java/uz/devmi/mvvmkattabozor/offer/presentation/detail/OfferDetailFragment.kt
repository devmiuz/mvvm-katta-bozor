package uz.devmi.mvvmkattabozor.offer.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import uz.devmi.mvvmkattabozor.databinding.OfferDetailFragmentBinding
import uz.devmi.mvvmkattabozor.offer.data.model.OfferDetail

@AndroidEntryPoint
class OfferDetailFragment : Fragment() {

    private var _binding: OfferDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: OfferDetailFragmentArgs by navArgs()

    private val adapter = OfferDetailAdapter()

    private val viewModel: OfferDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OfferDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        viewModel.getOfferDetail(args.offerId)
        lifecycleScope.launchWhenStarted {
            viewModel.offerDetailUiState.collect {
                when (it) {
                    is OfferDetailUiState.LOADING -> {
                        showLoading()
                    }
                    is OfferDetailUiState.SUCCESS -> {
                        showData(it.offerDetail)
                    }
                    is OfferDetailUiState.ERROR -> {
                        showError(it.error)
                    }
                    else -> {}
                }
            }
        }
        binding.btnOfferDetailRetry.setOnClickListener {
            viewModel.getOfferDetail(args.offerId)
        }
    }

    private fun showLoading() {
        with(binding) {
            offerDetailLoadingIndicator.isVisible = true
            lySuccessOfferDetail.isVisible = false
            lyErrorOfferDetail.isVisible = false
        }
    }

    private fun showData(offerDetail: OfferDetail) {
        with(binding) {
            lySuccessOfferDetail.isVisible = true

            adapter.keyValueModels = offerDetail.keyValues
            ivOfferDetail.load(offerDetail.image.url)

            offerDetailLoadingIndicator.isVisible = false
            lyErrorOfferDetail.isVisible = false
        }
    }

    private fun showError(error: Throwable) {
        with(binding) {
            lyErrorOfferDetail.isVisible = true
            tvOfferDetailError.text = error.message

            lySuccessOfferDetail.isVisible = false
            offerDetailLoadingIndicator.isVisible = false
        }
    }

    private fun initAdapter() {
        binding.rvDetail.adapter = adapter
    }
}