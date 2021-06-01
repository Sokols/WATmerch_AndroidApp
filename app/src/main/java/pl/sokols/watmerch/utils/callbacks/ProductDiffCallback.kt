package pl.sokols.watmerch.utils.callbacks

import androidx.recyclerview.widget.DiffUtil
import pl.sokols.watmerch.data.model.Product

/**
 * Based on:
 * https://medium.com/@trionkidnapper/recyclerview-more-animations-with-less-code-using-support-library-listadapter-62e65126acdb
 */
object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.barcode == newItem.barcode
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}