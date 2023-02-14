package uz.devmi.mvvmkattabozor.offer.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.devmi.mvvmkattabozor.databinding.OfferListFragmentBinding

@AndroidEntryPoint
class OfferListFragment : Fragment() {

    private var _binding: OfferListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OfferListViewModel by viewModels()

    private val adapter = OfferListAdapter {
        val action = OfferListFragmentDirections.actionOfferListFragmentToOfferDetailFragment(it)
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OfferListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewModel.getOffers()

        binding.swOfferList.setOnRefreshListener {
            viewModel.getOffers()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.offerPreviewUiState.collect {
                when (it) {
                    is OfferPreviewUiState.SUCCESS -> {
                        adapter.addItems(it.data)
                        binding.swOfferList.isRefreshing = false
                    }
                    is OfferPreviewUiState.LOADING -> {
                        binding.swOfferList.isRefreshing = true
                    }
                    is OfferPreviewUiState.ERROR -> {
                        binding.swOfferList.isRefreshing = false
                        Toast.makeText(
                            requireContext(),
                            "Takliflarni olishda xatolik yuz berdi",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is OfferPreviewUiState.EMPTY -> {}
                }
            }
        }
    }

    private fun initAdapter() {
        binding.rvOfferList.adapter = adapter
    }

}