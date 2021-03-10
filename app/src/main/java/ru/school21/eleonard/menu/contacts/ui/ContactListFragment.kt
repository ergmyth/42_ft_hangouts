package ru.school21.eleonard.menu.contacts.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.R
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.databinding.FragmentContactListBinding
import ru.school21.eleonard.helpers.hide
import ru.school21.eleonard.helpers.show
import ru.school21.eleonard.menu.contacts.adapters.GroupAdapter
import ru.school21.eleonard.menu.contacts.viewModels.ContactsViewModel

@AndroidEntryPoint
class ContactListFragment : Fragment() {
	private lateinit var binding: FragmentContactListBinding
	private lateinit var contactsViewModel: ContactsViewModel
	private lateinit var groupAdapter: GroupAdapter


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentContactListBinding.inflate(layoutInflater, container, false)
		initViewModels()
		return binding.root
	}

	private fun initViewModels() {
		contactsViewModel = ViewModelProvider(requireParentFragment()).get(ContactsViewModel::class.java)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initValues()
		configureViews()
		initListeners()
		initObservers()
	}

	private fun initValues() {
		if (contactsViewModel.isFavoriteContactsWindow) {
			contactsViewModel.favoriteContactList.value = splitToListsByFirstLetterOfName(contactsViewModel.getContacts(isFavorite = true))
		} else
			contactsViewModel.contactsList.value = splitToListsByFirstLetterOfName(contactsViewModel.getContacts(isFavorite = false))
	}

	private fun splitToListsByFirstLetterOfName(realmContactList: List<ContactRealmModel>): MutableList<MutableList<ContactRealmModel>> {
		val contactLists = mutableListOf<MutableList<ContactRealmModel>>()
		val sortedRealmList = realmContactList.sortedBy { it.name }
		var currentLetterList = mutableListOf<ContactRealmModel>()
		for (contact in sortedRealmList) {
			when {
				currentLetterList.isEmpty() -> currentLetterList.add(contact)
				contact.name.first() == currentLetterList[0].name.first() -> currentLetterList.add(contact)
				else -> {
					contactLists.add(currentLetterList)
					currentLetterList = mutableListOf()
					currentLetterList.add(contact)
				}
			}
		}
		if (currentLetterList.isNotEmpty())
			contactLists.add(currentLetterList)
		return contactLists
	}

	private fun configureViews() {

	}

	private fun initListeners() {
		binding.btnAddContact.setOnClickListener {
			parentFragmentManager
				.beginTransaction()
				.add(R.id.flContactsRootView, ContactInfoFragment())
				.addToBackStack(null)
				.commit()
		}
	}

	private fun initObservers() {
		if (contactsViewModel.isFavoriteContactsWindow)
			observeOnContactList(contactsViewModel.favoriteContactList)
		else
			observeOnContactList(contactsViewModel.contactsList)
	}

	private fun observeOnContactList(contactList: MutableLiveData<MutableList<MutableList<ContactRealmModel>>>) {
		contactList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
			if (it.isEmpty()) {
				binding.tvNoItemsMessage.show()
				binding.rvGroups.hide()
			} else {
				binding.tvNoItemsMessage.hide()
				binding.rvGroups.show()
				configureGroupAdapter(it)
			}
		})
	}

	private fun configureGroupAdapter(contactLists: MutableList<MutableList<ContactRealmModel>>) {
		if (binding.rvGroups.adapter == null) {
			binding.rvGroups.setHasFixedSize(true)
			groupAdapter = GroupAdapter(
				contactLists = contactLists,
				fragmentManager = parentFragmentManager,
				contactsViewModel = contactsViewModel
			)
			binding.rvGroups.adapter = groupAdapter
		} else
			groupAdapter.update(contactLists)
	}
}