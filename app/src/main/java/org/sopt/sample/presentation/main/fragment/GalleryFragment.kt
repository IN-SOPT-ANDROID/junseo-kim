package org.sopt.sample.presentation.main.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import coil.load
import org.sopt.sample.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding: FragmentGalleryBinding
        get() = requireNotNull(_binding) { "갤러리 프래그먼트에서 _binding이 널임" }
    private val imageLauncher =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) {
            loadImage(it)
        }

    private fun loadImage(imageList: List<Uri>) {
        binding.ivSample1.load(imageList[0])
        binding.ivSample2.load(imageList[1])
        binding.ivSample3.load(imageList[2])
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLoadImage.setOnClickListener {
            imageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}