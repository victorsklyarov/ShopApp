package com.zeroillusion.shopapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.zeroillusion.shopapp.R
import com.zeroillusion.shopapp.adapter.categoryAdapterDelegate
import com.zeroillusion.shopapp.adapter.flashSaleAdapterDelegate
import com.zeroillusion.shopapp.adapter.flashSaleListAdapterDelegate
import com.zeroillusion.shopapp.adapter.latestListAdapterDelegate
import com.zeroillusion.shopapp.api.ApiService
import com.zeroillusion.shopapp.databinding.FragmentPage1Binding
import com.zeroillusion.shopapp.model.Category
import com.zeroillusion.shopapp.model.DisplayableItem
import com.zeroillusion.shopapp.repository.MainRepository
import com.zeroillusion.shopapp.viewmodel.MainViewModel
import com.zeroillusion.shopapp.viewmodel.ViewModelFactory

class Page1 : Fragment() {

    private var _binding: FragmentPage1Binding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPage1Binding.inflate(inflater, container, false)

        val apiService = ApiService.getInstance()
        val mainRepository = MainRepository(apiService)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(mainRepository)
        )[MainViewModel::class.java]

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
/*
        viewModel.latestList.observe(requireActivity()) {
            binding.rvLatest.apply {
                this.adapter = ListDelegationAdapter(latestAdapterDelegate ({
                    Toast.makeText(requireActivity(), it.name, Toast.LENGTH_SHORT).show()
                }, {
                    Toast.makeText(requireActivity(), "${it.name} added to cart", Toast.LENGTH_SHORT).show()
                })).apply {
                    //items = getItemList()
                    items = viewModel.latestList.value?.toMutableList<DisplayableItem>()
                }
                this.layoutManager =
                    LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
                this.itemAnimator = DefaultItemAnimator()
            }
        }

        viewModel.flashSaleList.observe(requireActivity()) {
            binding.rvFlash.apply {
                this.adapter = ListDelegationAdapter(flashSaleAdapterDelegate {
                    Toast.makeText(requireActivity(), it.name, Toast.LENGTH_SHORT).show()
                }).apply {
                    items = viewModel.flashSaleList.value?.toMutableList<DisplayableItem>()
                }
                this.layoutManager =
                    LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
                this.itemAnimator = DefaultItemAnimator()
            }
        }
*/
        binding.toolbar.imageToolbar.clipToOutline = true

        viewModel.setCategory(
            arrayListOf(
                    Category("Phones", R.drawable.ic_phone),
                    Category("Headphones", R.drawable.ic_headphone),
                    Category("Games", R.drawable.ic_game),
                    Category("Cars", R.drawable.ic_car),
                    Category("Furniture", R.drawable.ic_furniture),
                    Category("Kids", R.drawable.ic_kids)
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