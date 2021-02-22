package ergmyth.almagest.ft_hangouts_eleonard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import ergmyth.almagest.ft_hangouts_eleonard.databinding.ItemGroupByFirstCharBinding
import ergmyth.almagest.ft_hangouts_eleonard.models.Contact
import ergmyth.almagest.ft_hangouts_eleonard.viewModels.ContactsViewModel

class GroupAdapter(
    private var contactLists: List<List<Contact>>,
    private val fragmentManager: FragmentManager,
    private val contactsViewModel: ContactsViewModel
) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    private lateinit var contactsAdapter: ContactsAdapter

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val curList = contactLists[position]

        holder.tvChar.text = curList[0].name.first().toUpperCase().toString()
        configureRvContacts(curList, holder.rvContacts)
    }

    private fun configureRvContacts(curList: List<Contact>, rvContacts: RecyclerView) {
        if (rvContacts.adapter == null) {
            rvContacts.setHasFixedSize(true)
            contactsAdapter = ContactsAdapter(
                contactList = curList,
                fragmentManager = fragmentManager,
                contactsViewModel = contactsViewModel
            )
            rvContacts.adapter = contactsAdapter
        } else
            contactsAdapter.update(curList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): GroupViewHolder {
        val binding =
            ItemGroupByFirstCharBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.rvContacts.setRecycledViewPool(viewPool)
        return GroupViewHolder(binding)
    }

    fun update(contactLists: List<List<Contact>>) {
        this.contactLists = contactLists
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return contactLists.size
    }

    class GroupViewHolder(binding: ItemGroupByFirstCharBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvChar: MaterialTextView = binding.tvChar
        val rvContacts: RecyclerView = binding.rvContacts
    }
}