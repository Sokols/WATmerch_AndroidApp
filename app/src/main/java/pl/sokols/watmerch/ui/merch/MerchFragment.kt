package pl.sokols.watmerch.ui.merch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import pl.sokols.watmerch.BasicApp
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Merch
import pl.sokols.watmerch.databinding.MerchFragmentBinding

class MerchFragment : Fragment() {

    companion object {
        fun newInstance() = MerchFragment()
    }

    private val viewModel: MerchViewModel by viewModels {
        MerchViewModelFactory((requireActivity().application as BasicApp).repository)
    }
    private lateinit var binding: MerchFragmentBinding
    private lateinit var merch: Merch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MerchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        merch = arguments?.getParcelable("merchItem")!!
        setBinding()
        setObservers()
    }

    private fun setObservers() {
        binding.addToCartMerchButton.setOnClickListener {
            viewModel.insert(merch)
        }
    }

    private fun setBinding() {
        binding.titleMerchTextView.text = merch.name
        binding.priceMerchTextView.text =
            String.format(context?.getString(R.string.price).toString(), merch.price)
    }
}