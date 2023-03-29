package cz.vvoleman.phr.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import cz.vvoleman.phr.R
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.gu.toolargetool.TooLargeTool;

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var navManager: NavManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TooLargeTool.startLogging(this.application,)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.listMedicalRecordsFragment,
                R.id.overviewFragment,
                R.id.medicineFragment,
                R.id.measurementsFragment
            ),
            binding.drawerLayout
        )

        initNavManager()
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initNavManager() {
        navManager.setOnNavEvent { directions ->
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
            currentFragment?.let {
                try {
                    navController.navigate(directions)
                } catch (e: java.lang.IllegalArgumentException) {
                    Log.e("MainActivity", "Error navigating to ${directions.actionId}", e)
                }
            }
        }
    }

}