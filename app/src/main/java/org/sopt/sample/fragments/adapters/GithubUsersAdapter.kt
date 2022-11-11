package org.sopt.sample.fragments.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent
import org.sopt.sample.databinding.ItemDescriptionBinding
import org.sopt.sample.fragments.models.UserRepo
import org.sopt.sample.databinding.ItemUsersBinding

class GithubUsersAdapter(context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val inflater by lazy { LayoutInflater.from(context) }
    private var userList: List<UserRepo> = emptyList()

    override fun getItemViewType(position: Int): Int {
        return userList[position].dataType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            header   -> {
                val binding = ItemDescriptionBinding.inflate(inflater, parent, false)
                DescriptionViewHolder(binding)
            }
            else -> {
                val binding = ItemUsersBinding.inflate(inflater, parent, false)
                GithubUsersViewHolder(binding)
            }
        }
    }

    class DescriptionViewHolder(private val binding: ItemDescriptionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data : UserRepo){
            binding.tvRepoDescription.text = data.name
        }
    }

    class GithubUsersViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data : UserRepo){
            binding.ivGithubLogo.setImageResource(data.image!!)
            binding.tvRepoName.text = data.repositoryName
            binding.tvUserName.text = data.name
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(userList[position].dataType){
            header -> {
                holder as DescriptionViewHolder
                holder.onBind(userList[position])
            }
            else -> {
                holder as GithubUsersViewHolder
                holder.onBind(userList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setUserList(userList: List<UserRepo>){
        this.userList = userList.toList() // toList 해주는 이유 > 얕은 복사를 위해
        notifyItemRangeInserted(0, this.userList.size)
    }

    companion object{
        const val header = 1
        const val content = 2
    }
}