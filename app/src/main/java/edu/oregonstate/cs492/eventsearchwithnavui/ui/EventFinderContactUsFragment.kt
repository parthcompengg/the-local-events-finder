package edu.oregonstate.cs492.eventsearchwithnavui.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.google.android.material.progressindicator.CircularProgressIndicator
import edu.oregonstate.cs492.eventsearchwithnavui.R
import edu.oregonstate.cs492.eventsearchwithnavui.data.EventFinderListJson
import edu.oregonstate.cs492.eventsearchwithnavui.util.LoadingStatus


/*
    MY TASK: Get the UI for the first FIGMA screen working
 */
class EventFinderContactUsFragment : Fragment(R.layout.fragment_contact_us_page) {
    private val viewModel: EvenFinderSearchViewModel by viewModels()
    private val adapter = EventFinderRepoListAdapter(::onGitHubRepoClick)

    private lateinit var phoneNumberTV: TextView
    private lateinit var emailTV: TextView
    private lateinit var websiteTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val searchBoxET: EditText = view.findViewById(R.id.et_search_box)
//        val findLocationBtn: Button = view.findViewById(R.id.btn_grab_location)

        loadingIndicator = view.findViewById(R.id.loading_indicator)
        phoneNumberTV = view.findViewById(R.id.tv_contact_us_phone)
        emailTV = view.findViewById(R.id.tv_contact_us_email)
        websiteTV = view.findViewById(R.id.tv_contact_us_website)

        //onclick listener for phoneNumber text view
        phoneNumberTV.setOnClickListener {
            val phoneNumber = "5417372867"
            if (phoneNumber.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneNumber")
                startActivity(intent)
            }
        }

        //onclick listener for email text view
        emailTV.setOnClickListener {
            val email = "contact@theeventfinder.com"
            if(email.isNotEmpty()){
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:$email")
                startActivity(intent)            }
        }

        //onclick listerner for website text view
        websiteTV.setOnClickListener {
            val website = "https://www.theeventfinder.com"
            if(website.isNotEmpty()){
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(website)
                startActivity(intent)
            }
        }



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


        //Here handle grabbing devices current location
//      locationBtn.setOnClickListener
    }

    private fun onGitHubRepoClick(repo: EventFinderListJson) {
        val directions = EventFinderSearchFragmentDirections.navigateToRepoDetail(repo)
        findNavController().navigate(directions)
    }
}