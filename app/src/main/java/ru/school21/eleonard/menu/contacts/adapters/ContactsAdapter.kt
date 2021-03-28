package ru.school21.eleonard.menu.contacts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import ru.school21.eleonard.R
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.databinding.ItemContactBinding
import ru.school21.eleonard.menu.contacts.ui.ContactInfoFragment
import ru.school21.eleonard.menu.contacts.viewModels.ContactsViewModel

class ContactsAdapter(
    private var contactList: MutableList<ContactRealmModel>,
    private val fragmentManager: FragmentManager,
    private val contactsViewModel: ContactsViewModel
) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val curContact = contactList[position]
        holder.binding.tvName.text = curContact.name
        configureAvatar(curContact.avatar, holder.binding.ivAvatar)

        holder.binding.rlContact.setOnClickListener {
            openContactFragment(curContact)
        }
    }

    private fun configureAvatar(url: String, ivAvatar: AppCompatImageView) {
        if (url.isEmpty())
            ivAvatar.setImageDrawable(ContextCompat.getDrawable(ivAvatar.context, R.drawable.ic_avatar_default))
        else {
            Glide.with(ivAvatar.context).load(url).into(ivAvatar)
            if (ivAvatar.drawable == null)
                ivAvatar.setImageDrawable(ContextCompat.getDrawable(ivAvatar.context, R.drawable.ic_avatar_default))
        }
    }

    private fun openContactFragment(curContact: ContactRealmModel) {
        val contactInfoFragment = ContactInfoFragment()
        contactsViewModel.curContact = curContact
        fragmentManager
            .beginTransaction()
            .add(R.id.flContactsRootView, contactInfoFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ContactsViewHolder {
        val binding =
            ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsViewHolder(binding)
    }

    fun update(contactList: MutableList<ContactRealmModel>) {
        this.contactList = contactList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    class ContactsViewHolder(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root)
}