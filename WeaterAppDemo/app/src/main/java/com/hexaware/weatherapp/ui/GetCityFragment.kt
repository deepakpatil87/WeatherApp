package com.hexaware.weatherapp.ui

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.smslist.room.LocationRoomDatabase
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hexaware.weatherapp.R
import com.hexaware.weatherapp.model.LocationTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*


class GetCityFragment : Fragment(),
    OnMapReadyCallback {

    private val india = LatLng(20.5937, 78.9629)
    private val ZOOM_LEVEL = 6f
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
        //return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.activity_addlocation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title =
            "Add Location"// set drawable icon

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)


    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just move the camera to Sydney and add a marker in Sydney.
     */
    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return


        with(googleMap) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(india, ZOOM_LEVEL))
           // addMarker(MarkerOptions().position(india))

            googleMap.setOnMapClickListener(OnMapClickListener { latLng ->
                try {

                    val geo = Geocoder(activity, Locale.getDefault())
                    val address: List<Address> =
                        geo.getFromLocation(latLng.latitude, latLng.longitude, 1)
                    if (address.isNotEmpty()) {
                        googleMap.addMarker(
                            MarkerOptions().position(latLng).title(
                                "Name:" + address[0].getCountryName()
                                    .toString() + ". Address:" + address[0].getAddressLine(0)
                            )
                        )


                    }

                    val mDb = LocationRoomDatabase.getDatabase(
                        mContext,
                        CoroutineScope(SupervisorJob())
                    )
                    val localityStr= if(!address[0].locality.isNullOrEmpty()) address[0].locality else ""
                    val locationTable = LocationTable(
                        localityStr, address[0].adminArea, address[0].getCountryName(),
                        address[0].getAddressLine(0), latLng.latitude, latLng.longitude
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        mDb.locationDao().insert(locationTable)

                    }
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            })

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {

                false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}