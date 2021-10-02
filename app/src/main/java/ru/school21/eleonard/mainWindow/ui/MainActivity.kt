package ru.school21.eleonard.mainWindow.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.BaseApp
import ru.school21.eleonard.Constants
import ru.school21.eleonard.R
import ru.school21.eleonard.databinding.ActivityMainBinding
import ru.school21.eleonard.helpers.base.BaseActivity
import ru.school21.eleonard.helpers.utils.*
import ru.school21.eleonard.mainWindow.KeyboardManager
import ru.school21.eleonard.mainWindow.MainActivityViewPagerManager
import ru.school21.eleonard.mainWindow.adapters.MainViewPagerStateAdapter
import ru.school21.eleonard.menu.contacts.viewModels.ContactsViewModel
import ru.school21.eleonard.security.helpers.PinState
import ru.school21.eleonard.security.viewModels.SecurityViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity(), MainActivityViewPagerManager, KeyboardManager {
	private lateinit var binding: ActivityMainBinding
	private lateinit var securityViewModel: SecurityViewModel
	private lateinit var contactsViewModel: ContactsViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		securityViewModel = ViewModelProvider(this).get(SecurityViewModel::class.java)
		contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
		configureViews()
		initBnvListener()
		ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleListener())
	}

	class AppLifecycleListener : LifecycleObserver {

		@OnLifecycleEvent(Lifecycle.Event.ON_START)
		fun onMoveToForeground() {
			val lockedTime = BaseApp.getSharedPref().getLong(Constants.SP_LOCKED_TIME, 0L)
			if (lockedTime != 0L)
				UtilsUI.makeCoolToast(UtilsUI.convertTimestampToDate(lockedTime), ToastStates.NORMAL)
		}

		@OnLifecycleEvent(Lifecycle.Event.ON_STOP)
		fun onMoveToBackground() {
			BaseApp.getSharedPref().edit().putLong(Constants.SP_LOCKED_TIME, System.currentTimeMillis()).apply()
		}
	}

	override fun onStop() {
		super.onStop()
		configureActivityByPin()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		when (requestCode) {
			CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
				processCroppedPhoto(resultCode, data)
			}
		}
	}

	private fun processCroppedPhoto(resultCode: Int, data: Intent?) {
		val result = CropImage.getActivityResult(data)
		if (resultCode == Activity.RESULT_OK) {
			contactsViewModel.curAvatar.value = result.uri.toString()
		} else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
			UtilsUI.makeCoolToast(result.error.message.toString(), ToastStates.ERROR)
		}
	}

	override fun openContacts() {
		binding.bnvMain.selectedItemId = R.id.menuItemContacts
	}

	override fun openSchool() {
		binding.bnvMain.selectedItemId = R.id.menuItemSchoolInfo
	}

	override fun openGraphics() {
		binding.bnvMain.selectedItemId = R.id.menuItemGraphics
	}

	override fun openOther() {
		binding.bnvMain.selectedItemId = R.id.menuItemOther
	}

	override fun openPin() {
		binding.bnvMain.selectedItemId = R.id.menuItemPin
	}

	override fun configureActivityPin() {
		securityViewModel.currentPinState = PinState.LOGIN
		binding.bnvMain.hide()
		binding.toolbar.hide()
	}

	override fun configureActivityMenu() {
		securityViewModel.currentPinState = if (isPinExist()) PinState.DELETE else PinState.CREATE
		binding.vpMain.isUserInputEnabled = false
		binding.bnvMain.show()
		binding.toolbar.show()
	}

	private fun configureViews() {
		setSupportActionBar(binding.toolbar)
		binding.vpMain.adapter = MainViewPagerStateAdapter(appCompatActivity = this@MainActivity, size = 5)
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