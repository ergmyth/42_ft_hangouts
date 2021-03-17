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
import ru.school21.eleonard.helpers.hide
import ru.school21.eleonard.helpers.show
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.mainWindow.MainActivityViewPagerManager
import ru.school21.eleonard.mainWindow.ProgressBarManager
import ru.school21.eleonard.mainWindow.ToolbarManager
import ru.school21.eleonard.mainWindow.adapters.MainViewPagerStateAdapter
import ru.school21.eleonard.security.helpers.PinState
import ru.school21.eleonard.security.viewModels.SecurityViewModel
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityViewPagerManager, ToolbarManager, ProgressBarManager {
	private lateinit var binding: ActivityMainBinding
	private lateinit var securityViewModel: SecurityViewModel

	private var backPressed = 0L

	private var ongoingRequestsCounter = 0

	override fun startLoading(text: String?) {
		ongoingRequestsCounter++
		binding.llLoading.show()
		binding.tvLoadingText.text = if (ongoingRequestsCounter > 1)
			resources.getString(R.string.loading_message_receiving) + " ($ongoingRequestsCounter)"
		else
			(text ?: resources.getString(R.string.loading_message_receiving))
	}

	override fun finishLoading() {
		ongoingRequestsCounter--
		when (ongoingRequestsCounter) {
			0 -> binding.llLoading.hide()
			1 -> binding.tvLoadingText.text = resources.getString(R.string.loading_message_receiving)
			else -> binding.tvLoadingText.text = "${binding.tvLoadingText.text} ($ongoingRequestsCounter)"
		}
	}

	override fun configureToolbar(title: String, toolbarState: ToolbarStates) {
		supportActionBar?.title = title
		supportActionBar?.setDisplayHomeAsUpEnabled(toolbarState.isBackBtnVisible)
		supportActionBar?.setHomeButtonEnabled(toolbarState.isBackBtnVisible)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			android.R.id.home -> onBackPressed()
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onBackPressed() {
		hideKeyboard()
		val countFragment = supportFragmentManager.backStackEntryCount
		var countChild = 0
		for (str in supportFragmentManager.fragments) {
			if (str.childFragmentManager.backStackEntryCount > 0) countChild++
		}
		if (countChild > 0) {
			for (fragment in supportFragmentManager.fragments) {
				if (fragment.childFragmentManager.backStackEntryCount >= 0)
					fragment.childFragmentManager.popBackStack()
			}
		} else if (countFragment > 0) {
			supportFragmentManager.popBackStack()
		} else {
			if (backPressed + 1000 > System.currentTimeMillis()) {
				super.onBackPressed()
				finish()
				finishAffinity()
				exitProcess(0)
			} else {
				Toast.makeText(this, resources.getString(R.string.main_back_pressed), Toast.LENGTH_SHORT).show()
				backPressed = System.currentTimeMillis()
			}
		}
	}

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

	override fun configureActivityForNonAuthorizedUser() {
		//todo Решить проблему с адаптером при переключении на тёмную тему.
		securityViewModel.currentPinState = PinState.LOGIN
		binding.vpMain.adapter = MainViewPagerStateAdapter(appCompatActivity = this@MainActivity, size = 1, isGuest = true)
		binding.bnvMain.hide()
		binding.cvToolbar.hide()
	}

	override fun configureActivityForAuthorizedUser() {
		securityViewModel.currentPinState = if (isPinExist()) PinState.DELETE else PinState.CREATE
		binding.vpMain.adapter = MainViewPagerStateAdapter(appCompatActivity = this@MainActivity, size = 4, isGuest = false)
		binding.bnvMain.show()
		binding.cvToolbar.show()
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
			configureActivityForNonAuthorizedUser()
		else
			configureActivityForAuthorizedUser()
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

	private fun hideKeyboard() {
		(this@MainActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
			.hideSoftInputFromWindow(binding.root.windowToken, 0)
	}
}