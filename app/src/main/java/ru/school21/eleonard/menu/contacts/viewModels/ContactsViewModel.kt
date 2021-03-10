package ru.school21.eleonard.menu.contacts.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.menu.contacts.domain.GetContactListUseCase

class ContactsViewModel @ViewModelInject constructor(
    private val getContactListUseCase: GetContactListUseCase,
) : ViewModel() {
    var contactsList = MutableLiveData<MutableList<MutableList<ContactRealmModel>>>()
    var favoriteContactList = MutableLiveData<MutableList<MutableList<ContactRealmModel>>>()
    var curContact: ContactRealmModel? = null
    var isFavoriteContactsWindow: Boolean = false
    init {
        contactsList.value = mutableListOf()
        favoriteContactList.value = mutableListOf()
    }

    fun getContacts(isFavorite: Boolean): List<ContactRealmModel> {
        return getContactListUseCase.invoke(isFavorite)
    }
}