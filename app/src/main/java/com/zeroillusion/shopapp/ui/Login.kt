package com.zeroillusion.shopapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.zeroillusion.shopapp.R
import com.zeroillusion.shopapp.dao.UserDao
import com.zeroillusion.shopapp.data.Database
import com.zeroillusion.shopapp.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDao: UserDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        userDao = Room.databaseBuilder(
            requireContext(),
            Database::class.java, "database"
        ).build().userDao()

        binding.loginBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                if (checkUser(binding.firstNameLogin.text.toString())) {
                    requireActivity().runOnUiThread {
                        findNavController().navigate(R.id.action_login_to_page1)
                    }
                } else {
                    requireActivity().runOnUiThread {
                        Toast.makeText(
                            requireContext(),
                            "Данный пользователь не существует",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        return binding.root
    }

    private suspend fun checkUser(firstName: String): Boolean {
        val user = withContext(Dispatchers.IO) {
            userDao.checkUser(firstName)
        }
        return user != null
    }
}