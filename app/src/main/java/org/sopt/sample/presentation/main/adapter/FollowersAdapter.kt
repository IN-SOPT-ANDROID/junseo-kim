package org.sopt.sample.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sopt.sample.data.remote.ResponseGetUsersDTO
import org.sopt.sample.databinding.ItemUsersBinding

class FollowersAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var userList: List<ResponseGetUsersDTO.User> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemUsersBinding.inflate(inflater, parent, false)
        return UsersViewHolder(binding)
    }

    class UsersViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseGetUsersDTO.User) {
            val name = data.firstName + data.lastName
            binding.tvName.text = name
            binding.tvEmail.text = data.email
            Glide.with(binding.root).load(data.avatar).into(binding.ivAvatar)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as UsersViewHolder
        holder.onBind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setUserList(userList: List<ResponseGetUsersDTO.User>) {
        this.userList = userList.toList()// toList 해주는 이유 > 얕은 복사를 위해
        notifyItemRangeInserted(0, this.userList.size)
    }
}