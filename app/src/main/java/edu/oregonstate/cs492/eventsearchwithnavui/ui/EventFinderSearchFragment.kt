package edu.oregonstate.cs492.eventsearchwithnavui.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import edu.oregonstate.cs492.eventsearchwithnavui.R
import edu.oregonstate.cs492.eventsearchwithnavui.data.EventFinderListJson
import edu.oregonstate.cs492.eventsearchwithnavui.util.LoadingStatus

class EventFinderSearchFragment : Fragment(R.layout.fragment_github_search) {
    private val viewModel: EvenFinderSearchViewModel by viewModels()
    private val adapter = EventFinderRepoListAdapter(::onGitHubRepoClick)

    private lateinit var searchResultsListRV: RecyclerView
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator
    private var postalCode: String? = null
    private var classificationName: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // val searchBoxET: EditText = view.findViewById(R.id.et_search_box)
        // val searchBtn: Button = view.findViewById(R.id.btn_search)

        searchErrorTV = view.findViewById(R.id.tv_search_error)
        loadingIndicator = view.findViewById(R.id.loading_indicator)

        searchResultsListRV = view.findViewById(R.id.rv_search_results)
        searchResultsListRV.layoutManager = LinearLayoutManager(requireContext())
        searchResultsListRV.setHasFixedSize(true)

        searchResultsListRV.adapter = adapter

        viewModel.searchResults.observe(viewLifecycleOwner) {
                searchResults -> adapter.updateRepoList(searchResults)
        }

        viewModel.loadingStatus.observe(viewLifecycleOwner) {
                loadingStatus ->
            when (loadingStatus) {
                LoadingStatus.LOADING -> {
                    searchResultsListRV.visibility = View.INVISIBLE
                    loadingIndicator.visibility = View.VISIBLE
                    searchErrorTV.visibility = View.INVISIBLE
                }
                LoadingStatus.ERROR -> {
                    searchResultsListRV.visibility = View.INVISIBLE
                    loadingIndicator.visibility = View.INVISIBLE
                    searchErrorTV.visibility = View.VISIBLE
                }
                else -> {
                    searchResultsListRV.visibility = View.VISIBLE
                    loadingIndicator.visibility = View.INVISIBLE
                    searchErrorTV.visibility = View.INVISIBLE
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
                error -> searchErrorTV.text = getString(
            R.string.search_error,
            "No Events Found."
        )
        }

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


        /*val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        searchBtn.setOnClickListener {
            val query = searchBoxET.text.toString()
            if (!TextUtils.isEmpty(query)) {
                val sort = "DU8u0wIzrAqF1q0sEPV0oQGmopbNqTSU"
                val user = prefs.getString(getString(R.string.pref_user_key), null)
                val fistIssues = prefs.getInt(getString(R.string.pref_first_issues_key), 0)
                val languages = prefs.getStringSet(getString(R.string.pref_language_key), null)
                viewModel.loadSearchResults(
                  query,
                    postalCode.toString(),
                    classificationName.toString()
                )
                searchResultsListRV.scrollToPosition(0)
            }
        }*/
    }


    private fun onGitHubRepoClick(repo: EventFinderListJson) {
        val directions = EventFinderSearchFragmentDirections.navigateToRepoDetail(repo)
        findNavController().navigate(directions)
    }


    override fun onResume() {
        super.onResume()
        arguments?.let { bundle ->
            postalCode = bundle.getString("postalCode")
            classificationName = bundle.getString("classificationName")
        }
        Log.d("PostalCode", "$postalCode")
        Log.d("classificationName","$classificationName")
        fetchEventData(postalCode!!,classificationName!!)
    }

    private fun fetchEventData(postalCode: String, classificationName: String) {
        viewModel.loadeventSearchResults(postalCode,classificationName)
        searchResultsListRV.scrollToPosition(0)
    }
}