package cz.vvoleman.phr.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.gu.toolargetool.TooLargeTool
import cz.vvoleman.phr.R
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var navManager: NavManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TooLargeTool.startLogging(this.application)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.listMedicalRecordsFragment,
                R.id.listMedicineFragment,
                R.id.overviewFragment,
                R.id.measurementsFragment
            ),
            binding.drawerLayout
        )

        initNavManager()
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navView.setupWithNavController(navController)

        setupNavigation()

        val patientsButton = binding.navView.getHeaderView(0).findViewById<Button>(R.id.button_edit_patient)
        patientsButton.setOnClickListener {
            navController.navigate(cz.vvoleman.phr.common_datasource.R.id.nav_common)
            binding.drawerLayout.close()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent != null) {
            val fragmentIdentifier = intent.getIntExtra(FRAGMENT_IDENTIFIER_KEY, -1)

            if (fragmentIdentifier == -1) {
                Log.e("MainActivity", "No fragment identifier found in intent")
                return
            }

            navController.navigate(fragmentIdentifier)
        }
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

    private fun setupNavigation() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.listMedicalRecordsFragment -> {
                    navController.navigate(cz.vvoleman.phr.featureMedicalRecord.R.id.nav_medical_record)
                    binding.drawerLayout.close()
                    true
                }
                R.id.listMedicineFragment -> {
                    navController.navigate(cz.vvoleman.phr.featureMedicine.R.id.nav_medicine)
                    binding.drawerLayout.close()
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        const val FRAGMENT_IDENTIFIER_KEY = "fragmentIdentifier"
    }
}
