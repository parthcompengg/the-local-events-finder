package edu.oregonstate.cs492.eventsearchwithnavui.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import edu.oregonstate.cs492.eventsearchwithnavui.R

class EventFinderRepoDetailFragment : Fragment(R.layout.fragment_github_repo_detail) {
    private val args: EventFinderRepoDetailFragmentArgs by navArgs()
    private val viewModel: BookmarkedReposViewModel by viewModels()
    private var isBookmarked = false

    private lateinit var bookmarkMenuItem: MenuItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleTV: TextView = view.findViewById(R.id.tv_event_title)
        val locationTV: TextView = view.findViewById(R.id.tv_event_location)
        val dateTV: TextView = view.findViewById(R.id.tv_event_date)
        val eventIconIV: ImageView = view.findViewById(R.id.iv_event_icon)
        val cityStateTV: TextView = view.findViewById(R.id.tv_event_city_state)
        val venueNameTV: TextView = view.findViewById(R.id.tv_event_venue_name)
        //val urlButton: Button = view.findViewById(R.id.tv_event_url)
        val urlButton: Button = view.findViewById(R.id.action_open_in_browser)
        urlButton.setText(R.string.action_open_in_browser)


        titleTV.text = args.repo.name
        locationTV.text = args.repo.embedded.venues[0].address.line1
//        cityStateTV.text= "${args.repo.embedded.venues[0].city.name}, ${args.repo.embedded.venues[0].state.name}"
        cityStateTV.text = getString(R.string.venue_location, args.repo.embedded.venues[0].city.name, args.repo.embedded.venues[0].state.name)
        dateTV.text = args.repo.dates.start.localDate
        venueNameTV.text = args.repo.embedded.venues[0].name
        // urlTV.text = args.repo.url

        Glide.with(this)
            .load(args.repo.images[4].url)
            .into(eventIconIV)

        urlButton.setOnClickListener{
            viewOnGitHub()
        }



        /*
         * This observer monitors the database to determine whether the current repo is
         * bookmarked and updates the UI accordingly.
         */
        viewModel.getBookmarkedRepoByName(args.repo.name).observe(viewLifecycleOwner) { repo ->
            when(repo) {
                null -> {
                    isBookmarked = false
                    bookmarkMenuItem.icon = AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_action_bookmark_off
                    )
                }
                else -> {
                    isBookmarked = true
                    bookmarkMenuItem.icon = AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_action_bookmark_on
                    )
                }
            }
            bookmarkMenuItem.isChecked = isBookmarked
        }

        val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            val galleryUri = it
            try {
                if (galleryUri != null) {
                    share(galleryUri)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }



        /*
         * This MenuProvider powers the top app bar actions for this fragment.
         */
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.github_repo_detail_menu, menu)
                    bookmarkMenuItem = menu.findItem(R.id.action_bookmark)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_open_in_browser -> {
                            viewOnGitHub()
                            true
                        }
                        R.id.action_share -> {
                            galleryLauncher.launch("image/*")
                            true
                        }
                        R.id.action_bookmark -> {
                            toggleRepoBookmark()
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.STARTED
        )
    }

    /**
     * This method toggles the state of the bookmark icon in the top app bar whenever the user
     * clicks it.
     */
    private fun toggleRepoBookmark() {
        when (!isBookmarked) {
            true -> {
                viewModel.addBookmarkedRepo(args.repo)
            }
            false -> {
                viewModel.removeBookmarkedRepo(args.repo)
            }
        }
    }

    private fun viewOnGitHub() {
        val url = Uri.parse(args.repo.url)
        val intent = Intent(Intent.ACTION_VIEW, url)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Snackbar.make(
                requireView(),
                R.string.open_in_browser_error,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun share(uri: Uri) {
        val shareText = getString(
            R.string.share_text,
            args.repo.name,
            args.repo.url
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("image/jpg")
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
        startActivity(Intent.createChooser(shareIntent, "Share image using"))
    }
}