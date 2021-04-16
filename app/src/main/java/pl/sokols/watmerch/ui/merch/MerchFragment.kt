package pl.sokols.watmerch.ui.merch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.Merch
import pl.sokols.watmerch.databinding.MerchFragmentBinding
import java.io.Serializable

class MerchFragment : Fragment() {

    companion object {
        fun newInstance() = MerchFragment()
    }

    private lateinit var viewModel: MerchViewModel
    private lateinit var binding: MerchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MerchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MerchViewModel::class.java)
        val merch: Merch = arguments?.getParcelable("merchItem")!!
        setBinding(merch)
    }

    private fun setBinding(merch: Merch) {
        binding.titleMerchTextView.text = merch.name
        binding.priceMerchTextView.text = String.format(context?.getString(R.string.price).toString(), merch.price)
    }

}