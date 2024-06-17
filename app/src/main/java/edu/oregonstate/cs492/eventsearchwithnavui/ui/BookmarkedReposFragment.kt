package edu.oregonstate.cs492.eventsearchwithnavui.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.oregonstate.cs492.eventsearchwithnavui.data.EventFinderRepo
import edu.oregonstate.cs492.eventsearchwithnavui.R
import edu.oregonstate.cs492.eventsearchwithnavui.data.EventFinderListJson

class BookmarkedReposFragment : Fragment(R.layout.fragment_bookmarked_repos) {
    private val adapter = EventFinderRepoListAdapter(::onGitHubRepoClick)
    private val viewModel: BookmarkedReposViewModel by viewModels()

    private lateinit var bookmarkedReposRV: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarkedReposRV = view.findViewById(R.id.rv_bookmarked_repos)
        bookmarkedReposRV.layoutManager = LinearLayoutManager(requireContext())
        bookmarkedReposRV.setHasFixedSize(true)
        bookmarkedReposRV.adapter = adapter

        viewModel.bookmarkedRepos.observe(viewLifecycleOwner) { bookmarkedRepos ->
            adapter.updateRepoList(bookmarkedRepos)
        }
    }

    private fun onGitHubRepoClick(repo: EventFinderListJson) {
        val directions = EventFinderSearchFragmentDirections.navigateToRepoDetail(repo)
        findNavController().navigate(directions)
    }
}