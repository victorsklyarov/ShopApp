package com.zeroillusion.shopapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.zeroillusion.shopapp.R
import com.zeroillusion.shopapp.adapter.categoryAdapterDelegate
import com.zeroillusion.shopapp.adapter.flashSaleListAdapterDelegate
import com.zeroillusion.shopapp.adapter.latestListAdapterDelegate
import com.zeroillusion.shopapp.api.ApiService
import com.zeroillusion.shopapp.databinding.FragmentPage1Binding
import com.zeroillusion.shopapp.model.Category
import com.zeroillusion.shopapp.repository.MainRepository
import com.zeroillusion.shopapp.utils.decodeBase64
import com.zeroillusion.shopapp.viewmodel.MainViewModel
import com.zeroillusion.shopapp.viewmodel.ViewModelFactory

class Page1 : Fragment() {

    private var _binding: FragmentPage1Binding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels() {
        ViewModelFactory(
            MainRepository(ApiService.getInstance())
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPage1Binding.inflate(inflater, container, false)

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        val adapter = ListDelegationAdapter(
            latestListAdapterDelegate(),
            flashSaleListAdapterDelegate()
        )

        viewModel.profilePhoto.observe(viewLifecycleOwner) {
            binding.toolbar.imageToolbar.setImageBitmap(it?.let { itNotNull ->
                decodeBase64(itNotNull)
            })
        }

        binding.toolbar.imageToolbar.clipToOutline = true

        viewModel.setCategory(
            arrayListOf(
                Category(resources.getString(R.string.category_phones), R.drawable.ic_phone),
                Category(
                    resources.getString(R.string.category_headphones),
                    R.drawable.ic_headphone
                ),
                Category(resources.getString(R.string.category_games), R.drawable.ic_game),
                Category(resources.getString(R.string.category_cars), R.drawable.ic_car),
                Category(resources.getString(R.string.category_furniture), R.drawable.ic_furniture),
                Category(resources.getString(R.string.category_kids), R.drawable.ic_kids)
            )
        )

        val catAdapter = ListDelegationAdapter(categoryAdapterDelegate())
        binding.rvCategory.apply {
            this.adapter = catAdapter
            this.layoutManager =
                LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
            this.itemAnimator = DefaultItemAnimator()
        }
        viewModel.categoryItems.observe(viewLifecycleOwner) {
            catAdapter.items = it
            catAdapter.notifyDataSetChanged()
        }

        binding.rvMain.apply {
            this.adapter = adapter
            this.layoutManager =
                LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            this.itemAnimator = DefaultItemAnimator()
        }
        viewModel.displayableItems.observe(viewLifecycleOwner) {
            adapter.items = it
            adapter.notifyDataSetChanged()
        }

        viewModel.getPageContent()
        return binding.root
    }
}