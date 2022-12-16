package org.sopt.sample.presentation.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.sopt.sample.R
import org.sopt.sample.databinding.FragmentMusicBinding
import org.sopt.sample.presentation.main.adapter.MusicsAdapter
import org.sopt.sample.presentation.main.view.MainActivity
import org.sopt.sample.presentation.main.viewmodel.MusicViewModel
import org.sopt.sample.util.ContentUriRequestBody

class MusicFragment : Fragment() {
    private var _binding: FragmentMusicBinding? = null
    val binding: FragmentMusicBinding
        get() = requireNotNull(_binding) { "갤러리 프래그먼트에서 _binding이 널임" }
    private val adapter by lazy { MusicsAdapter(requireContext()) }
    private val viewModel by viewModels<MusicViewModel>()

    private val singer = "안드로이드"
    private val title = "다음 팟장은 누구일까"

    private fun String.toRequestBody() = toRequestBody("application/json".toMediaTypeOrNull())
    private val musicRegisterLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            viewModel.registerMusic(
                ContentUriRequestBody(requireContext(), it),
                buildJsonObject {
                    put("singer", singer)
                    put("title", title)
                }.toString().toRequestBody()
            )
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        (activity as MainActivity).binding.bnvMain.setOnItemReselectedListener {
            if (it.itemId == R.id.item_music)
                binding.rvMusics.smoothScrollToPosition(0)
        }

        viewModel.getMusicList()
        observeMusicList()
        observeRegisterMusicResult()

        binding.btnRegisterMusic.setOnClickListener {
            musicRegisterLauncher.launch(PickVisualMediaRequest())
        }
    }

    private fun observeRegisterMusicResult() {
        viewModel.registerMusicResult.observe(viewLifecycleOwner) {
            alertResponse(it)
            viewModel.getMusicList()
        }
    }

    private fun observeMusicList() {
        viewModel.musicList.observe(viewLifecycleOwner) {
            adapter.setMusicList(it)
        }
        viewModel.getMusicResult.observe(viewLifecycleOwner) {
            showErrorToast(it)
        }
    }

    private fun initAdapter() {
        binding.rvMusics.adapter = adapter
        binding.rvMusics.layoutManager = LinearLayoutManager(requireContext())
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
            ).show()
        }
    }

    private fun showErrorToast(errorCode: Int) {
        if (errorCode in 400..499) {
            Toast.makeText(
                context,
                "상태 코드 : $errorCode, 클라이언트 요청 오류 발생",
                Toast.LENGTH_SHORT
            ).show()
        } else if (errorCode >= 500) {
            Toast.makeText(
                context,
                "상태 코드 : $errorCode, 서버 응답 오류 발생",
                Toast.LENGTH_SHORT
            ).show()
        } else
            Toast.makeText(
                context,
                "상태 코드 : $errorCode, 오류 발생",
                Toast.LENGTH_SHORT
            ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}