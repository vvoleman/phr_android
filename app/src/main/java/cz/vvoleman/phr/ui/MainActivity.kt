package cz.vvoleman.phr.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import cz.vvoleman.phr.R
import cz.vvoleman.phr.data.AdapterPair
import cz.vvoleman.phr.data.core.Patient
import cz.vvoleman.phr.databinding.ActivityMainBinding
import cz.vvoleman.phr.ui.main.MainViewModel
import cz.vvoleman.phr.ui.shared.PatientSharedViewModel
import cz.vvoleman.phr.ui.views.dialog_spinner.DialogSpinner
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DialogSpinner.DialogSpinnerListener {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var patientSpinner: DialogSpinner<Patient>

    private val viewModel: MainViewModel by viewModels()
    private val patientSharedViewModel: PatientSharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.overviewFragment,
                R.id.medicalRecordsFragment,
                R.id.medicineFragment,
                R.id.measurementsFragment
            ),
            binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navView.setupWithNavController(navController)

        patientSpinner = binding.navView.getHeaderView(0).findViewById(R.id.spinner_patient)

        viewModel.allPatients.observe(this) { patients ->
            Log.d("MainActivity", "Patients: $patients")
            val pairs = patients.map { it.getAdapterPair() }
            Log.d("MainActivity", "Pairs: $pairs")

            // Launch scope
            lifecycleScope.launch {
                patientSpinner.setData(pairs)
            }
        }

        patientSharedViewModel.selectedPatient.asLiveData().observe(this) { patient ->
            patientSpinner.setSelectedItem(patient.getAdapterPair())
        }

        patientSpinner.setListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onItemSelected(item: AdapterPair): Boolean {
        val patient = item.objectValue as Patient
        patient.id?.let {
            patientSharedViewModel.updatePatient(it)
        }

        return true
    }

    override fun onSearch(query: String) {
        viewModel.searchQuery.value = query
    }

}

const val ADD_OK = Activity.RESULT_FIRST_USER
const val EDIT_OK = Activity.RESULT_FIRST_USER + 1