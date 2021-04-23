package ru.school21.eleonard.mainWindow.ui

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.BaseApp
import ru.school21.eleonard.Constants
import ru.school21.eleonard.R
import ru.school21.eleonard.databinding.ActivityMainBinding
import ru.school21.eleonard.helpers.base.BaseActivity
import ru.school21.eleonard.helpers.utils.hide
import ru.school21.eleonard.helpers.utils.show
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.mainWindow.KeyboardManager
import ru.school21.eleonard.mainWindow.MainActivityViewPagerManager
import ru.school21.eleonard.mainWindow.ToolbarManager
import ru.school21.eleonard.mainWindow.adapters.MainViewPagerStateAdapter
import ru.school21.eleonard.security.helpers.PinState
import ru.school21.eleonard.security.viewModels.SecurityViewModel
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : BaseActivity(), MainActivityViewPagerManager, KeyboardManager {
	private lateinit var binding: ActivityMainBinding
	private lateinit var securityViewModel: SecurityViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		securityViewModel = ViewModelProvider(this).get(SecurityViewModel::class.java)
		configureViews()
		initBnvListener()
	}

	override fun onStop() {
		super.onStop()
		configureActivityByPin()
	}

	override fun openCameras() {
		binding.bnvMain.selectedItemId = R.id.menuItemContacts
	}

	override fun openMap() {
		binding.bnvMain.selectedItemId = R.id.menuItemSchoolInfo
	}

	override fun openMultiplayer() {
		binding.bnvMain.selectedItemId = R.id.menuItemGraphics
	}

	override fun openOther() {
		binding.bnvMain.selectedItemId = R.id.menuItemOther
	}

	override fun configureActivityPin() {
		securityViewModel.currentPinState = PinState.LOGIN
		binding.vpMain.adapter = MainViewPagerStateAdapter(appCompatActivity = this@MainActivity, size = 1, isGuest = true)
		binding.bnvMain.hide()
		binding.toolbar.hide()
	}

	override fun configureActivityMenu() {
		securityViewModel.currentPinState = if (isPinExist()) PinState.DELETE else PinState.CREATE
		binding.vpMain.adapter = MainViewPagerStateAdapter(appCompatActivity = this@MainActivity, size = 4, isGuest = false)
		binding.bnvMain.show()
		binding.toolbar.show()
		openCameras()
	}

	private fun configureViews() {
		setSupportActionBar(binding.toolbar)
		binding.vpMain.isUserInputEnabled = false
		binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
		configureActivityByPin()
	}

	private fun configureActivityByPin() {
		if (isPinExist())
			configureActivityPin()
		else
			configureActivityMenu()
	}

	private fun initBnvListener() {
		binding.bnvMain.setOnNavigationItemSelectedListener { menuItem ->
			when (menuItem.itemId) {
				R.id.menuItemContacts -> binding.vpMain.currentItem = 0
				R.id.menuItemSchoolInfo -> binding.vpMain.currentItem = 1
				R.id.menuItemGraphics -> binding.vpMain.currentItem = 2
				R.id.menuItemOther -> binding.vpMain.currentItem = 3
			}
			binding.toolbar.collapseActionView()
			true
		}
	}

	private fun isPinExist(): Boolean {
		return BaseApp.getSharedPref().getString(Constants.SP_PIN_ENCODED, "")!!.isNotEmpty()
	}

	override fun hideKeyboard() {
		(this@MainActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
			.hideSoftInputFromWindow(binding.root.windowToken, 0)
	}
}