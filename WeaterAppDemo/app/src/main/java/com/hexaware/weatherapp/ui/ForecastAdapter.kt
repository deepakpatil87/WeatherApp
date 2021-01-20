package com.hexaware.weatherapp.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hexaware.weatherapp.R
import com.hexaware.weatherapp.databinding.ItemForecastBinding
import com.hexaware.weatherapp.model.forecast.ListW


class ForecastAdapter(private val dataModelList: List<ListW>) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder?>() {
    private lateinit var binding: ItemForecastBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_forecast, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel: ListW = dataModelList[position]
        holder.itemRowBinding.model = dataModel
        holder.bind()

    }


    inner class ViewHolder(itemRowBinding: ItemForecastBinding) :
        RecyclerView.ViewHolder(itemRowBinding.getRoot()) {
        var itemRowBinding: ItemForecastBinding = itemRowBinding
        fun bind() {

            itemRowBinding.executePendingBindings()
        }

    }

    override fun getItemCount(): Int {
        return dataModelList.size
    }
}

