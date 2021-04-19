package pl.sokols.watmerch.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pl.sokols.watmerch.R
import pl.sokols.watmerch.Utils
import pl.sokols.watmerch.databinding.MainFragmentBinding
import pl.sokols.watmerch.ui.main.adapters.MerchListAdapter

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initComponents()
    }

    private fun initComponents() {
        val merchListAdapter = MerchListAdapter(Utils.exampleArray())
        binding.mainRecyclerView.adapter = merchListAdapter
    }
}