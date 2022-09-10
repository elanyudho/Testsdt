package com.elanyudho.testsdt.ui.secondpart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elanyudho.testsdt.databinding.ItemJokeBinding
import com.elanyudho.testsdt.domain.model.Jokes
import com.elanyudho.testsdt.utils.extension.glide

class JokesAdapter: RecyclerView.Adapter<JokesAdapter.ViewHolder>() {

    private var listData = mutableListOf<Jokes>()

    private lateinit var onClick: (data: Jokes) -> Unit

    fun submitList(newList: List<Jokes>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemJokeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemJokeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(listData[position]){
                binding.tvJoke.text = this.value
                binding.imageView.glide(binding.imageView, "https://api.chucknorris.io/img/chucknorris_logo_coloured_small@2x.png")
                binding.root.setOnClickListener { onClick.invoke(this) }
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun setOnClickData(onClick: (data: Jokes) -> Unit) {
        this.onClick = onClick
    }
}