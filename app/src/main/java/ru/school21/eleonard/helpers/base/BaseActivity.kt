package ru.school21.eleonard.helpers.base

import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.school21.eleonard.R
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.mainWindow.KeyboardManager
import ru.school21.eleonard.mainWindow.ToolbarManager
import kotlin.system.exitProcess

abstract class BaseActivity : AppCompatActivity(), ToolbarManager {
	private var backPressed = 0L

	override fun configureToolbar(toolbarState: ToolbarStates, customTitle: String) {
		supportActionBar?.title = if (customTitle.isEmpty()) toolbarState.title else customTitle
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
		(this as? KeyboardManager)?.hideKeyboard()
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
}