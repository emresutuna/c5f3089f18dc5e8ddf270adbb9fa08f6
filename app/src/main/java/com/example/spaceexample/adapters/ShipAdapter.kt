package com.example.spaceexample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceexample.R
import com.example.spaceexample.dao.FavoriteDao
import com.example.spaceexample.models.FavoriteShip
import com.example.spaceexample.models.Ships


open class ShipAdapter(private var favoriteDao: FavoriteDao) :
    RecyclerView.Adapter<ShipAdapter.DataViewHolder>(), Filterable {

    var photosList: ArrayList<Ships> = ArrayList()
    var shipListFiltered: ArrayList<Ships> = ArrayList()

    var onItemClick: ((Ships) -> Unit)? = null

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(shipListFiltered[adapterPosition])
            }
        }

        private val desc: TextView = itemView.findViewById(R.id.textView)
        private val eusText: TextView = itemView.findViewById(R.id.eusText)
        private val shipName: TextView = itemView.findViewById(R.id.shipName)
        private val favIcon: ImageView = itemView.findViewById(R.id.fav)

        fun bind(result: Ships) {
            shipName.text = result.name
            desc.text = result.stock.toString() + "/ ${result.need}"
            eusText.text = "eus 231"
            favIcon.setOnClickListener {
                addFavorite(result)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.rc_ship_item, parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(shipListFiltered[position])

    }

    override fun getItemCount(): Int = shipListFiltered.size

    fun addData(list: List<Ships>) {
        photosList = list as ArrayList<Ships>
        shipListFiltered = photosList
        notifyDataSetChanged()
    }

    private fun addFavorite(data: Ships) {
        val ship = FavoriteShip(null)
        ship.need = data.need
        ship.capacity = data.capacity
        ship.coordinateX = data.coordinateX
        ship.coordinateY = data.coordinateY
        ship.name = data.name
        ship.stock = data.stock
        favoriteDao.insert(ship)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) shipListFiltered = photosList else {
                    val filteredList = ArrayList<Ships>()
                    photosList
                        .filter {
                            (it.name!!.lowercase().contains(constraint!!)) or
                                    (it.name!!.lowercase().contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    shipListFiltered = filteredList

                }
                return FilterResults().apply { values = shipListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                shipListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Ships>
                notifyDataSetChanged()
            }
        }
    }
}