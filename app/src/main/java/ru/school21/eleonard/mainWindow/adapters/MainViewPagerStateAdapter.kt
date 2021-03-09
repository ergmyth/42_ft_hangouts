package ru.school21.eleonard.mainWindow.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.school21.eleonard.authorization.ui.LoginFragment
import ru.school21.eleonard.menu.contacts.ui.ContactsFragment
import ru.school21.eleonard.menu.schoolInfo.ui.SchoolInfoFragment
import ru.school21.eleonard.menu.graphics.ui.GraphicsFragment
import ru.school21.eleonard.menu.other.ui.OtherFragment

class MainViewPagerStateAdapter(
	appCompatActivity: AppCompatActivity,
	private val size: Int,
	private val isGuest: Boolean
) : FragmentStateAdapter(appCompatActivity) {

	override fun getItemCount(): Int {
		return size
	}

	override fun createFragment(position: Int): Fragment {
		if (isGuest)
			return LoginFragment()
		return when (position) {
			0 -> ContactsFragment()
			1 -> SchoolInfoFragment()
			2 -> GraphicsFragment()
			else -> OtherFragment()
		}
	}
}