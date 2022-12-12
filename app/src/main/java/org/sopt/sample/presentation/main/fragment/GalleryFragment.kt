package org.sopt.sample.presentation.main.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import org.sopt.sample.databinding.FragmentGalleryBinding
import org.sopt.sample.presentation.main.viewmodel.GalleryViewModel
import org.sopt.sample.util.ContentUriRequestBody

class GalleryFragment(private val userId: Int) : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding: FragmentGalleryBinding
        get() = requireNotNull(_binding) { "갤러리 프래그먼트에서 _binding이 널임" }
    private val viewModel by viewModels<GalleryViewModel>()
    private val imageLoadLauncher = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(3)
    ) {
        loadImage(it)
    }
    private val imageUploadLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {
        viewModel.setRequestBody(ContentUriRequestBody(requireContext(), it!!))
        viewModel.uploadProfileImage(userId)
    }

    private fun loadImage(imageList: List<Uri>) {
        when (imageList.size) {
            0 -> {
                Toast.makeText(requireContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT)
                    .show()
            }
            1 -> {
                binding.ivSample1.load(imageList[0])
            }
            2 -> {
                with(binding) {
                    ivSample1.load(imageList[0])
                    ivSample2.load(imageList[1])
                }
            }
            3 -> {
                with(binding) {
                    ivSample1.load(imageList[0])
                    ivSample2.load(imageList[1])
                    ivSample3.load(imageList[2])
                }
            }
        }
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

        with(binding) {
            btnLoadImage.setOnClickListener {
                imageLoadLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            }
            btnUploadImage.setOnClickListener {
                imageUploadLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            }
        }

        viewModel.result.observe(viewLifecycleOwner) {
            alertResponse(it)
        }

    }

    private fun alertResponse(responseStatusCode: Int) {
        if (responseStatusCode in 200..299) {
            Toast.makeText(requireContext(), "사진이 정상적으로 업로드되었습니다.", Toast.LENGTH_SHORT)
                .show()
        }
        if (responseStatusCode in 400..499) {
            Toast.makeText(
                requireContext(),
                "예기치 못한 오류가 발생했습니다. $responseStatusCode",
                Toast.LENGTH_SHORT
            )
                .show()
        }
        if (responseStatusCode in 500..599) {
            Toast.makeText(
                requireContext(),
                "서버 상태가 원활하지 않습니다. $responseStatusCode",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}