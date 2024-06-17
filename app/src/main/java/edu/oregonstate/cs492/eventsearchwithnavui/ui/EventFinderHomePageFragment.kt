package edu.oregonstate.cs492.eventsearchwithnavui.ui

import android.Manifest
import android.app.Activity
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationRequest
import android.os.Bundle
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.text.set
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.progressindicator.CircularProgressIndicator
import edu.oregonstate.cs492.eventsearchwithnavui.data.EventFinderRepo
import edu.oregonstate.cs492.eventsearchwithnavui.util.LoadingStatus
import edu.oregonstate.cs492.eventsearchwithnavui.util.buildGitHubQuery
import edu.oregonstate.cs492.eventsearchwithnavui.R
import edu.oregonstate.cs492.eventsearchwithnavui.data.EventFinderListJson
import java.lang.Error
import java.util.Locale
import androidx.navigation.findNavController



/*
    MY TASK: Get the UI for the first FIGMA screen working
 */
class EventFinderHomePageFragment : Fragment(R.layout.fragment_event_home_page) {
    private val viewModel: EvenFinderSearchViewModel by viewModels()
    private val adapter = EventFinderRepoListAdapter(::onGitHubRepoClick)

    private lateinit var prefs: SharedPreferences


    //Text Views
    private lateinit var categories: TextView
    private lateinit var musicLabel: TextView
    private lateinit var comedyLabel: TextView
    private lateinit var sportsLabel: TextView
    private lateinit var familyLabel: TextView
    private lateinit var dramaLabel: TextView
    private lateinit var miscellaneousLabel: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    //Image Views
    private lateinit var musicImage: ImageView
    private lateinit var comedyImage: ImageView
    private lateinit var sportsImage: ImageView
    private lateinit var familyImage: ImageView
    private lateinit var dramaImage: ImageView
    private lateinit var miscellaneousImage: ImageView
    private lateinit var searchBoxET: EditText
    private lateinit var findLocationBtn: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBoxET = view.findViewById(R.id.et_search_box)
        findLocationBtn = view.findViewById(R.id.btn_grab_location)

        //Text Views
        loadingIndicator = view.findViewById(R.id.loading_indicator)
        categories = view.findViewById(R.id.categories)
        musicLabel = view.findViewById(R.id.tv_music)
        comedyLabel = view.findViewById(R.id.tv_comedy)
        sportsLabel = view.findViewById(R.id.tv_sports)
        familyLabel = view.findViewById(R.id.tv_family)
        dramaLabel = view.findViewById(R.id.tv_drama)
        miscellaneousLabel = view.findViewById(R.id.tv_miscellaneous)

        //Image Views
        musicImage = view.findViewById(R.id.iv_music_icon)
        comedyImage = view.findViewById(R.id.iv_comedy_icon)
        sportsImage = view.findViewById(R.id.iv_sports_icon)
        familyImage = view.findViewById(R.id.iv_family_icon)
        dramaImage = view.findViewById(R.id.iv_drama_icon)
        miscellaneousImage = view.findViewById(R.id.iv_miscellaneous_icon)

        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())


        viewModel.searchResults.observe(viewLifecycleOwner) {
                searchResults -> adapter.updateRepoList(searchResults)
        }

        viewModel.loadingStatus.observe(viewLifecycleOwner) {
                loadingStatus ->
            when (loadingStatus) {
                LoadingStatus.LOADING -> {
//                    searchResultsListRV.visibility = View.INVISIBLE
                    loadingIndicator.visibility = View.VISIBLE
//                    searchErrorTV.visibility = View.INVISIBLE
                }
                LoadingStatus.ERROR -> {
//                    searchResultsListRV.visibility = View.INVISIBLE
                    loadingIndicator.visibility = View.INVISIBLE
//                    searchErrorTV.visibility = View.VISIBLE
                }
                else -> {
                    loadingIndicator.visibility = View.INVISIBLE
                }
            }
        }


        musicImage.setOnClickListener{

            val filterBundle = Bundle().apply {
                putString("postalCode", searchBoxET.text.toString())
                putString("classificationName", "Music")
            }
            findNavController().navigate(R.id.github_search, filterBundle)
        }
        musicLabel.setOnClickListener{

            val filterBundle = Bundle().apply {
                putString("postalCode", searchBoxET.text.toString())
                putString("classificationName", "Music")
            }
            findNavController().navigate(R.id.github_search, filterBundle)
        }

        comedyImage.setOnClickListener{

            val filterBundle = Bundle().apply {
                putString("postalCode", searchBoxET.text.toString())
                putString("classificationName", "Comedy")
            }
            findNavController().navigate(R.id.github_search, filterBundle)
        }

        comedyLabel.setOnClickListener{

            val filterBundle = Bundle().apply {
                putString("postalCode", searchBoxET.text.toString())
                putString("classificationName", "Comedy")
            }
            findNavController().navigate(R.id.github_search, filterBundle)
        }
        sportsImage.setOnClickListener{

            val filterBundle = Bundle().apply {
                putString("postalCode", searchBoxET.text.toString())
                putString("classificationName", "Sports")
            }
            findNavController().navigate(R.id.github_search, filterBundle)
        }

        sportsLabel.setOnClickListener{

            val filterBundle = Bundle().apply {
                putString("postalCode", searchBoxET.text.toString())
                putString("classificationName", "Sports")
            }
            findNavController().navigate(R.id.github_search, filterBundle)
        }


        dramaImage.setOnClickListener{

            val filterBundle = Bundle().apply {
                putString("postalCode", searchBoxET.text.toString())
                putString("classificationName", "Arts & Theatre")
            }
            findNavController().navigate(R.id.github_search, filterBundle)
        }

        dramaLabel.setOnClickListener{

            val filterBundle = Bundle().apply {
                putString("postalCode", searchBoxET.text.toString())
                putString("classificationName", "Arts & Theatre")
            }
            findNavController().navigate(R.id.github_search, filterBundle)
        }

        familyImage.setOnClickListener{

            val filterBundle = Bundle().apply {
                putString("postalCode", searchBoxET.text.toString())
                putString("classificationName", "Arts & Theatre")
            }
            findNavController().navigate(R.id.github_search, filterBundle)
        }

        familyLabel.setOnClickListener{

            val filterBundle = Bundle().apply {
                putString("postalCode", searchBoxET.text.toString())
                putString("classificationName", "Arts & Theatre")
            }
            findNavController().navigate(R.id.github_search, filterBundle)
        }

        miscellaneousImage.setOnClickListener{

            val filterBundle = Bundle().apply {
                putString("postalCode", searchBoxET.text.toString())
                putString("classificationName", "Miscellaneous")
            }
            findNavController().navigate(R.id.github_search, filterBundle)
        }

        miscellaneousLabel.setOnClickListener{

            val filterBundle = Bundle().apply {
                putString("postalCode", searchBoxET.text.toString())
                putString("classificationName", "Miscellaneous")
            }
            findNavController().navigate(R.id.github_search, filterBundle)
        }

