package com.zeroillusion.shopapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.zeroillusion.shopapp.R
import com.zeroillusion.shopapp.databinding.FragmentProfileBinding

class Profile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.imageProfile.clipToOutline = true

        binding.changePhoto.setOnClickListener {
            Toast.makeText(requireContext(), "Change user photo", Toast.LENGTH_SHORT).show()
        }

        binding.logOut.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_signInPage)
        }

        return binding.root
    }
}