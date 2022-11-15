package org.sopt.sample.fragments

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import org.sopt.sample.data.remote.GetUserService
import org.sopt.sample.data.remote.ResponseGetUsersDTO
import org.sopt.sample.data.remote.ServicePool
import org.sopt.sample.fragments.adapters.FollowersAdapter
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.fragments.models.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(){
    private var _binding: FragmentHomeBinding? = null // 정보 얻어오는 용, Nullable
    private val binding: FragmentHomeBinding // 위의 가짜 바인딩에서 null을 뺀 정보를 얻어오는 용
        get() = requireNotNull(_binding) { "홈 프래그먼트에서 _binding이 널임" }
    private val viewModel by viewModels<HomeViewModel>()
    private val adapter by lazy { FollowersAdapter(requireContext()) }
    lateinit var getUserService: GetUserService
    var userList : List<ResponseGetUsersDTO.User>? = null

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
    }

    fun initAdapter(){
        getUserService = ServicePool.getUserService
        getUserService.getUsers().enqueue(object : Callback<ResponseGetUsersDTO>{
            override fun onResponse(
                call: Call<ResponseGetUsersDTO>,
                response: Response<ResponseGetUsersDTO>
            ) {
                if (response.isSuccessful){
                    userList = response.body()?.data
                    viewModel.userList = userList!!
                    binding.rvUsers.layoutManager = GridLayoutManager(context, 3)
                    binding.rvUsers.adapter = adapter
                    adapter.setUserList(viewModel.userList)
                }else{
                    Toast.makeText(context,
                        "데이터를 가져오는데 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseGetUsersDTO>, t: Throwable) {
                Toast.makeText(context,
                    "네트워크 연결 미약", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}