package com.zeroillusion.shopapp.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.zeroillusion.shopapp.R
import com.zeroillusion.shopapp.dao.UserDao
import com.zeroillusion.shopapp.data.Database
import com.zeroillusion.shopapp.model.User
import com.zeroillusion.shopapp.databinding.FragmentSignInPageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInPage : Fragment() {

    private var _binding: FragmentSignInPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDao: UserDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInPageBinding.inflate(inflater, container, false)

        userDao = Room.databaseBuilder(
            requireContext(),
            Database::class.java, "database"
        ).build().userDao()

        binding.logInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signInPage_to_login)
        }

        binding.signInBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                if (checkEmail(binding.email.text.toString())){
                    userDao.insertUser(User(0, binding.firstName.text.toString(), binding.lastName.text.toString(), binding.email.text.toString()))
                    requireActivity().runOnUiThread {
                        binding.firstName.text.clear()
                        binding.lastName.text.clear()
                        binding.email.text.clear()
                        findNavController().navigate(R.id.action_signInPage_to_page1)
                    }
                }
            }
        }
        return binding.root
    }

    private suspend fun checkEmail(email: String): Boolean {
        if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //адрес валидный
            val emailResult = withContext(Dispatchers.IO) {
                userDao.checkEmail(email)
            }
            return if (emailResult != null) {
                //адрес есть в базе
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Пользователь с данным email уже существует", Toast.LENGTH_SHORT).show()
                }
                false
            } else {
                //адреса в базе нет
                true
            }
        } else {
            //адрес невалидный
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), "Введите действительный адрес почты", Toast.LENGTH_SHORT).show()
            }
            return false
        }
    }
}