package ru.school21.eleonard.menu.contacts.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.data.db.realmModels.MessageRealmModel
import ru.school21.eleonard.menu.contacts.domain.DeleteContactByIdUseCase
import ru.school21.eleonard.menu.contacts.domain.GetContactListUseCase

class ChatViewModel @ViewModelInject constructor(
) : ViewModel() {

}