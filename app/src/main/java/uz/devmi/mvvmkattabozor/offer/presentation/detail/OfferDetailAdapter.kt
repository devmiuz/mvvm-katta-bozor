package uz.devmi.mvvmkattabozor.offer.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.devmi.mvvmkattabozor.base.BindingViewHolder
import uz.devmi.mvvmkattabozor.databinding.ItemOfferDetailBinding
import uz.devmi.mvvmkattabozor.offer.data.model.KeyValueModel

class OfferDetailAdapter: RecyclerView.Adapter<OfferDetailAdapter.OfferDetailViewHolder>() {

    var keyValueModels = listOf<KeyValueModel>()
        set(value) {
            field = value
            notifyItemRangeInserted(0, value.size)
        }

    inner class OfferDetailViewHolder(binding: ItemOfferDetailBinding) :
        BindingViewHolder<ItemOfferDetailBinding>(binding) {

        fun bind(keyValueModel: KeyValueModel) {

            with(binding) {
                with(keyValueModel) {
                    tvKey.text = key
                    tvValue.text = value
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferDetailViewHolder {
        val binding =
            ItemOfferDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferDetailViewHolder(binding)
    }

    override fun getItemCount(): Int = keyValueModels.size

    override fun onBindViewHolder(holder: OfferDetailViewHolder, position: Int) {
        val offerPreview = keyValueModels[position]
        holder.bind(offerPreview)
    }
}