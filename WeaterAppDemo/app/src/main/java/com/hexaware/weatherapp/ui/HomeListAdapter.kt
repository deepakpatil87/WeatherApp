package com.hexaware.weatherapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.smslist.room.LocationRoomDatabase
import com.hexaware.weatherapp.R
import com.hexaware.weatherapp.databinding.LocationRowItemBinding
import com.hexaware.weatherapp.model.LocationTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class HomeListAdapter(dataModelList: List<LocationTable>, ctx: Context) :
    RecyclerView.Adapter<HomeListAdapter.ViewHolder?>()//, CustomClickListener
{
    private val dataModelList: List<LocationTable> = dataModelList
    private val context: Context = ctx
    private lateinit var binding: LocationRowItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.location_row_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel: LocationTable = dataModelList[position]
        holder.itemRowBinding.setModel(dataModel)
        holder.bind()


        holder.itemRowBinding.cardView.setOnClickListener { v ->
            val bundle = Bundle()

            bundle.putDouble("LAT", dataModel.latitude)
            bundle.putDouble("LON", dataModel.longitude)
            v?.findNavController()?.navigate(R.id.cityWeatherDetail, bundle)
        }
        holder.itemRowBinding.imgDelete.setOnClickListener(View.OnClickListener {
            val mDb = LocationRoomDatabase.getDatabase(context, CoroutineScope(SupervisorJob()))
            CoroutineScope(Dispatchers.IO).launch {
                mDb.locationDao().deleteRow(dataModel.latitude)
            }

        })
    }


    inner class ViewHolder(itemRowBinding: LocationRowItemBinding) :
        RecyclerView.ViewHolder(itemRowBinding.getRoot()) {
        var itemRowBinding: LocationRowItemBinding = itemRowBinding
        fun bind() {
            // itemRowBinding.setVariable(BR.model, obj)
            itemRowBinding.executePendingBindings()
        }

    }

    override fun getItemCount(): Int {
        return dataModelList.size
    }
}