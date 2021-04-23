package ru.school21.eleonard.menu.contacts.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.school21.eleonard.menu.contacts.ui.ContactsFragment.Companion.MY_CONTACTS_ITEM_INDEX
import ru.school21.eleonard.menu.contacts.ui.ContactListFragment
import ru.school21.eleonard.menu.contacts.ui.ContactsFragment.Companion.FAVOURITE_CONTACTS_ITEM_INDEX
import ru.school21.eleonard.menu.contacts.viewModels.ContactsViewModel

class ContactsViewPagerStateAdapter(
	fragment: Fragment,
	private val size: Int,
) : FragmentStateAdapter(fragment) {

	override fun getItemCount(): Int {
		return size
	}

	override fun createFragment(position: Int): Fragment {
		return when (position) {
			MY_CONTACTS_ITEM_INDEX -> ContactListFragment().apply { arguments = Bundle().apply { putBoolean("isFav", false) } }
			else -> ContactListFragment().apply { arguments = Bundle().apply { putBoolean("isFav", true) } }
		}
	}
}