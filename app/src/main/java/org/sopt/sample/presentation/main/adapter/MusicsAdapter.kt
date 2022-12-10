package org.sopt.sample.presentation.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.data.remote.model.ResponseGetMusicDto
import org.sopt.sample.databinding.ItemMusicsBinding

class MusicsAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var musicList: List<ResponseGetMusicDto.Music> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMusicsBinding.inflate(inflater, parent, false)
        return MusicViewHolder(binding)
    }

    class MusicViewHolder(private val binding: ItemMusicsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseGetMusicDto.Music) {
            binding.ivMusicProfile.setImageURI(data.image.toUri())
            binding.tvMusicTitle.text = data.title
            binding.tvMusicSinger.text = data.singer
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MusicViewHolder
        holder.onBind(musicList[position])
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    fun setMusicList(musicList: List<ResponseGetMusicDto.Music>) {
        this.musicList = musicList.toList()
        notifyItemRangeInserted(0, this.musicList.size)
    }
}