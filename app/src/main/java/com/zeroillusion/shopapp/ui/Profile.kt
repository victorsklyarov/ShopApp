package com.zeroillusion.shopapp.ui

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.zeroillusion.shopapp.R
import com.zeroillusion.shopapp.api.ApiService
import com.zeroillusion.shopapp.databinding.FragmentProfileBinding
import com.zeroillusion.shopapp.repository.MainRepository
import com.zeroillusion.shopapp.utils.decodeBase64
import com.zeroillusion.shopapp.utils.encodeBase64
import com.zeroillusion.shopapp.viewmodel.MainViewModel
import com.zeroillusion.shopapp.viewmodel.ViewModelFactory


class Profile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels() {
        ViewModelFactory(
            MainRepository(ApiService.getInstance())
        )
    }

    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)!!

        viewModel.profilePhoto.observe(viewLifecycleOwner) {
            binding.imageProfile.setImageBitmap(it?.let { itNotNull -> decodeBase64(itNotNull) })
        }

        val photoPickerLauncher =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    viewModel.setProfilePhoto(encodeBase64(this, uri))
                    with (sharedPref.edit()) {
                        putString(getString(R.string.saved_image_key), encodeBase64(this@Profile, uri))
                        apply()
                    }
                }
            }

        binding.imageProfile.clipToOutline = true

        binding.changePhoto.setOnClickListener {
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.leftArrowProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_page1)
        }

        binding.logOut.setOnClickListener {
            with (sharedPref.edit()) {
                putInt(getString(R.string.saved_session_key), 0)
                putString(getString(R.string.saved_image_key), "")
                apply()
            }
            viewModel.setProfilePhoto(encodeBase64(this@Profile,
                Uri.parse(
                    ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://${resources.getResourcePackageName(R.drawable.profile)}" +
                            "/${resources.getResourceTypeName(R.drawable.profile)}" +
                            "/${resources.getResourceEntryName(R.drawable.profile)}")))
            findNavController().navigate(R.id.action_profile_to_signInPage)
        }

        return binding.root
    }
}