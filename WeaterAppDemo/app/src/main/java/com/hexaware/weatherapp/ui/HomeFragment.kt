package com.hexaware.weatherapp.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hexaware.weatherapp.R
import com.hexaware.weatherapp.databinding.FragmentHomeBinding

import com.hexaware.weatherapp.model.LocationTable
import com.hexaware.weatherapp.util.Constant
import com.hexaware.weatherapp.viewmodel.LocationViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var locationViewModel: LocationViewModel
    lateinit var locationListAdapter: HomeListAdapter
    private lateinit var locationListBinding: FragmentHomeBinding
    lateinit var locationList: List<LocationTable>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        locationListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        locationListBinding.lifecycleOwner = this

        return locationListBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        locationViewModel =
            ViewModelProvider(
                this@HomeFragment
            ).get(LocationViewModel::class.java)

        val layoutManager = LinearLayoutManager(context)
        // val layoutManager = GridLayoutManager(mContext, 2)

        locationListBinding.deviceListRecycleView.layoutManager =
            layoutManager

        locationListBinding.deviceListRecycleView.hasFixedSize()

        val locationReset = Constant.getBooleanSharePreferences("LOCATION_RESET")
        if (locationReset) {
            CoroutineScope(Dispatchers.IO).launch {
                locationViewModel.resetAllLocation()
            }
        }
        locationViewModel.allLocation().observe(viewLifecycleOwner) { locations ->
            // Update the cached copy of the words in the adapter.
            locations.let {

                locationList = locations
                setAdapter(locationList)


            }
        }

        fab_map.setOnClickListener {

            view?.findNavController()
                ?.navigate(R.id.addlocationMap)
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    var filteredList = locationList
                    if (!TextUtils.isEmpty(s)) {
                        filteredList = filteredList.filter {
                            it.cityName.contains(
                                s.toString(),
                                true
                            )
                        } as ArrayList<LocationTable>
                    }
                    setAdapter(filteredList)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    fun setAdapter(locationList: List<LocationTable>) {
        if (locationList.isNotEmpty()) {
            msglabel.visibility = View.GONE
            deviceListRecycleView.visibility = View.VISIBLE
            locationListAdapter = HomeListAdapter(locationList, mContext)
            locationListBinding.deviceListRecycleView.adapter = locationListAdapter

        } else {
            deviceListRecycleView.visibility = View.GONE
            msglabel.visibility = View.VISIBLE
        }
    }
}