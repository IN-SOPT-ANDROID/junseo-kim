package org.sopt.sample.presentation.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.sopt.sample.R
import org.sopt.sample.databinding.FragmentMusicBinding
import org.sopt.sample.presentation.main.adapter.MusicsAdapter
import org.sopt.sample.presentation.main.view.MainActivity
import org.sopt.sample.presentation.main.viewmodel.MusicViewModel

class MusicFragment : Fragment() {
    private var _binding: FragmentMusicBinding? = null
    val binding: FragmentMusicBinding
        get() = requireNotNull(_binding) { "갤러리 프래그먼트에서 _binding이 널임" }
    private val adapter by lazy { MusicsAdapter(requireContext()) }
    private val viewModel by viewModels<MusicViewModel>()

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
        binding.rvMusics.adapter = adapter
        binding.rvMusics.layoutManager = LinearLayoutManager(requireContext())

        (activity as MainActivity).binding.bnvMain.setOnItemReselectedListener {
            if (it.itemId == R.id.item_music)
                binding.rvMusics.smoothScrollToPosition(0)
        }

        viewModel.getMusicList()
        viewModel.musicList.observe(viewLifecycleOwner) {
            adapter.setMusicList(it)
        }

        viewModel.result.observe(viewLifecycleOwner) {
            showErrorToast(it)
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