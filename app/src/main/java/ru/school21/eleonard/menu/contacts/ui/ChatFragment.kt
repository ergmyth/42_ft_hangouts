package ru.school21.eleonard.menu.contacts.ui

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.R
import ru.school21.eleonard.databinding.FragmentChatBinding
import ru.school21.eleonard.helpers.base.BaseFragment
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.menu.contacts.viewModels.ChatViewModel
import ru.school21.eleonard.menu.contacts.viewModels.ContactsViewModel

@AndroidEntryPoint
class ChatFragment : BaseFragment(R.layout.fragment_chat) {
	override val binding by viewBinding(FragmentChatBinding::bind)
	override val hasOptionMenu: Boolean = true
	override val toolbarState = ToolbarStates.STATE_CHAT_MAIN

	private lateinit var contactsViewModel: ContactsViewModel
	private lateinit var chatViewModel: ChatViewModel

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		contactsViewModel = ViewModelProvider(requireActivity()).get(ContactsViewModel::class.java)
		chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
		configureViews()
	}

	/**
	 * Загрузка истории чата и сортировка по времени
	 * Отображение пустой истории
	 * Отправка по смс и сохранение в бд
	 * Отслеживание статуса смс и смена его статуса
	 * Приём от этого через ресивер и запись в бд, если этот номер, то добавление в историю
	 * Дата диалога, перед первым смс этой даты??????????
	 */

	private fun configureViews() {


	}

	companion object {
	}
}