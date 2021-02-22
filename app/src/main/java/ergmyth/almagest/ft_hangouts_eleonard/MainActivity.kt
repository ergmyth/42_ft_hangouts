package ergmyth.almagest.ft_hangouts_eleonard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import ergmyth.almagest.ft_hangouts_eleonard.adapters.GroupAdapter
import ergmyth.almagest.ft_hangouts_eleonard.databinding.ActivityMainBinding
import ergmyth.almagest.ft_hangouts_eleonard.models.Contact
import ergmyth.almagest.ft_hangouts_eleonard.viewModels.ContactsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var groupAdapter: GroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureViews()
        initViewModels()
        initListeners()

        //todo когда загружен список - поменять llLoading = GONE
    }

    private fun initViewModels() {
        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
    }

    private fun splitIntoListsByFirstLetterOfName(contactList: List<Contact>): List<List<Contact>> {
        val contactLists = mutableListOf<List<Contact>>()
        contactList.sortedBy { it.name }
        val currentLetterList = mutableListOf<Contact>()
        for (contact in contactList) {
            while (currentLetterList.isEmpty() || contact.name.first() == currentLetterList[0].name.first()) {
                currentLetterList.add(contact)
            }
            contactLists.add(currentLetterList.toList())
        }

        return contactLists.toList()
    }

    private fun configureRvGroups(contactLists: List<List<Contact>>) {
        if (binding.rvGroups.adapter == null) {
            binding.rvGroups.setHasFixedSize(true)
            groupAdapter = GroupAdapter(
                contactLists = contactLists,
                fragmentManager = supportFragmentManager,
                contactsViewModel = contactsViewModel
            )
            binding.rvGroups.adapter = groupAdapter
        } else
            groupAdapter.update(contactLists)
    }

    private fun initListeners() {
        binding.ivClear.setOnClickListener {
            //todo очистить etSearch
            binding.ivClear.visibility = View.INVISIBLE
        }

        //todo отображать ivClear = Visible, когда etSearch не пуст
    }

    private fun configureViews() {

    }
}