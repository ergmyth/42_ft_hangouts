package ergmyth.almagest.ft_hangouts_eleonard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import ergmyth.almagest.ft_hangouts_eleonard.R
import ergmyth.almagest.ft_hangouts_eleonard.databinding.ItemContactBinding
import ergmyth.almagest.ft_hangouts_eleonard.models.Contact
import ergmyth.almagest.ft_hangouts_eleonard.ui.ContactInfoFragment
import ergmyth.almagest.ft_hangouts_eleonard.viewModels.ContactsViewModel

class ContactsAdapter(
    private var contactList: List<Contact>,
    private val fragmentManager: FragmentManager,
    private val contactsViewModel: ContactsViewModel
) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val curContact = contactList[position]

        holder.tvName.text = curContact.name
        holder.ivAvatar.setImageDrawable(curContact.avatar)
        holder.rlContact.setOnClickListener {
            openContactFragment(curContact)
        }
    }

    private fun openContactFragment(curContact: Contact) {
        val contactInfoFragment = ContactInfoFragment()
        contactsViewModel.curContact = curContact
        fragmentManager
            .beginTransaction()
            .add(R.id.flRootView, contactInfoFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ContactsViewHolder {
        val binding =
            ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsViewHolder(binding)
    }

    fun update(contactList: List<Contact>) {
        this.contactList = contactList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    class ContactsViewHolder(binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivAvatar: AppCompatImageView = binding.ivAvatar
        val rlContact: RelativeLayout = binding.rlContact
        val tvName: MaterialTextView = binding.tvName
    }
}