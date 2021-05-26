package pl.sokols.watmerch.ui.main_section.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.databinding.ProductItemBinding
import pl.sokols.watmerch.utils.Utils

class ProductListAdapter(
    private val dataSet: List<Product>?
) : RecyclerView.Adapter<ProductListAdapter.MerchListViewHolder>() {

    inner class MerchListViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product

            binding.imageProductImageView.setImageBitmap(Utils.getBitmapFromString(product.basicDetails?.logoImage))

            binding.productItemCardView.setOnClickListener { view ->
                val bundle = bundleOf(Utils.PRODUCT_BARCODE to product.barcode)
                view.findNavController()
                    .navigate(R.id.action_mainFragment_to_productFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchListViewHolder {
        return MerchListViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MerchListViewHolder, position: Int) {
        holder.bind(dataSet!![position])
    }

    override fun getItemCount(): Int = dataSet?.size!!
}