package ru.school21.eleonard.menu.contacts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.databinding.ItemGroupByFirstCharBinding
import ru.school21.eleonard.menu.contacts.viewModels.ContactsViewModel

class GroupAdapter(
	private var contactLists: MutableList<MutableList<ContactRealmModel>>,
	private val fragmentManager: FragmentManager,
	private val contactsViewModel: ContactsViewModel
) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {
	private val viewPool = RecyclerView.RecycledViewPool()

	override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
		val curList = contactLists[position]

		holder.tvChar.text = if (curList.first().name.isEmpty())
			curList.first().number.first().toUpperCase().toString()
		else
			curList.first().name.first().toUpperCase().toString()
		configureRvContacts(curList, holder.rvContacts)
	}

	private fun configureRvContacts(curList: MutableList<ContactRealmModel>, rvContacts: RecyclerView) {
		if (rvContacts.adapter == null) {
			rvContacts.setHasFixedSize(true)
			rvContacts.adapter = ContactsAdapter(
				contactList = curList,
				fragmentManager = fragmentManager,
				contactsViewModel = contactsViewModel
			)
		} else {
			(rvContacts.adapter as? ContactsAdapter)?.update(curList)
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, p1: Int): GroupViewHolder {
		val binding =
			ItemGroupByFirstCharBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		binding.rvContacts.setRecycledViewPool(viewPool)
		return GroupViewHolder(binding)
	}

	fun update(contactLists: MutableList<MutableList<ContactRealmModel>>) {
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