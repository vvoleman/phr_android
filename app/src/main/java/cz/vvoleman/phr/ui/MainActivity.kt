package cz.vvoleman.phr.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.gu.toolargetool.TooLargeTool
import cz.vvoleman.phr.R
import cz.vvoleman.phr.base.domain.ModuleListener
import cz.vvoleman.phr.base.presentation.navigation.NavManager
import cz.vvoleman.phr.common.data.datasource.model.PatientDataStore
import cz.vvoleman.phr.common.presentation.eventBus.CommonListener
import cz.vvoleman.phr.databinding.ActivityMainBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.subscriber.MedicalRecordListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var navManager: NavManager

    @Inject
    lateinit var commonListener: CommonListener

    @Inject
    lateinit var medicalRecordListener: MedicalRecordListener

    @Inject
    lateinit var patientDataStore: PatientDataStore

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
                R.id.listMeasurementFragment,
                R.id.listEventFragment,
                R.id.listHealthcareFragment,
                R.id.listProblemCategoryFragment,
            ),
            binding.drawerLayout
        )

        initNavManager()
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navView.setupWithNavController(navController)

        setupNavigation()

        val patientsButton = binding.navView.getHeaderView(0).findViewById<Button>(R.id.button_edit_patient)
        patientsButton.setOnClickListener {
            navController.navigate(cz.vvoleman.phr.common_datasource.R.id.nav_patient)
            binding.drawerLayout.close()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getListeners().forEach {
                    it.setController(navController)
                    it.onInit()
                }
            }

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                patientDataStore.preferencesFlow.collect { preferences ->
                    Log.d("MainActivity", "Patient id: ${preferences.patientId}")
                }
            }
        }


    }

    private fun getListeners(): List<ModuleListener> {
        return listOf(commonListener, medicalRecordListener)
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

                R.id.listMeasurementFragment -> {
                    navController.navigate(cz.vvoleman.phr.featureMeasurement.R.id.nav_measurement)
                    binding.drawerLayout.close()
                    true
                }

                R.id.listEventFragment -> {
                    navController.navigate(cz.vvoleman.phr.featureEvent.R.id.nav_event)
                    binding.drawerLayout.close()
                    true
                }

                R.id.listHealthcareFragment -> {
                    navController.navigate(cz.vvoleman.phr.common_datasource.R.id.nav_healthcare)
                    binding.drawerLayout.close()
                    true
                }

                R.id.listProblemCategoryFragment -> {
                    navController.navigate(cz.vvoleman.phr.common_datasource.R.id.nav_problem_category)
                    binding.drawerLayout.close()
                    true
                }


                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.DESTROYED) {
                getListeners().forEach { it.onDestroy() }
            }
        }
    }

    companion object {
        const val FRAGMENT_IDENTIFIER_KEY = "fragmentIdentifier"
    }
}
