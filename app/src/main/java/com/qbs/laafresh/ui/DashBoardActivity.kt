package com.qbs.laafresh.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.qbs.laafresh.R
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.databinding.ActivityDashboardBinding
import com.qbs.laafresh.ui.extension.hide
import com.qbs.laafresh.ui.extension.show
import com.qbs.laafresh.ui.extension.showDialAlert

class DashBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    lateinit var navController: NavController
    private var menuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)



        navController = Navigation.findNavController(this, R.id.dashboardNavHost)
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.selectedItemId = R.id.homeFragment

        binding.bottomNav.setOnNavigationItemSelectedListener {
            menuItem = it
            when (it.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                }

                R.id.favoriteFragment -> {
                    if (!PreferenceManager(this).getIsLoggedIn()) {
                        navController.navigate(R.id.loginFragment)
                    } else {
                        navController.navigate(R.id.favoriteFragment)
                    }
                }

                R.id.myOrdersFragment -> {
                    if (!PreferenceManager(this).getIsLoggedIn()) {
                        navController.navigate(R.id.loginFragment)
                    } else {
                        navController.navigate(R.id.myOrdersFragment)
                    }
                }

                R.id.settingsFragment -> {
                    if (!PreferenceManager(this).getIsLoggedIn()) {
                        navController.navigate(R.id.loginFragment)
                    } else {
                        navController.navigate(R.id.settingsFragment)
                    }
                }

                R.id.myCartFragment -> {
                    navController.navigate(R.id.myCartFragment)
                }

            }
            return@setOnNavigationItemSelectedListener false
        }
        visibilityNavElements(navController)
    }


    fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> binding.bottomNav.show()
                R.id.favoriteFragment -> binding.bottomNav.show()
                R.id.myOrdersFragment -> binding.bottomNav.show()
                R.id.settingsFragment -> binding.bottomNav.show()
                // R.id.myCartFragment -> bottomNav.show()
                else -> binding.bottomNav.hide()
            }
        }
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.homeFragment -> {
                this.showDialAlert(title = getString(R.string.label_exit), result = {
                    if (it) {
                        finish()
                    }
                })
            }

            else -> {
                navController.navigateUp()
            }
        }
    }

    fun setCartCount(badgeCount: Int) {
        if (::binding.isInitialized) {
            val badge: BadgeDrawable = binding.bottomNav.getOrCreateBadge(R.id.myCartFragment)
            if (badgeCount == 0) {
                badge.isVisible = false
            } else {
                badge.isVisible = true
                badge.number = badgeCount
            }
        }
    }
}