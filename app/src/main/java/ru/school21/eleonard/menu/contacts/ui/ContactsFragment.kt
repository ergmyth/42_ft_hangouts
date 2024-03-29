package ru.school21.eleonard.menu.contacts.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.R
import ru.school21.eleonard.databinding.FragmentContactInfoBinding
import ru.school21.eleonard.databinding.FragmentContactsBinding
import ru.school21.eleonard.helpers.base.BaseFragment
import ru.school21.eleonard.helpers.toolbar.ToolbarConfigurator
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.mainWindow.ToolbarManager
import ru.school21.eleonard.menu.contacts.ContactsViewPagerManager
import ru.school21.eleonard.menu.contacts.adapters.ContactsViewPagerStateAdapter
import ru.school21.eleonard.menu.contacts.viewModels.ContactsViewModel

@AndroidEntryPoint
class ContactsFragment : BaseFragment(R.layout.fragment_contacts), ContactsViewPagerManager {
	override val binding by viewBinding(FragmentContactsBinding::bind)
	override val hasOptionMenu: Boolean = true
	override val toolbarState = ToolbarStates.STATE_CONTACTS_MAIN

	private lateinit var contactsViewModel: ContactsViewModel

	override fun openMyContacts() {
		binding.vpContacts.currentItem = MY_CONTACTS_ITEM_INDEX
	}

	override fun openFavouriteContacts() {
		binding.vpContacts.currentItem = FAVOURITE_CONTACTS_ITEM_INDEX
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		contactsViewModel = ViewModelProvider(requireActivity()).get(ContactsViewModel::class.java)
		configureViews()
	}

	private fun configureViews() {
		initViewPager()
	}

	private fun initViewPager() {
		binding.vpContacts.adapter = ContactsViewPagerStateAdapter(
			fragment = this@ContactsFragment,
			size = 2,
		)

		TabLayoutMediator(binding.tlContacts, binding.vpContacts) { tab, position ->
			when (position) {
				MY_CONTACTS_ITEM_INDEX -> tab.text = resources.getString(R.string.contacts_mine)
				FAVOURITE_CONTACTS_ITEM_INDEX -> tab.text = resources.getString(R.string.contacts_favourite)
			}
		}.attach()
	}

	companion object {
		const val MY_CONTACTS_ITEM_INDEX = 0
		const val FAVOURITE_CONTACTS_ITEM_INDEX = 1
	}
}