package ru.school21.eleonard.menu.contacts.ui

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.R
import ru.school21.eleonard.data.db.RealmUtils
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.databinding.FragmentContactInfoBinding
import ru.school21.eleonard.helpers.base.BaseFragment
import ru.school21.eleonard.helpers.utils.hide
import ru.school21.eleonard.helpers.toolbar.ToolbarConfigurator
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.helpers.utils.setPhoneFormat
import ru.school21.eleonard.mainWindow.ToolbarManager
import ru.school21.eleonard.menu.contacts.viewModels.ContactsViewModel

@AndroidEntryPoint
class ContactInfoFragment : BaseFragment(R.layout.fragment_contact_info) {
	override val binding by viewBinding(FragmentContactInfoBinding::bind)
	override val hasOptionMenu: Boolean = true
	override val toolbarState = ToolbarStates.STATE_CONTACT_INFO

	lateinit var contactsViewModel: ContactsViewModel

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		ToolbarConfigurator().configureToolbar(
			menu,
			inflater,
			requireActivity() as? ToolbarManager,
			defineToolbarState(),
			defineToolbarTitle()
		)
	}

	private fun defineToolbarState(): ToolbarStates {
		return if (contactsViewModel.curContact == null)
			ToolbarStates.STATE_CONTACT_INFO
		else
			ToolbarStates.STATE_CONTACT_INFO_CREATED
	}

	private fun defineToolbarTitle(): String {
		return if (contactsViewModel.curContact == null)
			""
		else
			contactsViewModel.curContact?.name!!
	}

	private fun initViewModels() {
		contactsViewModel = ViewModelProvider(requireParentFragment()).get(ContactsViewModel::class.java)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initViewModels()
		configureViews()
		initListeners()
	}

	private fun initListeners() {
		binding.btnCreateContact.setOnClickListener {
			if (isPhoneValid()) {
				val contactRealmModel = ContactRealmModel().apply {
					name = if (binding.etName.text.toString().trim().isEmpty())
						binding.etPhone.text.toString()
					else
						binding.etName.text.toString()
					number = binding.etPhone.text.toString()
					isFavorite = contactsViewModel.isFavoriteContactsWindow
				}
				RealmUtils.getInstance().insertOrUpdate(contactRealmModel)
				parentFragmentManager.popBackStack()
				if (contactRealmModel.isFavorite)
					uploadContactList(
						contactRealmModel = contactRealmModel,
						contactsList = contactsViewModel.favoriteContactList.value!!,
						isFavorite = true
					)
				uploadContactList(
					contactRealmModel = contactRealmModel,
					contactsList = contactsViewModel.contactsList.value!!,
					isFavorite = false
				)
			}
		}
	}

	private fun uploadContactList(
		contactRealmModel: ContactRealmModel,
		contactsList: MutableList<MutableList<ContactRealmModel>>,
		isFavorite: Boolean
	) {
		var currentLetterList = contactsList.find { it.first().name.first() == contactRealmModel.name.first() }

		if (currentLetterList == null) {
			currentLetterList = mutableListOf()
			currentLetterList.add(contactRealmModel)
			contactsList.add(currentLetterList)
			contactsList.sortBy { it.first().name.first() }
		} else {
			currentLetterList.add(contactRealmModel)
			currentLetterList.sortBy { it.name }
		}
		if (isFavorite)
			contactsViewModel.favoriteContactList.value = contactsList
		else
			contactsViewModel.contactsList.value = contactsList
	}

	private fun isPhoneValid(): Boolean {
		return when {
			binding.etPhone.text.toString().isEmpty() -> {
				binding.tilPhone.error = resources.getString(R.string.default_required_field_message)
				false
			}
			else -> true
		}
	}

	private fun configureViews() {
		if (contactsViewModel.curContact == null)
			configureViewsForNewContact()
		else
			configureViewsForExistingContact()
	}

	private fun configureViewsForExistingContact() {
		binding.btnCreateContact.hide()
		binding.tilName.hide()
		binding.etPhone.setPhoneFormat()
		binding.etPhone.setText(contactsViewModel.curContact?.number)
		binding.etPhone.isEnabled = false
	}

	private fun configureViewsForNewContact() {

	}

	override fun onDestroyView() {
		super.onDestroyView()
		contactsViewModel.curContact = null
	}
}