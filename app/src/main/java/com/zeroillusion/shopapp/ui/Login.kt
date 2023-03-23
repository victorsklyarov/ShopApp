package com.zeroillusion.shopapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.zeroillusion.shopapp.R
import com.zeroillusion.shopapp.databinding.FragmentLoginBinding
import com.zeroillusion.shopapp.utils.Auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Login : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)!!



        binding.loginBtn.setOnClickListener {
            loginBtn()
        }
        return binding.root
    }

    private fun loginBtn() {
        CoroutineScope(Dispatchers.Default).launch {
            if (Auth(requireContext()).login(binding.firstNameLogin.text.toString(), "")) {
                requireActivity().runOnUiThread {
                    with (sharedPref.edit()) {
                        putInt(getString(R.string.saved_session_key), 1)
                        apply()
                    }
                    findNavController().navigate(R.id.action_login_to_page1)
                }
            } else {
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.error_user_not_found),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}