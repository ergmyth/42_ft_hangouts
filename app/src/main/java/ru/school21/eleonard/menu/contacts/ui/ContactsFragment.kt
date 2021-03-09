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
import ru.school21.eleonard.databinding.FragmentContactsBinding
import ru.school21.eleonard.helpers.toolbar.ToolbarConfigurator
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.mainWindow.ToolbarManager
import ru.school21.eleonard.menu.contacts.ContactsViewPagerManager
import ru.school21.eleonard.menu.contacts.adapters.ContactsViewPagerStateAdapter
import ru.school21.eleonard.menu.contacts.viewModels.ContactsViewModel


@AndroidEntryPoint
class ContactsFragment : Fragment(), ContactsViewPagerManager {
	private lateinit var binding: FragmentContactsBinding
	private lateinit var contactsViewModel: ContactsViewModel

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		ToolbarConfigurator().configureToolbar(
			menu,
			inflater,
			activity as? ToolbarManager,
			ToolbarStates.STATE_CONTACTS_MAIN,
			resources.getString(R.string.bnv_hangouts)
		)
	}

	override fun openMyContacts() {
		binding.vpContacts.currentItem = MY_CONTACTS_ITEM_INDEX
	}

	override fun openFavouriteContacts() {
		binding.vpContacts.currentItem = FAVOURITE_CONTACTS_ITEM_INDEX
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentContactsBinding.inflate(layoutInflater, container, false)
		contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		configureViews()
	}

	private fun configureViews() {
		setHasOptionsMenu(true)
		initViewPager()
	}

	private fun initViewPager() {
		binding.vpContacts.adapter = ContactsViewPagerStateAdapter(
			fragment = this@ContactsFragment,
			size = 2,
			viewModel = contactsViewModel
		)

		TabLayoutMediator(binding.tlContacts, binding.vpContacts) { tab, position ->
			when (position) {
				MY_CONTACTS_ITEM_INDEX -> tab.text = resources.getString(R.string.contacts_mine)
				FAVOURITE_CONTACTS_ITEM_INDEX -> tab.text = resources.getString(R.string.contacts_favourite)
			}
		}.attach()
		binding.tlContacts.addOnTabSelectedListener(object : OnTabSelectedListener {
			override fun onTabSelected(tab: TabLayout.Tab) {
				contactsViewModel.isFavoriteContactsWindow = tab.position != 0
			}

			override fun onTabUnselected(tab: TabLayout.Tab) {}
			override fun onTabReselected(tab: TabLayout.Tab) {}
		})
	}

	companion object {
		const val MY_CONTACTS_ITEM_INDEX = 0
		const val FAVOURITE_CONTACTS_ITEM_INDEX = 1
	}
}