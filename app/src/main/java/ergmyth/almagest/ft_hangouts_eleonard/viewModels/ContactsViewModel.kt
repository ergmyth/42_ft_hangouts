package ergmyth.almagest.ft_hangouts_eleonard.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ergmyth.almagest.ft_hangouts_eleonard.models.Contact

class ContactsViewModel : ViewModel() {
    var contactsList = MutableLiveData<List<List<Contact>>>()
    var curContact: Contact? = null
    init {
        contactsList.value = listOf()
    }
}