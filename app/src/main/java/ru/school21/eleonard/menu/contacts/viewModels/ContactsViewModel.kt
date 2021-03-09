package ru.school21.eleonard.menu.contacts.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.school21.eleonard.helpers.requests.Event
import ru.school21.eleonard.menu.contacts.domain.LoadContactListUseCase
import ru.school21.eleonard.menu.contacts.models.Contact
import kotlinx.coroutines.flow.collect
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.menu.contacts.domain.getContactList.GetContactListUseCase

class ContactsViewModel @ViewModelInject constructor(
    private val loadContactListUseCase: LoadContactListUseCase,
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

    val contactsLoadingResponse = MutableLiveData<Event<List<Contact>>>()
    fun loadContacts() {
        viewModelScope.launch {
            loadContactListUseCase.invoke().collect {
                contactsLoadingResponse.postValue(it)
            }
        }
    }

    fun getContacts(isFavorite: Boolean): List<ContactRealmModel> {
        return getContactListUseCase.invoke(isFavorite)
    }
}