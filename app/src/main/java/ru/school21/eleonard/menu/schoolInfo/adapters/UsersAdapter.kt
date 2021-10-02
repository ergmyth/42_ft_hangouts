package ru.school21.eleonard.menu.schoolInfo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.school21.eleonard.data.network.api.models.UserInListResponse
import ru.school21.eleonard.databinding.ItemUserBinding

class UsersAdapter(
	private var users: List<UserInListResponse>,
) : RecyclerView.Adapter<UsersAdapter.GroupViewHolder>() {

	override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
		val curUser = users[position]

		holder.binding.tvName.text = curUser.login
	}


	override fun onCreateViewHolder(parent: ViewGroup, p1: Int): GroupViewHolder {
		val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return GroupViewHolder(binding)
	}

	fun update(users: List<UserInListResponse>) {
		this.users = users
		notifyDataSetChanged()
	}

	override fun getItemCount(): Int {
		return users.size
	}

	class GroupViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}