package uz.devmi.mvvmkattabozor.offer.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.devmi.mvvmkattabozor.base.BindingViewHolder
import uz.devmi.mvvmkattabozor.base.extention.loadImage
import uz.devmi.mvvmkattabozor.databinding.ItemOfferListBinding
import uz.devmi.mvvmkattabozor.offer.data.model.OfferPreview

class OfferListAdapter(
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<OfferListAdapter.OfferListViewHolder>() {

    private val offerPreviews = arrayListOf<OfferPreview>()

    fun addItems(offerPreviews: List<OfferPreview>) {
        this.offerPreviews.clear()
        this.offerPreviews.addAll(offerPreviews)
        notifyDataSetChanged()
    }

    inner class OfferListViewHolder(binding: ItemOfferListBinding) :
        BindingViewHolder<ItemOfferListBinding>(binding) {

        init {
            binding.root.setOnClickListener {
                onClick(offerPreviews[adapterPosition].id)
            }
        }

        fun bind(offerPreview: OfferPreview) {
            with(binding) {
                with(offerPreview) {
                    tvOfferPreviewProduct.text = productName
                    tvOfferPreviewMerchant.text = merchantName
                    ivOfferPreview.loadImage(image)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferListViewHolder {
        val binding =
            ItemOfferListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferListViewHolder(binding)
    }

    override fun getItemCount(): Int = offerPreviews.size

    override fun onBindViewHolder(holder: OfferListViewHolder, position: Int) {
        val offerPreview = offerPreviews[position]
        holder.bind(offerPreview)
    }
}