package ru.school21.eleonard.menu.contacts.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.menu.contacts.domain.DeleteContactByIdUseCase
import ru.school21.eleonard.menu.contacts.domain.GetContactListUseCase

class ContactsViewModel @ViewModelInject constructor(
    private val getContactListUseCase: GetContactListUseCase,
    private val deleteContactByIdUseCase: DeleteContactByIdUseCase,
) : ViewModel() {
    var contactsList = MutableLiveData<MutableList<ContactRealmModel>>()
    var curContact: ContactRealmModel? = null
    init {
        contactsList.value = getContacts(false).toMutableList()
    }

    fun getContacts(isFavorite: Boolean): List<ContactRealmModel> {
        return getContactListUseCase.invoke(isFavorite)
    }

    fun deleteContact(contact: ContactRealmModel) {
        deleteContactByIdUseCase.invoke(contact)
    }
}