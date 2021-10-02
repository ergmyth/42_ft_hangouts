package ru.school21.eleonard.menu.contacts.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.R
import ru.school21.eleonard.data.db.RealmUtils
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.databinding.FragmentContactInfoBinding
import ru.school21.eleonard.helpers.base.BaseFragment
import ru.school21.eleonard.helpers.toolbar.ToolbarConfigurator
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.helpers.utils.*
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
		contactsViewModel = ViewModelProvider(requireActivity()).get(ContactsViewModel::class.java)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initViewModels()
		loadData()
		configureViews()
		initListeners()
		initObservers()
	}

	private fun initObservers() {
		contactsViewModel.curAvatar.observe(viewLifecycleOwner, { avatarPath ->
			if (avatarPath != null) {
				loadAvatar(avatarPath)
			}
		})
	}

	private fun loadData() {
		contactsList = contactsViewModel.contactsList.value!!
		if (contactsViewModel.curContact == null) {
			binding.ivSetAvatarIcon.show()
			binding.ivSetAvatarBackground.show()
			configureViewsForNewContact()
			contactsViewModel.curContact = ContactRealmModel()
		} else {
			binding.ivSetAvatarIcon.hide()
			binding.ivSetAvatarBackground.hide()
			configureViewsForExistingContact()
		}
		loadAvatar(contactsViewModel.curContact?.avatar!!)
	}

	private fun loadAvatar(avatarPath: String) {
		UtilsUI.loadAvatar(binding.ivAvatarProfile, avatarPath)
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (REQUEST_SMS_PERMISSION == requestCode) {
			for (result in grantResults)
				if (result != PackageManager.PERMISSION_GRANTED)
					return
			openChatFragment()
		} else if (REQUEST_PHOTO_PERMISSION == requestCode) {
			takePhoto()
		}
	}

	private fun initListeners() {
		binding.btnCreateContact.setOnClickListener {
			if (isPhoneValid()) {
				contactsViewModel.curContact!!.apply {
					name = binding.etName.text.toString().trim()
					number = binding.etPhone.text.toString()
					company = binding.etCompany.text.toString().trim()
					avatar = contactsViewModel.curAvatar.value ?: ""
					position = binding.etPosition.text.toString().trim()
					address = binding.etAddress.text.toString().trim()
					isFavorite = isFav
				}
				RealmUtils.getInstance().insertOrUpdate(contactsViewModel.curContact!!)
				contactsList.add(contactsViewModel.curContact!!)
				contactsViewModel.contactsList.value = contactsList
				parentFragmentManager.popBackStack()
			}
		}
		binding.ivSetAvatarIcon.setOnClickListener {
			if (UtilsPermissions.hasPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PHOTO_PERMISSION))
				takePhoto()
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
		binding.layoutActions.ivSms.setOnClickListener {
			if (UtilsPermissions.hasPermissions(
					this,
					arrayOf(Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS),
					REQUEST_SMS_PERMISSION
				)
			)
				openChatFragment()
		}
		binding.layoutActions.ivSave.setOnClickListener {
			if (isPhoneValid()) {
				setEnableOfFields(false)
				configureViewsAfterSave()

				contactsViewModel.contactsList.value!!.find { it == contactsViewModel.curContact }!!.apply {
					RealmUtils.getInstance().realm.executeTransaction {
						name = binding.etName.text.toString().trim()
						company = binding.etCompany.text.toString().trim()
						if (contactsViewModel.curAvatar.value != null)
							avatar = contactsViewModel.curAvatar.value!!
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
			configureViewsForEdit()
		}
	}

	private fun configureViewsForEdit() {
		binding.layoutActions.apply {
			ivSms.hide()
			tvSms.hide()
			ivEdit.hide()
			tvEdit.hide()
			ivPhone.hide()
			tvPhone.hide()
			ivDelete.hide()
			binding.ivSetAvatarIcon.show()
			binding.ivSetAvatarBackground.show()
			tvDelete.hide()
			ivFavorite.hide()
			tvFavorite.hide()
			ivSave.show()
			tvSave.show()
		}
	}

	private fun configureViewsAfterSave() {
		binding.layoutActions.apply {
			ivSms.show()
			tvSms.show()
			ivEdit.show()
			tvEdit.show()
			ivPhone.show()
			tvPhone.show()
			ivDelete.show()
			tvDelete.show()
			ivFavorite.show()
			tvFavorite.show()
			binding.ivSetAvatarIcon.hide()
			binding.ivSetAvatarBackground.hide()
			tvFavorite.show()
			ivSave.hide()
			tvSave.hide()
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (REQUEST_CONTACT_PHOTO == requestCode && resultCode == Activity.RESULT_OK) {
			val minPixels = 200
			val maxPixels = 10000
			val requestedPixels = 1000
			data?.let { intent ->
				CropImage
					.activity(Uri.parse(intent.data.toString()))
					.setCropShape(CropImageView.CropShape.OVAL)
					.setFixAspectRatio(true)
					.setRequestedSize(requestedPixels, requestedPixels, CropImageView.RequestSizeOptions.RESIZE_FIT)
					.setMaxCropResultSize(maxPixels, maxPixels)
					.setMinCropResultSize(minPixels, minPixels)
					.start(requireActivity())
			}
		}
	}

	private fun takePhoto() {
		val intent = Intent(Intent.ACTION_GET_CONTENT)
			.setType("image/*")
			.addCategory(Intent.CATEGORY_OPENABLE)
		startActivityForResult(intent, REQUEST_CONTACT_PHOTO)
	}

	private fun openChatFragment() {
		parentFragmentManager
			.beginTransaction()
			.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
			.add(binding.root.id, ChatFragment())
			.addToBackStack(null)
			.commit()
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
			layoutActions.nsvActions.show()
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
			layoutActions.nsvActions.hide()
			btnCreateContact.show()
			setEnableOfFields(true)
		}
	}

	companion object {
		private const val REQUEST_SMS_PERMISSION = 9968
		private const val REQUEST_PHOTO_PERMISSION = 9967
		private const val REQUEST_CONTACT_PHOTO = 9966
	}

	override fun onDestroy() {
		super.onDestroy()
		contactsViewModel.curContact = null
		contactsViewModel.curAvatar.value = null
	}
}