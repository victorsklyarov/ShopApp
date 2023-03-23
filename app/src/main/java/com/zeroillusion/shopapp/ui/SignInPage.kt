package com.zeroillusion.shopapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zeroillusion.shopapp.R
import com.zeroillusion.shopapp.databinding.FragmentSignInPageBinding
import com.zeroillusion.shopapp.utils.Auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInPage : Fragment() {

    private var _binding: FragmentSignInPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInPageBinding.inflate(inflater, container, false)

        sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)!!

        val checkLogin = sharedPref.getInt(getString(R.string.saved_session_key), 0)
        if (checkLogin == 1){
            findNavController().navigate(R.id.action_signInPage_to_page1)
        }

        binding.logInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signInPage_to_login)
        }

        binding.signInBtn.setOnClickListener {
            signInBtn()
        }
        return binding.root
    }

    private fun signInBtn() {
        CoroutineScope(Dispatchers.Default).launch {
            when (
                Auth(requireContext()).signIn(
                    binding.firstName.text.toString(),
                    binding.lastName.text.toString(),
                    binding.email.text.toString()
                )
            ) {
                1 -> {
                    requireActivity().runOnUiThread {
                        binding.firstName.text.clear()
                        binding.lastName.text.clear()
                        binding.email.text.clear()
                        with (sharedPref.edit()) {
                            putInt(getString(R.string.saved_session_key), 1)
                            apply()
                        }
                        findNavController().navigate(R.id.action_signInPage_to_page1)
                    }
                }
                0 -> {
                    requireActivity().runOnUiThread {
                        Toast.makeText(
                            requireContext(),
                            resources.getString(R.string.error_valid_email),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                -1 -> {
                    requireActivity().runOnUiThread {
                        Toast.makeText(
                            requireContext(),
                            resources.getString(R.string.error_user_already_exist),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}