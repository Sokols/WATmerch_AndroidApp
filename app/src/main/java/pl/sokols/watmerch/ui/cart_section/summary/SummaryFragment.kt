package pl.sokols.watmerch.ui.cart_section.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.R
import pl.sokols.watmerch.databinding.SummaryFragmentBinding
import pl.sokols.watmerch.utils.Status
import pl.sokols.watmerch.utils.Utils

@AndroidEntryPoint
class SummaryFragment : Fragment() {

    private val viewModel: SummaryViewModel by viewModels()
    private lateinit var binding: SummaryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SummaryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.setAddresses(
            arguments?.getParcelable(getString(R.string.shipping_address))!!,
            arguments?.getParcelable(getString(R.string.billing_address))!!
        )
        setListeners()
    }

    private fun setListeners() {
        binding.viewModel = viewModel

        binding.applySummaryButton.setOnClickListener {
            viewModel.makePurchase().observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.summaryProgressIndicator.visibility = View.INVISIBLE
                            Utils.getSnackbar(
                                binding.root,
                                resource.data.toString(),
                                requireActivity()
                            ).show()
                            findNavController().navigate(R.id.action_summaryFragment_to_mainFragment)
                        }
                        Status.ERROR -> {
                            binding.summaryProgressIndicator.visibility = View.INVISIBLE
                            Utils.getSnackbar(
                                binding.root,
                                resource.message.toString(),
                                requireActivity()
                            ).show()
                        }
                        Status.LOADING -> {
                            binding.summaryProgressIndicator.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }
}