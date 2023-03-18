package com.zeroillusion.shopapp.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.squareup.picasso.Picasso
import com.zeroillusion.shopapp.R
import com.zeroillusion.shopapp.databinding.ItemBlockBinding
import com.zeroillusion.shopapp.databinding.ItemCategoryBinding
import com.zeroillusion.shopapp.databinding.ItemFlashSaleBinding
import com.zeroillusion.shopapp.databinding.ItemLatestBinding
import com.zeroillusion.shopapp.model.*

fun categoryAdapterDelegate() = adapterDelegateViewBinding<Category, DisplayableItem, ItemCategoryBinding>(
    { layoutInflater, root -> ItemCategoryBinding.inflate(layoutInflater, root, false) }
        ) {
            bind {
                binding.titleCategory.text = item.title
                binding.imageCategory.setImageResource(item.img)
            }
        }

fun latestListAdapterDelegate() =
    adapterDelegateViewBinding<BlockLatest, DisplayableItem, ItemBlockBinding>(
        { layoutInflater, parent ->
            ItemBlockBinding.inflate(layoutInflater, parent, false)
                .apply {
                    rvBlock.adapter = ListDelegationAdapter(latestAdapterDelegate())
                }
        }
    ) {
        bind {
            (binding.rvBlock.adapter as ListDelegationAdapter<List<Latest>>).apply {
                items = item.items
                notifyDataSetChanged()
            }
            binding.blockName.text = item.title
        }
    }

fun latestAdapterDelegate() =
    adapterDelegateViewBinding<Latest, DisplayableItem, ItemLatestBinding>(
        { layoutInflater, root -> ItemLatestBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            Picasso.get().load(item.image_url).fit().centerCrop().into(binding.imageLatest)
            binding.imageLatest.clipToOutline = true
            binding.categoryLatest.text = item.category
            binding.nameLatest.text = item.name
            binding.priceLatest.text = "$ ${item.price}"
        }
    }

fun flashSaleListAdapterDelegate() =
    adapterDelegateViewBinding<BlockFlashSale, DisplayableItem, ItemBlockBinding>(
        { layoutInflater, parent ->
            ItemBlockBinding.inflate(layoutInflater, parent, false).apply {
                rvBlock.adapter = ListDelegationAdapter(flashSaleAdapterDelegate())
            }
        }
    ) {
        bind {
            (binding.rvBlock.adapter as ListDelegationAdapter<List<FlashSale>>).apply {
                items = item.items
                notifyDataSetChanged()
            }
            binding.blockName.text = item.title
        }
    }

fun flashSaleAdapterDelegate() =
    adapterDelegateViewBinding<FlashSale, DisplayableItem, ItemFlashSaleBinding>(
        { layoutInflater, root -> ItemFlashSaleBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            Picasso.get().load(item.image_url).fit().centerCrop().into(binding.imageFlash)
            binding.imageFlash.clipToOutline = true
            binding.seller.setImageResource(R.drawable.profile)
            binding.seller.clipToOutline = true
            binding.discountFlash.text = "${item.discount}% off"
            binding.categoryFlash.text = item.category
            binding.nameFlash.text = item.name
            binding.priceFlash.text = "$ ${item.price}"
        }
    }