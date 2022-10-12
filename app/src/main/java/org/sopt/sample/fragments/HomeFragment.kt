package org.sopt.sample.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sopt.sample.fragments.adapters.GithubUsersAdapter
import org.sopt.sample.fragments.models.ItemUserModel
import org.sopt.sample.R
import org.sopt.sample.databinding.FragmentHomeBinding

class HomeFragment : Fragment(){
    private var _binding: FragmentHomeBinding? = null // 정보 얻어오는 용, Nullable
    private val binding: FragmentHomeBinding // 위의 가짜 바인딩에서 null을 뺀 정보를 얻어오는 용
        get() = requireNotNull(_binding) { "홈 프래그먼트에서 _binding이 널임" }
    private val exampleList = listOf<ItemUserModel>(
        ItemUserModel(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "giovannijunseokim"
        ),
        ItemUserModel(
            image = R.drawable.github,
            name = "Kim Junseo2",
            repositoryName = "giovannijunseokim"
        ),
        ItemUserModel(
            image = R.drawable.github,
            name = "Kim Junseo3",
            repositoryName = "giovannijunseokim"
        ),
        ItemUserModel(
            image = R.drawable.github,
            name = "Kim Junseo4",
            repositoryName = "giovannijunseokim"
        ),
        ItemUserModel(
            image = R.drawable.github,
            name = "Kim Junseo5",
            repositoryName = "giovannijunseokim"
        )
    )
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
        val adapter = GithubUsersAdapter(requireContext())
        binding.rvGithubUsers.adapter = adapter
        adapter.setUserList(exampleList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}