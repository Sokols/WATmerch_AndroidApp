package pl.sokols.watmerch.utils.callbacks

import androidx.recyclerview.widget.DiffUtil
import pl.sokols.watmerch.data.model.OrderProduct

/**
 * Based on:
 * https://medium.com/@trionkidnapper/recyclerview-more-animations-with-less-code-using-support-library-listadapter-62e65126acdb
 */
object OrderProductDiffCallback : DiffUtil.ItemCallback<OrderProduct>() {
    override fun areItemsTheSame(oldItem: OrderProduct, newItem: OrderProduct): Boolean {
        return oldItem.product!!.barcode == newItem.product!!.barcode
    }

    override fun areContentsTheSame(oldItem: OrderProduct, newItem: OrderProduct): Boolean {
        return oldItem == newItem
    }
}