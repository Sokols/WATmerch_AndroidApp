package pl.sokols.watmerch.ui.cart_section.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Address
import pl.sokols.watmerch.databinding.AddressFragmentBinding
import pl.sokols.watmerch.utils.Status
import pl.sokols.watmerch.utils.Utils

@AndroidEntryPoint
class AddressFragment : Fragment() {

    private val viewModel: AddressViewModel by viewModels()
    private lateinit var binding: AddressFragmentBinding

    private lateinit var shippingAddress: Address

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddressFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkIfFirstAddress()
        setListeners()
    }

    private fun checkIfFirstAddress() {
        val address = arguments?.getParcelable<Address>(getString(R.string.shipping_address))
        if (address != null) {
            binding.titleAddressTextView.text = getString(R.string.billing_address)
            binding.sameAddressCheckBox.visibility = View.GONE
            shippingAddress = address
        }
    }

    private fun setListeners() {
        binding.applyAddressButton.setOnClickListener {
            viewModel.provideAddressData(checkRequiredData()).observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            val bundle = Bundle()
                            when {
                                // OPTION 1: this is billing address fragment
                                binding.sameAddressCheckBox.visibility == View.GONE -> {
                                    bundle.putParcelable(
                                        getString(R.string.shipping_address),
                                        shippingAddress
                                    )
                                    bundle.putParcelable(
                                        getString(R.string.billing_address),
                                        resource.data
                                    )
                                    findNavController().navigate(
                                        R.id.action_addressFragment_to_summaryFragment,
                                        bundle
                                    )
                                }
                                // OPTION 2: this is shipping address fragment
                                // and we want to use the same address in the billing
                                binding.sameAddressCheckBox.isChecked -> {
                                    bundle.putParcelable(
                                        getString(R.string.shipping_address),
                                        resource.data
                                    )
                                    bundle.putParcelable(
                                        getString(R.string.billing_address),
                                        resource.data
                                    )
                                    findNavController().navigate(
                                        R.id.action_addressFragment_to_summaryFragment,
                                        bundle
                                    )
                                }
                                // OPTION 3: this is shipping address fragment
                                // and we DON'T want to use the same adderss in the billing
                                else -> {
                                    bundle.putParcelable(
                                        getString(R.string.shipping_address),
                                        resource.data
                                    )
                                    findNavController().navigate(
                                        R.id.action_addressFragment_self,
                                        bundle
                                    )
                                }
                            }
                        }
                        Status.ERROR -> {
                            Utils.getSnackbar(
                                binding.root,
                                resource.message.toString(),
                                requireActivity()
                            ).show()
                        }
                        Status.LOADING -> {
                        }
                    }
                }
            })
        }

        binding.userDataAddressCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setUserData(isChecked)
        }

        binding.goBackAddressTextView.setOnClickListener {
            findNavController().navigate(R.id.action_addressFragment_to_cartFragment)
        }
    }

    private fun checkRequiredData(): Boolean =
        binding.addressUserAddressForm.firstNameAddressEditText.text.toString().isNotEmpty() &&
                binding.addressUserAddressForm.lastNameAddressEditText.text.toString()
                    .isNotEmpty() &&
                binding.addressUserAddressForm.phoneAddressEditText.text.toString()
                    .isNotEmpty() &&
                binding.addressUserAddressForm.countryAddressEditText.text.toString()
                    .isNotEmpty() &&
                binding.addressUserAddressForm.streetAddressEditText.text.toString()
                    .isNotEmpty() &&
                binding.addressUserAddressForm.postalCodeAddressEditText.text.toString()
                    .isNotEmpty() &&
                binding.addressUserAddressForm.cityAddressEditText.text.toString()
                    .isNotEmpty()
}