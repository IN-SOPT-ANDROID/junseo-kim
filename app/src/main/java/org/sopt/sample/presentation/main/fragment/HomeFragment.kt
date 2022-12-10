package org.sopt.sample.presentation.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import org.sopt.sample.R
import org.sopt.sample.data.remote.model.ResponseGetUserDto
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.presentation.main.adapter.FollowersAdapter
import org.sopt.sample.presentation.main.view.MainActivity
import org.sopt.sample.presentation.main.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null // 정보 얻어오는 용, Nullable
    val binding: FragmentHomeBinding // 위의 가짜 바인딩에서 null을 뺀 정보를 얻어오는 용
        get() = requireNotNull(_binding) { "홈 프래그먼트에서 _binding이 널임" }
    private val viewModel by viewModels<HomeViewModel>()
    private val adapter by lazy { FollowersAdapter(requireContext()) }
    private var userList: List<ResponseGetUserDto.User>? = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false) // 입력할때는 _binding
        return binding.root // 사용할때는 binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        (activity as MainActivity).binding.bnvMain.setOnItemReselectedListener {
            if (it.itemId == R.id.item_home)
                binding.rvUsers.smoothScrollToPosition(0)
        }
    }

    fun initAdapter() {
        viewModel.getUser()
        viewModel.userList.observe(viewLifecycleOwner) {
            userList = it.data
            adapter.setUserList(userList!!)
            binding.rvUsers.adapter = adapter
            binding.rvUsers.layoutManager = GridLayoutManager(requireContext(), 3)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
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