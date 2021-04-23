package ru.school21.eleonard.menu.contacts.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
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
import ru.school21.eleonard.helpers.utils.show
import ru.school21.eleonard.mainWindow.ToolbarManager
import ru.school21.eleonard.menu.contacts.viewModels.ContactsViewModel

@AndroidEntryPoint
class ContactInfoFragment : BaseFragment(R.layout.fragment_contact_info) {
	override val binding by viewBinding(FragmentContactInfoBinding::bind)
	override val hasOptionMenu: Boolean = true
	override val toolbarState = ToolbarStates.STATE_CONTACT_INFO

	private lateinit var contactsViewModel: ContactsViewModel
	private lateinit var contactsList: MutableList<ContactRealmModel>

	private val isFav: Boolean by lazy {
		requireArguments().getBoolean("isFav")
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		ToolbarConfigurator().configureToolbar(
			menu,
			inflater,
			requireActivity() as? ToolbarManager,
			defineToolbarState(),
		)
	}

	private fun defineToolbarState(): ToolbarStates {
		return if (contactsViewModel.curContact == null)
			ToolbarStates.STATE_CONTACT_INFO
		else
			ToolbarStates.STATE_CONTACT_INFO_CREATED
	}

	private fun initViewModels() {
		contactsViewModel = ViewModelProvider(requireParentFragment()).get(ContactsViewModel::class.java)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initViewModels()
		loadData()
		configureViews()
		initListeners()
	}

	private fun loadData() {
		contactsList = contactsViewModel.contactsList.value!!
	}

	private fun initListeners() {
		binding.btnCreateContact.setOnClickListener {
			if (isPhoneValid()) {
				val contactRealmModel = ContactRealmModel().apply {
					name = binding.etName.text.toString().trim()
					number = binding.etPhone.text.toString()
					company = binding.etCompany.text.toString().trim()
					position = binding.etPosition.text.toString().trim()
					address = binding.etAddress.text.toString().trim()
					isFavorite = isFav
				}
				RealmUtils.getInstance().insertOrUpdate(contactRealmModel)
				contactsList.add(contactRealmModel)
				contactsViewModel.contactsList.value = contactsList
				parentFragmentManager.popBackStack()
			}
		}
		binding.layoutActions.ivDelete.setOnClickListener {
			contactsList.remove(contactsViewModel.curContact)
			contactsViewModel.contactsList.value = contactsList
			contactsViewModel.deleteContact(contactsViewModel.curContact!!)
			parentFragmentManager.popBackStack()
		}
		binding.layoutActions.ivFavorite.setOnClickListener {
			binding.layoutActions.apply {
				if (contactsViewModel.curContact!!.isFavorite) {
					ivFavorite.setImageDrawable(ContextCompat.getDrawable(ivFavorite.context, R.drawable.ic_favorite))
					RealmUtils.getInstance().realm.executeTransaction {
						contactsViewModel.contactsList.value!!.find { it == contactsViewModel.curContact }!!.isFavorite = false
					}
				} else {
					ivFavorite.setImageDrawable(ContextCompat.getDrawable(ivFavorite.context, R.drawable.ic_favorite_selected))
					RealmUtils.getInstance().realm.executeTransaction {
						contactsViewModel.contactsList.value!!.find { it == contactsViewModel.curContact }!!.isFavorite = true
					}
				}
			}
			contactsViewModel.contactsList.value = contactsViewModel.contactsList.value
		}
		binding.layoutActions.ivPhone.setOnClickListener {
			val callIntent = Intent(Intent.ACTION_DIAL)
			val number = "tel:" + binding.etPhone.text.toString()
			callIntent.data = Uri.parse(number)
			callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_USER_ACTION
			startActivity(callIntent)
		}
		binding.layoutActions.ivSave.setOnClickListener {
			if (isPhoneValid()) {
				setEnableOfFields(false)
				binding.layoutActions.apply {
					ivEdit.show()
					tvEdit.show()
					ivPhone.show()
					tvPhone.show()
					ivSave.hide()
					tvSave.hide()
				}
				contactsViewModel.contactsList.value!!.find { it == contactsViewModel.curContact }!!.apply {
					RealmUtils.getInstance().realm.executeTransaction {
						name = binding.etName.text.toString().trim()
						company = binding.etCompany.text.toString().trim()
						number = binding.etPhone.text.toString().trim()
						position = binding.etPosition.text.toString().trim()
						address = binding.etAddress.text.toString().trim()
					}
				}
				contactsViewModel.contactsList.value = contactsViewModel.contactsList.value
			}
		}
		binding.layoutActions.ivEdit.setOnClickListener {
			setEnableOfFields(true)
			binding.layoutActions.apply {
				ivEdit.hide()
				tvEdit.hide()
				ivPhone.hide()
				tvPhone.hide()
				ivSave.show()
				tvSave.show()
			}
		}
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
		binding.apply {
			if (contactsViewModel.curContact!!.isFavorite)
				layoutActions.ivFavorite.setImageDrawable(
					ContextCompat.getDrawable(
						layoutActions.ivFavorite.context,
						R.drawable.ic_favorite_selected
					)
				)
			else
				layoutActions.ivFavorite.setImageDrawable(
					ContextCompat.getDrawable(
						layoutActions.ivFavorite.context,
						R.drawable.ic_favorite
					)
				)
			btnCreateContact.hide()
			layoutActions.clActions.show()
			etName.setText(contactsViewModel.curContact?.name)
			etPhone.setText(contactsViewModel.curContact?.number)
			etAddress.setText(contactsViewModel.curContact?.address)
			etCompany.setText(contactsViewModel.curContact?.company)
			etPosition.setText(contactsViewModel.curContact?.position)
			setEnableOfFields(false)
		}
	}

	private fun setEnableOfFields(isEnabled: Boolean) {
		binding.apply {
			etName.isEnabled = isEnabled
			etPhone.isEnabled = isEnabled
			etAddress.isEnabled = isEnabled
			etCompany.isEnabled = isEnabled
			etPosition.isEnabled = isEnabled
		}
	}

	private fun configureViewsForNewContact() {
		binding.apply {
			layoutActions.clActions.hide()
			btnCreateContact.show()
			setEnableOfFields(true)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		contactsViewModel.curContact = null
	}
}