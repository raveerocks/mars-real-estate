package io.raveerocks.marsrealestate.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.raveerocks.marsrealestate.databinding.FragmentDetailBinding
import io.raveerocks.marsrealestate.view.detail.DetailViewModel
import io.raveerocks.marsrealestate.view.detail.DetailViewModelFactory

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val marsProperty = DetailFragmentArgs.fromBundle(
            requireArguments()
        ).selectedProperty
        val viewModelFactory = DetailViewModelFactory(marsProperty, application)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        return binding.root
    }
}
