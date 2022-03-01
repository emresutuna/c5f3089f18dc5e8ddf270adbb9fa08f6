package com.example.spaceexample.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceexample.dao.FavoriteDao
import com.example.spaceexample.databinding.RcFavoriteShipItemBinding
import com.example.spaceexample.models.FavoriteShip

class FavoriteShipAdapter(
    private var favoriteShip: List<FavoriteShip>,
    private val favoriteDao: FavoriteDao
) :
    RecyclerView.Adapter<FavoriteShipAdapter.FavoriteShipViewHolder>() {
    class FavoriteShipViewHolder(val itemBinding: RcFavoriteShipItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteShipViewHolder {
        return FavoriteShipAdapter.FavoriteShipViewHolder(
            RcFavoriteShipItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteShipViewHolder, position: Int) {
        holder.itemBinding.shipName.text = favoriteShip[position].name
        holder.itemBinding.shipEus.text = favoriteShip[position].capacity.toString()
        holder.itemBinding.shipFav.setOnClickListener {
            removeItem(favoriteShip[position], position)
        }
    }

    private fun removeItem(favoriteShip: FavoriteShip, pos: Int) {
        favoriteDao.delete(favoriteShip)
        notifyItemRemoved(pos)
    }

    override fun getItemCount(): Int {
        return favoriteShip.size
    }


}