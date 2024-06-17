package edu.oregonstate.cs492.eventsearchwithnavui.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import edu.oregonstate.cs492.eventsearchwithnavui.R

class MainActivityWithNavDrawer : AppCompatActivity() {
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_with_nav_drawer)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        val navController = navHostFragment.navController
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        appBarConfig = AppBarConfiguration(navController.graph, drawerLayout)

        val appBar: MaterialToolbar = findViewById(R.id.top_app_bar)
        setSupportActionBar(appBar)
        setupActionBarWithNavController(navController, appBarConfig)

        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)

//        addEntriesToNavDrawer()

        /*
         * This MenuProvider powers the top app bar actions for all screens under this activity.
         */
//        addMenuProvider(
//            object : MenuProvider {
//                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                    menuInflater.inflate(R.menu.activity_main_menu, menu)
//                }
//
//                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                    return when (menuItem.itemId) {
//                        R.id.action_settings -> {
//                            findNavController(R.id.nav_host_fragment).navigate(R.id.navigate_to_settings)
//                            true
//                        }
//                        else -> false
//                    }
//                }
//
//            },
//            this,
//            Lifecycle.State.STARTED
//        )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

//    private fun addEntriesToNavDrawer() {
//        val entries = listOf(
//            "Entry #1",
//            "Entry #2",
//            "Entry #3",
//            "Entry #4",
//            "Entry #5"
//        )
//        val navView: NavigationView = findViewById(R.id.nav_view)
//        val subMenu = navView.menu.findItem(R.id.programmatically_inserted).subMenu
//        subMenu?.clear()
//        for (entry in entries) {
//            subMenu?.add(entry)?.setOnMenuItemClickListener {
//                Snackbar.make(
//                    navView,
//                    "This entry was clicked: ${entry}",
//                    Snackbar.LENGTH_LONG
//                ).show()
//                true
//            }
//        }
//    }
}