//        viewModel.error.observe(viewLifecycleOwner) {
//                error -> searchErrorTV.text = getString(
//            R.string.search_error,
//            error
//        )
//        }

        /*
         * This MenuProvider powers the top app bar actions for this screen.
         */
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.github_search_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_bookmarks -> {
                            val directions = EventFinderSearchFragmentDirections.navigateToBookmarkedRepos()
                            findNavController().navigate(directions)
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.STARTED
        )
        findLocationBtn.setOnClickListener{
            fetchAndDisplayWeatherForCurrentLocation()
        }
//        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
//        searchBtn.setOnClickListener {
//            val query = searchBoxET.text.toString()
//            if (!TextUtils.isEmpty(query)) {
//                val sort = prefs.getString(getString(R.string.pref_sort_key), null)
//                val user = prefs.getString(getString(R.string.pref_user_key), null)
//                val fistIssues = prefs.getInt(getString(R.string.pref_first_issues_key), 0)
//                val languages = prefs.getStringSet(getString(R.string.pref_language_key), null)
//                viewModel.loadSearchResults(
//                    buildGitHubQuery(query, user, languages, fistIssues),
//                    sort
//                )
//                searchResultsListRV.scrollToPosition(0)
//            }
//        }


        //!! FINAL PROJECT: Here handle grabbing devices current location !!
//      locationBtn.setOnClickListener{
//      }
    }

    private fun fetchAndDisplayWeatherForCurrentLocation() {
        loadingIndicator.visibility = View.VISIBLE

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireContext() as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_REQUEST_CODE)
            return
        }

        val locationRequest = com.google.android.gms.location.LocationRequest.create()?.apply {
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 5000
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                p0 ?: return
                for (location in p0.locations) {
                    Log.d("location", "$location")
                    if (location != null) {
                        Log.d("Lat" , "${location.latitude}")
                        Log.d("Long" , "${location.longitude}")
                        fetchPostalCode(location.latitude,location.longitude)
                        fusedLocationClient.removeLocationUpdates(this)
                        return
                    }
                }
            }
        }

        if (locationRequest != null) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
    }

    override fun onResume() {
        super.onResume()
        var zipCode = prefs.getString(getString(R.string.pref_zip_key), "97301")
        searchBoxET.setText(zipCode.toString())
    }
    private fun fetchPostalCode(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            Log.d("address", "$addresses")
            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                val postalCode = address.postalCode
                searchBoxET.setText(postalCode)
                loadingIndicator.visibility = View.INVISIBLE

                Log.d("Postal Code", "Postal Code: $postalCode")
                // Use the postal code as needed
            }
        } catch (e: Error) {
            Log.e("Geocoder Error", "Unable to get postal code", e)
        }
    }
    private fun onGitHubRepoClick(repo: EventFinderListJson) {
        val directions = EventFinderSearchFragmentDirections.navigateToRepoDetail(repo)
        findNavController().navigate(directions)
    }
    companion object {
        private const val LOCATION_REQUEST_CODE = 1000
    }
}