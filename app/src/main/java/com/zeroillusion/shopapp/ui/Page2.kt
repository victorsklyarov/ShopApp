package com.zeroillusion.shopapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zeroillusion.shopapp.databinding.FragmentPage2Binding

class Page2 : Fragment() {

    private var _binding: FragmentPage2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPage2Binding.inflate(inflater, container, false)

        return binding.root
    }
}