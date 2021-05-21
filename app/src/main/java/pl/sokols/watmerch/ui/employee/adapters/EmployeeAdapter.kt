package pl.sokols.watmerch.ui.employee.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.databinding.EmployeeProductItemBinding

class EmployeeAdapter(
    private val dataSet: List<Product>
) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    inner class EmployeeViewHolder(private val binding: EmployeeProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.product = product
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder(
            EmployeeProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size
}