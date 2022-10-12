package org.sopt.sample.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.fragments.models.ItemUserModel
import org.sopt.sample.databinding.ItemUsersBinding

class GithubUsersAdapter(context: Context):RecyclerView.Adapter<GithubUsersAdapter.GithubUsersViewHolder>(){
    private val inflater by lazy { LayoutInflater.from(context) }
    private var userList: List<ItemUserModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUsersViewHolder {
        val binding = ItemUsersBinding.inflate(inflater, parent, false)
        return GithubUsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GithubUsersViewHolder, position: Int) {
        holder.onBind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setUserList(userList: List<ItemUserModel>){
        this.userList = userList.toList() // toList 해주는 이유 > 얕은 복사를 위해
        notifyDataSetChanged()
    }

    inner class GithubUsersViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data : ItemUserModel){
            binding.ivGithubLogo.setImageResource(data.image)
            binding.tvRepoName.text = data.repositoryName
            binding.tvUserName.text = data.name
        }
    }
}